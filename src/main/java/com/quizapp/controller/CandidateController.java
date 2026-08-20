package com.quizapp.controller;


import com.quizapp.entity.Question;
import com.quizapp.entity.QuestionOption;
import com.quizapp.entity.QuestionStatus;
import com.quizapp.entity.Quiz;
import com.quizapp.entity.QuizQuestion;
import com.quizapp.entity.QuizResult;
import com.quizapp.entity.PracticeSet;
import com.quizapp.entity.PracticeAttempt;
import com.quizapp.entity.User;
import com.quizapp.entity.UserGroup;
import com.quizapp.repository.AnnouncementRepository;
import com.quizapp.repository.UserRepository;
import com.quizapp.repository.PracticeAttemptRepository;
import com.quizapp.repository.PracticeSetRepository;
import com.quizapp.repository.QuestionRepository;
import com.quizapp.repository.QuizAssignmentRepository;
import com.quizapp.repository.QuizRepository;
import com.quizapp.repository.QuizResultRepository;
import com.quizapp.service.QuizScheduleStatusService;
import com.quizapp.service.ScheduleStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
@Slf4j
@Controller
@RequestMapping("/candidate")
@RequiredArgsConstructor
public class CandidateController {

    private final UserRepository userRepository;
    private final QuizRepository quizRepository;
    private final QuizAssignmentRepository quizAssignmentRepository;
    private final QuizResultRepository quizResultRepository;
    private final QuizScheduleStatusService quizScheduleStatusService;
    private final PracticeSetRepository practiceSetRepository;
    private final PracticeAttemptRepository practiceAttemptRepository;
    private final QuestionRepository questionRepository;
    private final AnnouncementRepository announcementRepository;


    // ---------- Dashboard ----------

    @GetMapping("/dashboard")
    public String dashboard(Authentication authentication, Model model) {
        log.info("Fetching dashboard data for candidate");
        User me = currentUser(authentication);
        List<Quiz> quizzes = assignedQuizzesFor(me);
        model.addAttribute("assignedQuizzes", quizzes);
        model.addAttribute("quizStatusMap", quizScheduleStatusService.buildStatusMap(quizzes));
        model.addAttribute("recentResults", quizResultRepository.findByUserIdAndSubmittedAtIsNotNull(me.getId()));
        model.addAttribute("practiceSets", practiceSetRepository.findAll().stream()
                .filter(PracticeSet::getActive).toList());
        // add active announcements so users see what admins publish
        model.addAttribute("announcements", announcementRepository.findByActiveTrue());
        log.info("Dashboard data added to model for candidate: {}", me.getEmail());
        return "candidate/dashboard";
    }

    // ---------- Available quizzes ----------

    @GetMapping("/quizzes")
    public String availableQuizzes(Authentication authentication, Model model) {
        log.info("Fetching available quizzes for candidate");
        User me = currentUser(authentication);
        List<Quiz> quizzes = assignedQuizzesFor(me);
        model.addAttribute("quizzes", quizzes);
        model.addAttribute("quizStatusMap", quizScheduleStatusService.buildStatusMap(quizzes));
        log.info("Available quizzes added to model for candidate: {}", me.getEmail());
        return "candidate/quizzes";
    }

    private List<Quiz> assignedQuizzesFor(User user) {
        log.info("Fetching assigned quizzes for user: {}", user.getEmail());
        Set<Long> quizIds = new LinkedHashSet<>();

        quizAssignmentRepository.findByUserId(user.getId())
                .forEach(a -> quizIds.add(a.getQuiz().getId()));

        List<Long> groupIds = user.getGroups().stream().map(UserGroup::getId).toList();
        if (!groupIds.isEmpty()) {
            quizAssignmentRepository.findByGroupIdIn(groupIds)
                    .forEach(a -> quizIds.add(a.getQuiz().getId()));
        }
        log.info("Assigned quiz IDs for user {}: {}", user.getEmail(), quizIds);

        return quizIds.stream()
                .map(id -> quizRepository.findById(id).orElse(null))
                .filter(Objects::nonNull)
                .filter(Quiz::getActive)
                .collect(Collectors.toList());
    }

    // ---------- Taking a quiz ----------

    @GetMapping("/quizzes/{id}/take")
    public String takeQuiz(@PathVariable Long id, Authentication authentication,
                            Model model, RedirectAttributes redirectAttributes) {
        log.info("Candidate {} is attempting to take quiz with ID: {}", authentication.getName(), id);
        Quiz quiz = quizRepository.findById(id).orElseThrow();

        ScheduleStatus status = quizScheduleStatusService.resolveStatus(id);
        if (!status.isStartable()) {
            redirectAttributes.addFlashAttribute("errorMessage", scheduleMessage(status));
            log.info("Quiz with ID: {} is not startable for candidate {}. Status: {}", id, authentication.getName(), status);
            return "redirect:/candidate/quizzes";
        }

        User me = currentUser(authentication);
        quizResultRepository.findFirstByQuizIdAndUserIdAndSubmittedAtIsNull(id, me.getId())
                .orElseGet(() -> quizResultRepository.save(QuizResult.builder()
                        .quiz(quiz)
                        .user(me)
                        .scoreObtained(0.0)
                        .totalMarks(quiz.getTotalMarks())
                        .passed(false)
                        .startedAt(LocalDateTime.now())
                        .submittedAt(null)
                        .build()));

        model.addAttribute("quiz", quiz);
        log.info("Quiz with ID: {} is ready for candidate {} to take", id, authentication.getName());
        return "candidate/quiz-take";
    }

    @PostMapping("/quizzes/{id}/submit")
    public String submitQuiz(@PathVariable Long id,
                              @RequestParam Map<String, String> allParams,
                              Authentication authentication,
                              RedirectAttributes redirectAttributes) {
        log.info("Candidate {} is submitting quiz with ID: {}", authentication.getName(), id);
        Quiz quiz = quizRepository.findById(id).orElseThrow();
        User me = currentUser(authentication);


        ScheduleStatus status = quizScheduleStatusService.resolveStatus(id);
        if (!status.isStartable()) {
            redirectAttributes.addFlashAttribute("errorMessage", scheduleMessage(status));
            log.info("Quiz with ID: {} is not startable for candidate {} upon submission. Status: {}", id, authentication.getName(), status);
            return "redirect:/candidate/quizzes";
        }

        int totalMarks = 0;
        double scoreObtained = 0;

        for (QuizQuestion qq : quiz.getQuizQuestions()) {
            Question question = qq.getQuestion();
            totalMarks += question.getMarks();

            String selectedOptionId = allParams.get("answers[" + question.getId() + "]");
            if (selectedOptionId == null) continue;

            for (QuestionOption option : question.getOptions()) {
                if (option.getId().toString().equals(selectedOptionId)) {
                    if (Boolean.TRUE.equals(option.getIsCorrect())) {
                        scoreObtained += question.getMarks();
                    } else {
                        scoreObtained -= question.getNegativeMarks();
                    }
                }
            }
        }

        boolean passed = scoreObtained >= quiz.getPassingMarks();

        // Reuse the "in progress" row created when the candidate opened the
        // quiz (so it disappears from Live Monitoring the moment they
        // submit) — falling back to creating a fresh row if, for some
        // reason, none was found.
        QuizResult result = quizResultRepository
                .findFirstByQuizIdAndUserIdAndSubmittedAtIsNull(id, me.getId())
                .orElseGet(() -> QuizResult.builder()
                        .quiz(quiz)
                        .user(me)
                        .startedAt(LocalDateTime.now())
                        .build());

        result.setScoreObtained(scoreObtained);
        result.setTotalMarks(totalMarks);
        result.setPassed(passed);
        result.setSubmittedAt(LocalDateTime.now());
        quizResultRepository.save(result);

        redirectAttributes.addFlashAttribute("successMessage",
                "Quiz submitted! Score: " + scoreObtained + " / " + totalMarks +
                (passed ? " — Passed" : " — Not passed"));
        log.info("Quiz with ID: {} submitted by candidate {}. Score: {} / {}. Passed: {}", id, authentication.getName(), scoreObtained, totalMarks, passed);
        return "redirect:/candidate/results";
    }

    private String scheduleMessage(ScheduleStatus status) {
        return switch (status) {
            case UPCOMING -> "This quiz is not open yet — it hasn't reached its scheduled start time.";
            case EXPIRED -> "This quiz is now closed — its scheduled time window has ended.";
            default -> "This quiz is not currently available.";
        };
    }

    // ---------- Results ----------

    @GetMapping("/results")
    public String myResults(Authentication authentication, Model model) {
        log.info("Fetching quiz results for candidate: {}", authentication.getName());
        User me = currentUser(authentication);
        model.addAttribute("results", quizResultRepository.findByUserIdAndSubmittedAtIsNotNull(me.getId()));
        log.info("Fetching quiz results for candidate: {}", me.getEmail());
        return "candidate/results";
    }

    // ---------- Practice ----------

    @GetMapping("/practice")
    public String practiceSets(Model model) {
        model.addAttribute("practiceSets", practiceSetRepository.findAll().stream()
                .filter(PracticeSet::getActive).toList());
        return "candidate/practice";
    }

    @GetMapping("/practice/{id}/take")
    public String takePractice(@PathVariable Long id, Model model) {
        log.info("Fetching practice set with ID: {}", id);
        PracticeSet practiceSet = practiceSetRepository.findById(id).orElseThrow();

        List<Question> questions = practiceSet.getSubject() != null
                ? questionRepository.findBySubjectId(practiceSet.getSubject().getId()).stream()
                    .filter(q -> q.getStatus() == QuestionStatus.APPROVED)
                    .limit(10)
                    .toList()
                : questionRepository.findByStatus(QuestionStatus.APPROVED).stream().limit(10).toList();

        model.addAttribute("practiceSet", practiceSet);
        model.addAttribute("questions", questions);
        log.info("Practice set with ID: {} and {} questions fetched for practice", id, questions.size());
        return "candidate/practice-take";
    }

    @PostMapping("/practice/{id}/submit")
    public String submitPractice(@PathVariable Long id,
                                  @RequestParam Map<String, String> allParams,
                                  Authentication authentication,
                                  RedirectAttributes redirectAttributes) {
        log.info("Candidate {} is submitting practice set with ID: {}", authentication.getName(), id);
        PracticeSet practiceSet = practiceSetRepository.findById(id).orElseThrow();
        User me = currentUser(authentication);

        List<Question> questions = practiceSet.getSubject() != null
                ? questionRepository.findBySubjectId(practiceSet.getSubject().getId()).stream()
                    .filter(q -> q.getStatus() == QuestionStatus.APPROVED)
                    .limit(10)
                    .toList()
                : questionRepository.findByStatus(QuestionStatus.APPROVED).stream().limit(10).toList();

        int total = questions.size();
        int correct = 0;
        for (Question question : questions) {
            String selectedOptionId = allParams.get("answers[" + question.getId() + "]");
            if (selectedOptionId == null) continue;
            for (QuestionOption option : question.getOptions()) {
                if (option.getId().toString().equals(selectedOptionId) && Boolean.TRUE.equals(option.getIsCorrect())) {
                    correct++;
                }
            }
        }

        double scorePercent = total == 0 ? 0 : (correct * 100.0 / total);

        PracticeAttempt attempt = PracticeAttempt.builder()
                .practiceSet(practiceSet)
                .user(me)
                .scoreObtained(scorePercent)
                .build();
        practiceAttemptRepository.save(attempt);

        redirectAttributes.addFlashAttribute("successMessage",
                "Practice submitted! You scored " + correct + " / " + total +
                " (" + String.format("%.1f", scorePercent) + "%)");
        log.info("Practice set with ID: {} submitted by candidate {}. Score: {} / {} ({}%)", id, authentication.getName(), correct, total, scorePercent);
        return "redirect:/candidate/practice-history";
    }

    @GetMapping("/practice-history")
    public String practiceHistory(Authentication authentication, Model model) {
        log.info("Fetching practice history for candidate: {}", authentication.getName());
        User me = currentUser(authentication);
        model.addAttribute("attempts", practiceAttemptRepository.findByUserId(me.getId()));
        log.info("Practice history fetched for candidate: {}", me.getEmail());
        return "candidate/practice-history";
    }

    private User currentUser(Authentication authentication) {
        return userRepository.findByEmail(authentication.getName()).orElseThrow();
    }
}
