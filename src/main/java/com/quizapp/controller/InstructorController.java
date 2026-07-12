package com.quizapp.controller;

import com.quizapp.entity.Quiz;
import com.quizapp.entity.QuestionStatus;
import com.quizapp.entity.User;
import com.quizapp.repository.QuestionRepository;
import com.quizapp.repository.QuizRepository;
import com.quizapp.repository.UserRepository;
import com.quizapp.service.QuizScheduleStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/instructor")
@RequiredArgsConstructor
public class InstructorController {

    private final QuestionRepository questionRepository;
    private final QuizRepository quizRepository;
    private final UserRepository userRepository;
    private final QuizScheduleStatusService quizScheduleStatusService;

    @GetMapping("/dashboard")
    public String dashboard(Authentication authentication, Model model) {
        User me = currentUser(authentication);

        List<?> myQuestions = questionRepository.findByCreatedById(me.getId());
        long approved = myQuestions.stream()
                .filter(q -> ((com.quizapp.entity.Question) q).getStatus() == QuestionStatus.APPROVED).count();
        long pending = myQuestions.stream()
                .filter(q -> ((com.quizapp.entity.Question) q).getStatus() == QuestionStatus.PENDING_REVIEW).count();
        long rejected = myQuestions.stream()
                .filter(q -> ((com.quizapp.entity.Question) q).getStatus() == QuestionStatus.REJECTED).count();

        model.addAttribute("myQuestionCount", myQuestions.size());
        model.addAttribute("myApprovedCount", approved);
        model.addAttribute("myPendingCount", pending);
        model.addAttribute("myRejectedCount", rejected);
        model.addAttribute("myQuizCount", quizRepository.findByCreatedById(me.getId()).size());
        return "instructor/dashboard";
    }

    @GetMapping("/questions")
    public String myQuestions(Authentication authentication, Model model) {
        User me = currentUser(authentication);
        model.addAttribute("questions", questionRepository.findByCreatedById(me.getId()));
        return "questions/list"; // reuse the same list view used by the admin Question Bank
    }

    @GetMapping("/quizzes")
    public String myQuizzes(Authentication authentication, Model model) {
        User me = currentUser(authentication);
        List<Quiz> quizzes = quizRepository.findByCreatedById(me.getId());
        model.addAttribute("quizzes", quizzes);
        model.addAttribute("quizStatusMap", quizScheduleStatusService.buildStatusMap(quizzes));
        return "quizzes/list"; // reuse the same list view used by admin Quiz Management
    }

    private User currentUser(Authentication authentication) {
        return userRepository.findByEmail(authentication.getName()).orElseThrow();
    }
}
