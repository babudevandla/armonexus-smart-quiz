package com.quizapp.controller;

import com.quizapp.entity.Question;
import com.quizapp.entity.QuestionAttachment;
import com.quizapp.repository.QuestionAttachmentRepository;
import com.quizapp.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

@Controller
@RequestMapping("/question-attachments")
@RequiredArgsConstructor
public class QuestionAttachmentController {

    private final QuestionAttachmentRepository attachmentRepository;
    private final QuestionRepository questionRepository;

    private static final String UPLOAD_DIR = "uploads/question-attachments/";

    @GetMapping
    public String list(Model model) {
        model.addAttribute("attachments", attachmentRepository.findAll());
        model.addAttribute("questions", questionRepository.findAll());
        return "questionattachments/list";
    }

    @PostMapping("/upload")
    public String upload(@RequestParam("questionId") Long questionId,
                          @RequestParam("file") MultipartFile file,
                          RedirectAttributes redirectAttributes) {
        try {
            Question question = questionRepository.findById(questionId).orElseThrow();

            Path dir = Path.of(UPLOAD_DIR);
            Files.createDirectories(dir);

            String storedName = UUID.randomUUID() + "_" + file.getOriginalFilename();
            Path target = dir.resolve(storedName);
            file.transferTo(target);

            QuestionAttachment attachment = QuestionAttachment.builder()
                    .question(question)
                    .fileName(file.getOriginalFilename())
                    .filePath(target.toString())
                    .fileType(file.getContentType())
                    .build();
            attachmentRepository.save(attachment);

            redirectAttributes.addFlashAttribute("successMessage", "Attachment uploaded successfully.");
        } catch (IOException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Upload failed: " + e.getMessage());
        }
        return "redirect:/question-attachments";
    }

    @GetMapping("/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        attachmentRepository.findById(id).ifPresent(a -> {
            if (a.getFilePath() != null) {
                new File(a.getFilePath()).delete();
            }
            attachmentRepository.deleteById(id);
        });
        redirectAttributes.addFlashAttribute("successMessage", "Attachment deleted.");
        return "redirect:/question-attachments";
    }
}
