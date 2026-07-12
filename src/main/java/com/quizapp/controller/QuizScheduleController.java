package com.quizapp.controller;

import com.quizapp.entity.QuizSchedule;
import com.quizapp.repository.QuizRepository;
import com.quizapp.repository.QuizScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/quiz-schedules")
@RequiredArgsConstructor
public class QuizScheduleController {

    private final QuizScheduleRepository quizScheduleRepository;
    private final QuizRepository quizRepository;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("schedules", quizScheduleRepository.findAll());
        return "quizschedules/list";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("schedule", new QuizSchedule());
        model.addAttribute("quizzes", quizRepository.findAll());
        return "quizschedules/form";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("schedule", quizScheduleRepository.findById(id).orElseThrow());
        model.addAttribute("quizzes", quizRepository.findAll());
        return "quizschedules/form";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute QuizSchedule schedule, RedirectAttributes redirectAttributes) {
        quizScheduleRepository.save(schedule);
        redirectAttributes.addFlashAttribute("successMessage", "Quiz schedule saved successfully.");
        return "redirect:/quiz-schedules";
    }

    @GetMapping("/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        quizScheduleRepository.deleteById(id);
        redirectAttributes.addFlashAttribute("successMessage", "Quiz schedule deleted successfully.");
        return "redirect:/quiz-schedules";
    }
}
