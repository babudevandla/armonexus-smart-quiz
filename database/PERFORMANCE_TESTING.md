# Master Data Dump & Performance Testing Guide

This folder gives you a realistic, full-scale dataset for the Quiz Admin
Portal so you can click through every page — Admin, Instructor, Reviewer,
and Student — with actual volume instead of the 4 demo rows `DataSeeder`
creates, and see how the app behaves under load.

## What's in here

| File | Purpose |
|---|---|
| `generate_seed_dump.py` | The Python script that generated the dump below. Re-run it (optionally tweaking the row counts near the top) to regenerate with different volume. |
| `seed-master-data-dump.sql` | ~2,700 rows across all 30 tables — the actual data to load. |
| `performance-indices.sql` | Adds indices on columns the app filters/sorts on heavily (status, submitted_at, email, etc.) that Hibernate's `ddl-auto=update` doesn't create automatically. |

## Row counts in the dump

| Table | Rows | Why this much |
|---|---|---|
| `users` | 80 (5 admin, 12 instructor, 8 reviewer, 55 student) | Enough to test the Users list pagination and role filtering |
| `questions` | 400 | The main table DataTables pagination/search gets exercised against |
| `question_options` | 1,600 | 4 per question |
| `question_tags` | ~830 | 1-3 tags per question |
| `quizzes` | 30 | Spread across all 10 subjects |
| `quiz_questions` | ~300 | ~10 questions per quiz |
| `quiz_schedules` | 30 | Deliberately mixed past/current/future dates — see below |
| `quiz_assignments` | ~185 | Mix of direct-to-user and to-group assignments |
| `quiz_results` | 315 (300 completed + 15 in-progress) | The 15 in-progress rows (`submitted_at IS NULL`) let you see Live Monitoring populated |
| `practice_attempts` | 200 | |
| `audit_logs` | 80 | |
| everything else (subjects, categories, tags, notifications, announcements, settings) | 3–50 each | Enough to see real lists, not edge cases of 0-1 rows |

**Every seeded user's password is `Password@123`** (a real bcrypt hash is
baked into the dump — no extra step needed).

## How to load it

```bash
# 1. Make sure the schema exists first — start the app once so Hibernate's
#    ddl-auto=update creates all tables (DataSeeder will also insert its
#    usual 4 demo accounts; that's fine, this dump won't collide with them).
mvn spring-boot:run
# ... let it boot fully, then Ctrl+C

# 2. Load the master data dump
mysql -u root -p quiz_admin_db < database/seed-master-data-dump.sql

# 3. Add the performance indices
mysql -u root -p quiz_admin_db < database/performance-indices.sql

# 4. Start the app for real
mvn spring-boot:run
```

Login with any seeded account, e.g. `admin1@quizapp.com` / `Password@123`,
or the original demo accounts from `DataSeeder` still work too
(`admin@quizapp.com` / `Admin@123`, etc).

### Why this is safe to run more than once / in either order

- Every explicit ID in the dump starts at **1000+**, and `DataSeeder` only
  ever creates rows with IDs in the 1-5 range — they can't collide.
- Tables with a unique business key (`roles.name`, `users.email`,
  `difficulty_levels.name`, `question_types.name`, `tags.name`, and the
  5 settings singleton tables) use `ON DUPLICATE KEY UPDATE`, so re-running
  the dump updates those rows in place instead of erroring or duplicating.
- Tables without a natural unique key (questions, options, quizzes, etc.)
  use plain `INSERT` — re-running the dump on a database that already has
  it loaded **will** duplicate those rows. If you want to reset and reload,
  truncate first (see "Resetting" below).

### Resetting to reload cleanly

```sql
-- Run in this order (respects FK dependencies) if you want a clean reload:
SET FOREIGN_KEY_CHECKS=0;
TRUNCATE question_tags; TRUNCATE question_options; TRUNCATE quiz_questions;
TRUNCATE user_group_members; TRUNCATE user_roles;
TRUNCATE questions; TRUNCATE quiz_schedules; TRUNCATE quiz_assignments;
TRUNCATE quiz_results; TRUNCATE invitations; TRUNCATE practice_attempts;
TRUNCATE practice_sets; TRUNCATE categories; TRUNCATE subjects;
TRUNCATE tags; TRUNCATE difficulty_levels; TRUNCATE question_types;
TRUNCATE quizzes; TRUNCATE user_groups; TRUNCATE users; TRUNCATE roles;
TRUNCATE sms_notifications; TRUNCATE email_notifications; TRUNCATE push_notifications;
TRUNCATE announcements; TRUNCATE audit_logs;
SET FOREIGN_KEY_CHECKS=1;
```
Then re-run steps 2-3 above.

## Page-by-page performance checklist

Once loaded, go through each page and note load time (browser dev tools →
Network tab → the main document request) plus how DataTables feels with
real volume:

| Page | What to check |
|---|---|
| **Admin → Questions** (`/questions`) | 400 rows — this is the single best test of DataTables' client-side pagination/search. Should still feel instant since DataTables paginates in-browser, but watch initial page load time (the server sends all 400 rows in the HTML in one shot — see note below). |
| **Admin → Users** (`/users`) | 80 rows with role badges — check the role-badge loop doesn't visibly lag rendering. |
| **Admin → Quizzes** (`/quizzes`) | 30 rows, now with the Schedule status column — confirm Open/Upcoming/Closed badges match what you'd expect given the mixed schedule dates. |
| **Admin → Live Monitoring** (`/live-monitoring`) | Should show the 15 in-progress attempts, auto-refreshing every 10s. |
| **Admin → Results** (`/results`) | 300 completed rows — good pagination stress test. |
| **Admin → Leaderboard** (`/leaderboard`) | Sorted client-side by DataTables over 300 rows. |
| **Admin → Reports → Performance Analytics** (`/reports/performance`) | Pass rate calculation runs over all 300 results in memory — watch for any lag. |
| **Student → Available Quizzes** (`/student/quizzes`) | Log in as any `studentN@quizapp.com` — each student is assigned multiple quizzes via both direct assignment and group membership, so you'll see a realistic mixed list with Open/Upcoming/Closed states. |
| **Student → Take Quiz** | Pick an "Open Now" quiz and confirm the timer, question rendering, and grading feel snappy with ~10 questions. |
| **Instructor → My Questions / My Quizzes** | Log in as `instructor1@quizapp.com` — confirm the filtered-to-own-content views load quickly even though the underlying tables have hundreds of rows. |
| **Reviewer → Review/Approval Queue** | Log in as `reviewer1@quizapp.com` — ~80 questions are `PENDING_REVIEW` across the dataset, a good test of that queue at realistic size. |

### A known scaling note worth knowing about

Every list page currently sends **all matching rows** to the browser in one
HTML response, and DataTables paginates client-side (in JavaScript) rather
than the server only sending one page at a time. At 400 rows this is still
fast, but if you plan to grow well past a few thousand rows per table,
the next real performance step would be **server-side pagination** — having
each controller accept `page`/`size` parameters and use Spring Data's
`Pageable`/`Page<T>` instead of `findAll()`, with DataTables configured for
`"serverSide": true`. That's a larger change than this dump/testing pass;
flag it if you want it done once you've seen how the current setup performs
for your real data volume.

## What `performance-indices.sql` adds

| Index | Why |
|---|---|
| `questions(status)`, `questions(subject_id, status)` | Every Question Bank list and the Review/Approval queue filter by status |
| `quiz_results(submitted_at)`, `quiz_results(user_id, submitted_at)` | Live Monitoring, Results, Leaderboard, and both Student result pages all filter on `submitted_at IS NULL` / `IS NOT NULL` |
| `quizzes(active)`, `quiz_schedules(quiz_id, active)`, `quiz_schedules(start_time, end_time)` | The schedule-status computation (`QuizScheduleStatusService`) runs this lookup on every quiz list render |
| `users(email)` | Hit on literally every authenticated request (Spring Security's `UserDetailsService` looks up by email) |
| `audit_logs(timestamp)` | Audit log is naturally viewed sorted by recency |

Run `SHOW INDEX FROM questions;` (or any table) after applying the script
to confirm they're in place.
