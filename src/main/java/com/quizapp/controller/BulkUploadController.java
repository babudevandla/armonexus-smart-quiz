package com.quizapp.controller;

import com.quizapp.entity.*;
import com.quizapp.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * Bulk question upload via CSV.
 * Expected CSV columns (header row required):
 * questionText,subjectId,categoryId,questionTypeId,difficultyId,marks,negativeMarks,option1,option2,option3,option4,correctOptionIndex
 *
 * correctOptionIndex is 1-based (1 = option1 is correct, etc).
 */
@Controller
@RequestMapping("/questions/bulk-upload")
@RequiredArgsConstructor
public class BulkUploadController {

    private final QuestionRepository questionRepository;
    private final SubjectRepository subjectRepository;
    private final CategoryRepository categoryRepository;
    private final QuestionTypeRepository questionTypeRepository;
    private final DifficultyLevelRepository difficultyLevelRepository;

    @GetMapping
    public String form() {
        return "questions/bulk-upload";
    }

    @PostMapping
    public String upload(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {
        int successCount = 0;
        List<String> errors = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8))) {

            String header = reader.readLine(); // skip header row
            String line;
            int rowNum = 1;

            while ((line = reader.readLine()) != null) {
                rowNum++;
                if (line.isBlank()) continue;

                try {
                    String[] cols = line.split(",", -1);
                    Question q = new Question();
                    q.setQuestionText(cols[0].trim());
                    q.setSubject(subjectRepository.findById(Long.parseLong(cols[1].trim())).orElse(null));
                    q.setCategory(categoryRepository.findById(Long.parseLong(cols[2].trim())).orElse(null));
                    q.setQuestionType(questionTypeRepository.findById(Long.parseLong(cols[3].trim())).orElse(null));
                    q.setDifficultyLevel(difficultyLevelRepository.findById(Long.parseLong(cols[4].trim())).orElse(null));
                    q.setMarks(Integer.parseInt(cols[5].trim()));
                    q.setNegativeMarks(Integer.parseInt(cols[6].trim()));
                    q.setStatus(QuestionStatus.PENDING_REVIEW);

                    List<QuestionOption> options = new ArrayList<>();
                    int correctIndex = Integer.parseInt(cols[11].trim());
                    for (int i = 0; i < 4; i++) {
                        String optText = cols[7 + i].trim();
                        if (optText.isEmpty()) continue;
                        QuestionOption opt = QuestionOption.builder()
                                .question(q)
                                .optionText(optText)
                                .isCorrect((i + 1) == correctIndex)
                                .optionOrder(i + 1)
                                .build();
                        options.add(opt);
                    }
                    q.setOptions(options);

                    questionRepository.save(q);
                    successCount++;
                } catch (Exception rowEx) {
                    errors.add("Row " + rowNum + ": " + rowEx.getMessage());
                }
            }
        } catch (IOException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Could not read file: " + e.getMessage());
            return "redirect:/questions/bulk-upload";
        }

        redirectAttributes.addFlashAttribute("successMessage",
                successCount + " question(s) imported successfully." +
                (errors.isEmpty() ? "" : " " + errors.size() + " row(s) failed: " + String.join("; ", errors)));
        return "redirect:/questions/bulk-upload";
    }
}
