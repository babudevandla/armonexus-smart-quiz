package com.quizapp.controller;

import com.quizapp.entity.QuizResult;
import com.quizapp.repository.QuizResultRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.nio.charset.StandardCharsets;

@Controller
@RequiredArgsConstructor
public class ExportReportController {

    private final QuizResultRepository quizResultRepository;

    @GetMapping("/export-reports")
    public String page() {
        return "export-reports";
    }

    @GetMapping("/export-reports/results.csv")
    public ResponseEntity<byte[]> exportResultsCsv() {
        StringBuilder csv = new StringBuilder("User,Quiz,Score,Total,Passed,SubmittedAt\n");
        // completed attempts only — exporting an in-progress attempt makes no sense
        for (QuizResult r : quizResultRepository.findBySubmittedAtIsNotNull()) {
            csv.append(escape(r.getUser().getFullName())).append(",")
               .append(escape(r.getQuiz().getTitle())).append(",")
               .append(r.getScoreObtained()).append(",")
               .append(r.getTotalMarks()).append(",")
               .append(r.getPassed()).append(",")
               .append(r.getSubmittedAt()).append("\n");
        }

        byte[] bytes = csv.toString().getBytes(StandardCharsets.UTF_8);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=quiz-results.csv")
                .contentType(MediaType.parseMediaType("text/csv"))
                .body(bytes);
    }

    private String escape(String value) {
        if (value == null) return "";
        return "\"" + value.replace("\"", "'") + "\"";
    }
}
