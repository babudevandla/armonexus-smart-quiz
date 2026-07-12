package com.quizapp.service;

/**
 * Availability of a quiz relative to its QuizSchedule row(s).
 *
 * NOT_SCHEDULED - the quiz has no active schedule at all, so it's treated
 *                 as always open (backward compatible with unscheduled quizzes).
 * OPEN          - "now" falls inside at least one active schedule window.
 * UPCOMING      - every active schedule's start time is still in the future.
 * EXPIRED       - every active schedule's end time has already passed.
 */
public enum ScheduleStatus {
    NOT_SCHEDULED, OPEN, UPCOMING, EXPIRED;

    public boolean isStartable() {
        return this == NOT_SCHEDULED || this == OPEN;
    }
}
