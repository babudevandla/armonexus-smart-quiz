package com.quizapp.controller;

import com.quizapp.entity.Question;
import com.quizapp.entity.QuestionStatus;
import com.quizapp.entity.Quiz;
import com.quizapp.entity.QuizQuestion;
import com.quizapp.entity.User;
import com.quizapp.repository.QuestionRepository;
import com.quizapp.repository.SubjectRepository;
import com.quizapp.repository.UserRepository;
import com.quizapp.service.QuizScheduleStatusService;
import com.quizapp.service.QuizService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;
@Slf4j
@Controller
@RequestMapping("/quizzes")
@RequiredArgsConstructor
public class QuizController {

    private final QuizService quizService;
    private final SubjectRepository subjectRepository;
    private final QuestionRepository questionRepository;
    private final UserRepository userRepository;
    private final QuizScheduleStatusService quizScheduleStatusService;

    @GetMapping
    public String list(Model model) {
        log.info("Fetching all quizzes");
        List<Quiz> quizzes = quizService.findAll();
        model.addAttribute("quizzes", quizzes);
        model.addAttribute("quizStatusMap", quizScheduleStatusService.buildStatusMap(quizzes));
        log.info("Quizzes and their statuses added to model");
        return "quizzes/list";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        log.info("Displaying form for creating a new quiz");
        model.addAttribute("quiz", new Quiz());
        addReferenceData(model);
        log.info("New quiz object added to model");
        return "quizzes/form";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        log.info("Displaying form for editing quiz with ID: {}", id);
        model.addAttribute("quiz", quizService.findById(id));
        addReferenceData(model);
        log.info("Quiz object added to model");
        return "quizzes/form";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute Quiz quiz,
                        @RequestParam(value = "questionIds", required = false) List<Long> questionIds,
                        Authentication authentication,
                        RedirectAttributes redirectAttributes) {
        log.info("Saving quiz: {}", quiz);
        if (quiz.getId() == null) {
            User currentUser = userRepository.findByEmail(authentication.getName()).orElse(null);
            quiz.setCreatedBy(currentUser);
        }

        List<QuizQuestion> quizQuestions = new ArrayList<>();
        if (questionIds != null) {
            int order = 1;
            for (Long qid : questionIds) {
                Question question = questionRepository.findById(qid).orElse(null);
                if (question != null) {
                    quizQuestions.add(QuizQuestion.builder()
                            .quiz(quiz)
                            .question(question)
                            .questionOrder(order++)
                            .build());
                }
            }
        }
        quiz.setQuizQuestions(quizQuestions);

        quizService.save(quiz);
        redirectAttributes.addFlashAttribute("successMessage", "Quiz saved successfully.");
        log.info("Quiz saved successfully: {}", quiz);
        return "redirect:/quizzes";
    }

    @GetMapping("/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        log.info("Attempting to delete quiz with ID: {}", id);
        try {
            quizService.deleteById(id);
            redirectAttributes.addFlashAttribute("successMessage", "Quiz deleted successfully.");
        } catch (DataIntegrityViolationException e) {
            redirectAttributes.addFlashAttribute("errorMessage",
                    "This quiz cannot be deleted because it is connected to quiz results or other related records.");
        }
        log.info("Quiz with ID: {} deletion attempted", id);
        return "redirect:/quizzes";
    }

    private void addReferenceData(Model model) {
        model.addAttribute("subjects", subjectRepository.findAll());
        model.addAttribute("approvedQuestions",
                questionRepository.findByStatus(QuestionStatus.APPROVED));
    }
}
