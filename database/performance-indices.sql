-- ============================================================
-- Performance indices for Quiz Admin Portal
--
-- Hibernate's ddl-auto=update creates indices on @ManyToOne foreign key
-- columns automatically, but NOT on plain columns the app filters/sorts on
-- heavily (status enums, submitted_at, active flags, email lookups, etc).
-- Run this once after the schema exists (after first app startup, or after
-- loading seed-master-data-dump.sql) to add those.
--
-- All statements use `CREATE INDEX IF NOT EXISTS` equivalents via a
-- procedure guard, since plain MySQL (<8.0.29) doesn't support
-- "IF NOT EXISTS" on CREATE INDEX directly — this makes the script safe
-- to re-run without erroring on existing indices.
-- ============================================================

DELIMITER $$

DROP PROCEDURE IF EXISTS add_index_if_missing $$
CREATE PROCEDURE add_index_if_missing(
    IN tbl VARCHAR(64), IN idx VARCHAR(64), IN cols VARCHAR(255)
)
BEGIN
    IF NOT EXISTS (
        SELECT 1 FROM information_schema.statistics
        WHERE table_schema = DATABASE() AND table_name = tbl AND index_name = idx
    ) THEN
        SET @ddl = CONCAT('CREATE INDEX ', idx, ' ON ', tbl, ' (', cols, ')');
        PREPARE stmt FROM @ddl;
        EXECUTE stmt;
        DEALLOCATE PREPARE stmt;
    END IF;
END $$

DELIMITER ;

-- ---- Question Bank: status is filtered on every list/approval-queue page ----
CALL add_index_if_missing('questions', 'idx_questions_status', 'status');
CALL add_index_if_missing('questions', 'idx_questions_subject_status', 'subject_id, status');
CALL add_index_if_missing('questions', 'idx_questions_created_by', 'created_by');

-- ---- Quiz Management: active flag + schedule date range checks ----
CALL add_index_if_missing('quizzes', 'idx_quizzes_active', 'active');
CALL add_index_if_missing('quizzes', 'idx_quizzes_created_by', 'created_by');
CALL add_index_if_missing('quiz_schedules', 'idx_schedules_quiz_active', 'quiz_id, active');
CALL add_index_if_missing('quiz_schedules', 'idx_schedules_window', 'start_time, end_time');

-- ---- Results / Live Monitoring: submitted_at IS NULL is queried constantly ----
CALL add_index_if_missing('quiz_results', 'idx_results_submitted_at', 'submitted_at');
CALL add_index_if_missing('quiz_results', 'idx_results_user_submitted', 'user_id, submitted_at');
CALL add_index_if_missing('quiz_results', 'idx_results_quiz_id', 'quiz_id');
CALL add_index_if_missing('quiz_results', 'idx_results_passed', 'passed');

-- ---- Users: email is the login lookup, hit on every request ----
CALL add_index_if_missing('users', 'idx_users_email', 'email');
CALL add_index_if_missing('users', 'idx_users_enabled', 'enabled');

-- ---- Practice ----
CALL add_index_if_missing('practice_attempts', 'idx_practice_user', 'user_id');
CALL add_index_if_missing('practice_attempts', 'idx_practice_set', 'practice_set_id');

-- ---- Audit Logs: usually queried/sorted by timestamp descending ----
CALL add_index_if_missing('audit_logs', 'idx_audit_timestamp', 'timestamp');

-- ---- Invitations ----
CALL add_index_if_missing('invitations', 'idx_invitations_status', 'status');
CALL add_index_if_missing('invitations', 'idx_invitations_quiz', 'quiz_id');

DROP PROCEDURE IF EXISTS add_index_if_missing;

-- ============================================================
-- Verify indices were created:
--   SHOW INDEX FROM questions;
--   SHOW INDEX FROM quiz_results;
-- ============================================================
