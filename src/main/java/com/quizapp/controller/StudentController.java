package com.quizapp.controller;

import com.quizapp.entity.*;
import com.quizapp.repository.*;
import com.quizapp.service.QuizScheduleStatusService;
import com.quizapp.service.ScheduleStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/student")
@RequiredArgsConstructor
public class StudentController {

    private final UserRepository userRepository;
    private final QuizRepository quizRepository;
    private final QuizAssignmentRepository quizAssignmentRepository;
    private final QuizResultRepository quizResultRepository;
    private final QuizScheduleStatusService quizScheduleStatusService;
    private final PracticeSetRepository practiceSetRepository;
    private final PracticeAttemptRepository practiceAttemptRepository;
    private final QuestionRepository questionRepository;

    // ---------- Dashboard ----------

    @GetMapping("/dashboard")
    public String dashboard(Authentication authentication, Model model) {
        User me = currentUser(authentication);
        List<Quiz> quizzes = assignedQuizzesFor(me);
        model.addAttribute("assignedQuizzes", quizzes);
        model.addAttribute("quizStatusMap", quizScheduleStatusService.buildStatusMap(quizzes));
        model.addAttribute("recentResults", quizResultRepository.findByUserIdAndSubmittedAtIsNotNull(me.getId()));
        model.addAttribute("practiceSets", practiceSetRepository.findAll().stream()
                .filter(PracticeSet::getActive).toList());
        return "student/dashboard";
    }

    // ---------- Available quizzes ----------

    @GetMapping("/quizzes")
    public String availableQuizzes(Authentication authentication, Model model) {
        User me = currentUser(authentication);
        List<Quiz> quizzes = assignedQuizzesFor(me);
        model.addAttribute("quizzes", quizzes);
        model.addAttribute("quizStatusMap", quizScheduleStatusService.buildStatusMap(quizzes));
        return "student/quizzes";
    }

    private List<Quiz> assignedQuizzesFor(User user) {
        Set<Long> quizIds = new LinkedHashSet<>();

        quizAssignmentRepository.findByUserId(user.getId())
                .forEach(a -> quizIds.add(a.getQuiz().getId()));

        List<Long> groupIds = user.getGroups().stream().map(UserGroup::getId).toList();
        if (!groupIds.isEmpty()) {
            quizAssignmentRepository.findByGroupIdIn(groupIds)
                    .forEach(a -> quizIds.add(a.getQuiz().getId()));
        }

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
        Quiz quiz = quizRepository.findById(id).orElseThrow();

        ScheduleStatus status = quizScheduleStatusService.resolveStatus(id);
        if (!status.isStartable()) {
            redirectAttributes.addFlashAttribute("errorMessage", scheduleMessage(status));
            return "redirect:/student/quizzes";
        }

        User me = currentUser(authentication);

        // Write an "in progress" row (submittedAt still null) the moment the
        // student opens the quiz, so Live Monitoring can actually see them.
        // If they reload/reopen the same quiz, reuse the existing row instead
        // of creating duplicates.
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
        return "student/quiz-take";
    }

    @PostMapping("/quizzes/{id}/submit")
    public String submitQuiz(@PathVariable Long id,
                              @RequestParam Map<String, String> allParams,
                              Authentication authentication,
                              RedirectAttributes redirectAttributes) {
        Quiz quiz = quizRepository.findById(id).orElseThrow();
        User me = currentUser(authentication);

        // Server-side re-check: never grade/save a submission for a quiz
        // whose schedule window has since closed (covers a browser tab left
        // open past the end time, or a direct POST bypassing the UI).
        ScheduleStatus status = quizScheduleStatusService.resolveStatus(id);
        if (!status.isStartable()) {
            redirectAttributes.addFlashAttribute("errorMessage", scheduleMessage(status));
            return "redirect:/student/quizzes";
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

        // Reuse the "in progress" row created when the student opened the
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
        return "redirect:/student/results";
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
        User me = currentUser(authentication);
        model.addAttribute("results", quizResultRepository.findByUserIdAndSubmittedAtIsNotNull(me.getId()));
        return "student/results";
    }

    // ---------- Practice ----------

    @GetMapping("/practice")
    public String practiceSets(Model model) {
        model.addAttribute("practiceSets", practiceSetRepository.findAll().stream()
                .filter(PracticeSet::getActive).toList());
        return "student/practice";
    }

    @GetMapping("/practice/{id}/take")
    public String takePractice(@PathVariable Long id, Model model) {
        PracticeSet practiceSet = practiceSetRepository.findById(id).orElseThrow();

        List<Question> questions = practiceSet.getSubject() != null
                ? questionRepository.findBySubjectId(practiceSet.getSubject().getId()).stream()
                    .filter(q -> q.getStatus() == QuestionStatus.APPROVED)
                    .limit(10)
                    .toList()
                : questionRepository.findByStatus(QuestionStatus.APPROVED).stream().limit(10).toList();

        model.addAttribute("practiceSet", practiceSet);
        model.addAttribute("questions", questions);
        return "student/practice-take";
    }

    @PostMapping("/practice/{id}/submit")
    public String submitPractice(@PathVariable Long id,
                                  @RequestParam Map<String, String> allParams,
                                  Authentication authentication,
                                  RedirectAttributes redirectAttributes) {
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
        return "redirect:/student/practice-history";
    }

    @GetMapping("/practice-history")
    public String practiceHistory(Authentication authentication, Model model) {
        User me = currentUser(authentication);
        model.addAttribute("attempts", practiceAttemptRepository.findByUserId(me.getId()));
        return "student/practice-history";
    }

    private User currentUser(Authentication authentication) {
        return userRepository.findByEmail(authentication.getName()).orElseThrow();
    }
}
