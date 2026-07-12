# ArmoNexus Online Smart Quiz / Admin Portal

A Spring Boot admin application that lets administrators and instructors manage quizzes, questions, users, schedules, reports, notifications and announcements. The UI is server-side rendered using JSPs and the application uses Spring Security, Spring Data JPA (Hibernate) and MySQL.

## Table of contents
  - Overview
  - Actors & use-cases
  - Architecture & important files
  - Requirements
  - Quick start (build & run)
  - Configuration
  - Default seeded accounts
  - Key web endpoints (UI / controllers)
  - Notifications (example usage)
  - Troubleshooting
  - Testing
  - Extending & development tips
  - Contributing
  - License & contacts

## Overview
This admin portal supports:

  - User and role management
  - Question authoring with attachments
  - Categories, difficulty levels, question types and tags
  - Bulk uploads for questions
  - Quiz scheduling and assignments
  - Results, reports and exports
  - Audit logs and live monitoring
  - Notifications: SMS, Email, Push and Announcements

## Actors & main use-cases
  - Admin: full system management, user & role management, settings, generate reports, send notifications.
  - Instructor: create and manage questions, practice sets, schedule quizzes.
  - Reviewer: review and approve submitted questions.
  - Student (indirect): takes quizzes and receives notifications — results are visible to admin/instructors.

Example user stories:

 - Admin can create users and assign roles.
 - Instructor can create questions and upload attachments.
 - Admin can schedule quizzes and export results.
 - Admin can send announcements and push/sms/email notifications.

## 1. Tech Stack

| Layer        | Technology                                        |
|--------------|---------------------------------------------------|
| Language     | Java 21                                           |
| Framework    | Spring Boot 3.2.5 (Spring MVC, Spring Data JPA)   |
| View         | JSP + JSTL (Jakarta namespace) + Spring form tags |
| Security     | Spring Security (form login, role-based access)   |
| Database     | MySQL 8 (via Spring Data JPA / Hibernate)         |
| Build        | Maven, packaged as WAR                            |

JSP requires WAR packaging + `tomcat-embed-jasper`, which is why the POM is
set to `<packaging>war</packaging>` — this still runs with `mvn spring-boot:run`
using the embedded Tomcat, and can also be deployed to an external Tomcat.

## 2. Prerequisites

- JDK 21+
- Maven 3.8+
- MySQL 8 running locally (or update `application.properties`)

## 3. Setup & Run

```bash
# 1. Create the database (or let createDatabaseIfNotExist=true handle it)
mysql -u root -p -e "CREATE DATABASE quiz_admin_db;"

# 2. Update credentials if needed
#    src/main/resources/application.properties
#    spring.datasource.username / spring.datasource.password

# 3. Build and run
mvn clean package
mvn spring-boot:run
```

Visit **http://localhost:8080/login**

Default seeded logins (created automatically on first run by `DataSeeder`) — one per role:

| Role       | Email                    | Password        | Lands on             |
|------------|---------------------------|------------------|-----------------------|
| Admin      | admin@quizapp.com         | Admin@123        | `/dashboard`          |
| Instructor | instructor@quizapp.com    | Instructor@123   | `/instructor/dashboard` |
| Reviewer   | reviewer@quizapp.com      | Reviewer@123     | `/reviewer/dashboard` |
| Student    | student@quizapp.com       | Student@123      | `/student/dashboard`  |

`DashboardController` inspects the logged-in user's authority and redirects to
the right role dashboard automatically — there's one login form, but four
distinct experiences behind it, each with its own sidebar menu
(`layout/sidebar.jsp` uses `<sec:authorize>` blocks per role).

Hibernate's `ddl-auto=update` will auto-create all tables from the JPA
entities on first startup — no manual schema.sql required.

## 4. Role-by-role breakdown

### Admin (`ROLE_ADMIN`)
Full access to every module — Users, Roles, Groups, Master Data, Question
Bank, Quiz Management, Results, Reports, Notifications, Settings, Audit
Logs. This is the module tree from the original spec, entirely implemented.

### Instructor (`ROLE_INSTRUCTOR`) — creates quizzes & questions
- `/instructor/dashboard` — counts of their own questions (by status) and quizzes.
- `/instructor/questions` — "My Questions" (reuses the admin Question Bank
  list/form views, filtered to `createdBy = me`).
- `/instructor/quizzes` — "My Quizzes" (reuses the admin Quiz Management
  list/form views, filtered to `createdBy = me`).
- Can create/edit questions (`/questions/new`, `/questions/{id}/edit`),
  bulk-upload via CSV (`/questions/bulk-upload`), and build quizzes from
  approved questions (`/quizzes/new`) — same endpoints Admin uses, now also
  authorized for `ROLE_INSTRUCTOR` in `SecurityConfig`.
- New questions always start in `PENDING_REVIEW` and can't be added to a
  quiz until a Reviewer approves them.

### Reviewer (`ROLE_REVIEWER`) — reviews & approves questions
- `/reviewer/dashboard` — pending / approved / rejected counts.
- `/question-approval` — the queue of `PENDING_REVIEW` questions with
  Approve/Reject actions (`QuestionApprovalController`).

### Student (`ROLE_STUDENT`) — takes quizzes
- `/student/dashboard` — assigned quizzes, active practice sets, and recent results.
- `/student/quizzes` → `/student/quizzes/{id}/take` — a real quiz-taking
  screen: renders every question with radio-button options, runs a
  JS countdown timer based on the quiz's duration, and auto-submits when
  time runs out.
- `/student/quizzes/{id}/submit` — server-side grading: compares selected
  option IDs against each `QuestionOption.isCorrect`, applies marks/negative
  marks, saves a `QuizResult` (pass/fail against `passingMarks`).
- `/student/results` — their own quiz history.
- `/student/practice` → `/student/practice/{id}/take` — untimed self-practice
  pulling up to 10 approved questions from the practice set's subject;
  submitting saves a `PracticeAttempt` with a percentage score.
- `/student/practice-history` — their own practice attempts.
- Which quizzes show up as "assigned" is driven by `QuizAssignment` rows
  (assigned directly to the user, or to a `UserGroup` they belong to) — set
  these up as Admin via **Quiz Management → Assign Users / Assign Groups**.

`SecurityConfig` enforces all of this at the URL level (not just hidden
menu items) — e.g. a Student hitting `/questions` directly gets a 403, not
just a missing sidebar link.

## 5. What's fully implemented (working CRUD + views)

- **Auth**: Spring Security form login/logout, BCrypt passwords, role-based
  URL authorization (`SecurityConfig`), 403 access-denied page.
- **Dashboard**: live counts (users, questions, quizzes, attempts).
- **User Management**
  - Users: full CRUD, multi-role assignment, enable/disable toggle.
  - User Roles: full CRUD.
- **Master Data**
  - Subjects: full CRUD.
  - Categories: full CRUD, linked to Subject (FK pattern example).
- **Question Bank**
  - Questions: full CRUD with dynamic options (checkbox-correct-answer +
    text), subject/category/type/difficulty dropdowns, status workflow
    (`DRAFT` → `PENDING_REVIEW` → `APPROVED`/`REJECTED`).
  - Question Approval: reviewer approve/reject screen.
- **Quiz Management**
  - Quizzes: full CRUD, multi-select approved questions into a quiz,
    duration/marks/pass-mark/shuffle settings.
- **Results**
  - Quiz Results: read-only listing (pass/fail, score).
- **Profile**: view current logged-in user's info.

## 6. What's scaffolded at the data layer only

Every entity in your original module tree has a JPA entity + repository
already generated, even where a controller/JSP hasn't been built yet:
`UserGroup`, `DifficultyLevel`, `QuestionType`, `Tag`, `QuestionAttachment`,
`QuizSchedule`, `QuizAssignment`. This means the database schema is complete;
you're only missing the presentation layer for those screens.

Sidebar links exist for **all** menu items from your spec (Practice,
Notifications, Settings, Reports, Live Monitoring, etc.) so navigation
matches your design, but modules beyond section 4 will 404 until you add a
controller + JSP for them.

## 7. How to add a new module (copy-paste pattern)

Every simple master-data screen (Tags, Difficulty Levels, Question Types,
User Groups) follows the exact same 4-file pattern used for **Subject** and
**Category**. To add e.g. "Tags":

1. **Controller** — copy `CategoryController.java`, rename to
   `TagController`, point it at `TagRepository`, change the view folder to
   `tags/`.
2. **Views** — copy `categories/list.jsp` and `categories/form.jsp` into a
   new `tags/` folder, swap `${category...}` for `${tag...}` and drop the
   subject dropdown (Tag has no FK).
3. **Sidebar** — already linked (`/tags`).
4. Done — no service layer strictly required for simple lookup tables
   (controllers can call the repository directly, as `RoleController` and
   `CategoryController` do).

For modules with real business logic (e.g. **Quiz Schedules**, **Quiz
Assignments**, **Notifications**), follow the **Question**/**Quiz** pattern
instead: Service interface → ServiceImpl → Controller → JSPs, so business
rules stay out of the controller.

## 8. Project Structure

```
src/main/java/com/quizapp/
├── QuizAdminApplication.java     # Spring Boot entrypoint (WAR-capable)
├── config/
│   ├── SecurityConfig.java       # form login, URL authorization rules
│   ├── CustomUserDetails(Service).java
│   ├── DataSeeder.java           # seeds roles/admin user/master data
│   └── GlobalExceptionHandler.java
├── entity/                       # JPA entities (one per DB table)
├── repository/                   # Spring Data JPA repositories
├── service/ + service/impl/      # business logic layer
└── controller/                   # Spring MVC controllers

src/main/webapp/WEB-INF/views/    # JSP views, organized by module
src/main/webapp/resources/css/    # shared admin.css
```

## 9. Security Notes for Production

- Change the seeded admin password immediately after first login.
- Move DB credentials to environment variables, not `application.properties`.
- Set `spring.jpa.hibernate.ddl-auto=validate` once your schema is stable
  (don't run `update` in production).
- Add CSRF-safe AJAX handling if you convert any forms to fetch/XHR (the
  `${_csrf.token}` hidden field pattern used throughout the JSPs already
  covers standard form posts).
- The `/settings/**` and `/audit-logs/**` routes are already restricted to
  `ROLE_ADMIN` in `SecurityConfig` — apply the same restriction to any new
  sensitive module you add (e.g. `/reports/revenue`).

## 10. Notes on the Quiz-Taking / Practice Flow

This is a reference implementation, not a production exam engine. Known
simplifications worth hardening before real use:

- **No answer persistence per question** — only the final aggregate score is
  saved to `QuizResult`/`PracticeAttempt`. If you need review screens
  ("here's what you got wrong"), add a `QuizAnswer` entity capturing
  `(quizResult, question, selectedOption)`.
- **No server-side time enforcement** — the countdown timer in
  `student/quiz-take.jsp` is client-side JS; a determined user could disable
  it. For real exams, stamp `startedAt` when `/take` is first loaded and
  reject `/submit` requests past `startedAt + durationMinutes` server-side.
- **Single-choice grading only** — `submitQuiz()`/`submitPractice()` in
  `StudentController` check one selected option per question. Multi-select
  questions (`MULTIPLE_CHOICE` type) will need checkbox inputs and
  set-comparison grading.
- **Practice questions are pulled live by subject** (not a fixed set saved
  once) — if you add/remove approved questions in a subject, a student's
  next practice attempt pulls a different mix. Add a join entity
  (`PracticeSetQuestion`) if you need a fixed, curated set instead.
- **Retakes are unrestricted** — a student can hit `/take` on the same quiz
  repeatedly, each generating a new `QuizResult`. Add a check against
  existing `QuizResult` rows in `takeQuiz()` if you need one-attempt-only
  enforcement.


## Contributing
Follow existing package layout and naming conventions.
Add tests for new features.
For UI changes include screenshots in PRs.
Document DB migrations and configuration changes in PR descriptions.

## License & contact
Add a LICENSE file (e.g., MIT) if you want to publish the project.
Add a MAINTAINERS.md or a short contact section here with developer/maintainer emails.