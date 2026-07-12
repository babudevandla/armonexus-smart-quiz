package com.quizapp.service.impl;

import com.quizapp.entity.Quiz;
import com.quizapp.entity.QuizSchedule;
import com.quizapp.repository.QuizScheduleRepository;
import com.quizapp.service.QuizScheduleStatusService;
import com.quizapp.service.ScheduleStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class QuizScheduleStatusServiceImpl implements QuizScheduleStatusService {

    private final QuizScheduleRepository quizScheduleRepository;

    @Override
    public ScheduleStatus resolveStatus(Long quizId) {
        List<QuizSchedule> schedules = quizScheduleRepository.findByQuizId(quizId).stream()
                .filter(QuizSchedule::getActive)
                .toList();

        if (schedules.isEmpty()) {
            return ScheduleStatus.NOT_SCHEDULED; // never scheduled = always open
        }

        LocalDateTime now = LocalDateTime.now();
        boolean hasUpcoming = false;

        for (QuizSchedule s : schedules) {
            boolean afterStart = s.getStartTime() == null || !now.isBefore(s.getStartTime());
            boolean beforeEnd = s.getEndTime() == null || !now.isAfter(s.getEndTime());
            if (afterStart && beforeEnd) {
                return ScheduleStatus.OPEN; // inside at least one active window right now
            }
            if (s.getStartTime() != null && now.isBefore(s.getStartTime())) {
                hasUpcoming = true;
            }
        }

        return hasUpcoming ? ScheduleStatus.UPCOMING : ScheduleStatus.EXPIRED;
    }

    @Override
    public Map<Long, ScheduleStatus> buildStatusMap(List<Quiz> quizzes) {
        Map<Long, ScheduleStatus> map = new HashMap<>();
        for (Quiz quiz : quizzes) {
            map.put(quiz.getId(), resolveStatus(quiz.getId()));
        }
        return map;
    }
}
