package com.quizapp.service;

import com.quizapp.entity.Quiz;

import java.util.List;
import java.util.Map;

/**
 * Single source of truth for "is this quiz open right now" — used by the
 * Candidate quiz-taking flow AND by every admin-facing screen that lists
 * quizzes, so the schedule window is enforced and displayed consistently
 * everywhere in the app.
 */
public interface QuizScheduleStatusService {

    ScheduleStatus resolveStatus(Long quizId);

    /** Convenience for list/table views — one lookup per quiz, keyed by quiz id. */
    Map<Long, ScheduleStatus> buildStatusMap(List<Quiz> quizzes);
}
