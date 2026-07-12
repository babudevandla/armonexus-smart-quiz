package com.quizapp.service.impl;

import com.quizapp.entity.QuizSchedule;
import com.quizapp.repository.QuizScheduleRepository;
import com.quizapp.service.ScheduleStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Single source of truth for "is this quiz open right now?" — used by the
 * Student take/submit flow, the Admin Quiz Management list, the Instructor's
 * My Quizzes list, and Live Monitoring, so a quiz reads as closed/expired
 * consistently everywhere the moment its schedule window ends.
 */
@Service
@RequiredArgsConstructor
public class QuizScheduleStatusService {

    private final QuizScheduleRepository quizScheduleRepository;

    public ScheduleStatus resolve(Long quizId) {
        List<QuizSchedule> schedules = quizScheduleRepository.findByQuizId(quizId).stream()
                .filter(QuizSchedule::getActive)
                .toList();

        if (schedules.isEmpty()) {
            return ScheduleStatus.NOT_SCHEDULED;
        }

        LocalDateTime now = LocalDateTime.now();
        boolean hasUpcoming = false;

        for (QuizSchedule s : schedules) {
            boolean afterStart = s.getStartTime() == null || !now.isBefore(s.getStartTime());
            boolean beforeEnd = s.getEndTime() == null || !now.isAfter(s.getEndTime());
            if (afterStart && beforeEnd) {
                return ScheduleStatus.OPEN; // inside at least one active window
            }
            if (s.getStartTime() != null && now.isBefore(s.getStartTime())) {
                hasUpcoming = true;
            }
        }

        return hasUpcoming ? ScheduleStatus.UPCOMING : ScheduleStatus.EXPIRED;
    }

    /** Batch version so list pages don't run one query per row. */
    public Map<Long, ScheduleStatus> resolveForAll(List<Long> quizIds) {
        Map<Long, ScheduleStatus> map = new HashMap<>();
        for (Long id : quizIds) {
            map.put(id, resolve(id));
        }
        return map;
    }
}
