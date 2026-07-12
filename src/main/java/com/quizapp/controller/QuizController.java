package com.quizapp.controller;

import com.quizapp.entity.*;
import com.quizapp.repository.QuestionRepository;
import com.quizapp.repository.SubjectRepository;
import com.quizapp.repository.UserRepository;
import com.quizapp.service.QuizScheduleStatusService;
import com.quizapp.service.QuizService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

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
        List<Quiz> quizzes = quizService.findAll();
        model.addAttribute("quizzes", quizzes);
        model.addAttribute("quizStatusMap", quizScheduleStatusService.buildStatusMap(quizzes));
        return "quizzes/list";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("quiz", new Quiz());
        addReferenceData(model);
        return "quizzes/form";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("quiz", quizService.findById(id));
        addReferenceData(model);
        return "quizzes/form";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute Quiz quiz,
                        @RequestParam(value = "questionIds", required = false) List<Long> questionIds,
                        Authentication authentication,
                        RedirectAttributes redirectAttributes) {
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
        return "redirect:/quizzes";
    }

    @GetMapping("/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        quizService.deleteById(id);
        redirectAttributes.addFlashAttribute("successMessage", "Quiz deleted successfully.");
        return "redirect:/quizzes";
    }

    private void addReferenceData(Model model) {
        model.addAttribute("subjects", subjectRepository.findAll());
        model.addAttribute("approvedQuestions",
                questionRepository.findByStatus(QuestionStatus.APPROVED));
    }
}
