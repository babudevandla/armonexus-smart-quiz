package com.quizapp.controller;

import com.quizapp.repository.AuditLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class AuditLogController {

    private final AuditLogRepository auditLogRepository;

    @GetMapping("/audit-logs")
    public String list(Model model) {
        model.addAttribute("logs", auditLogRepository.findAll());
        return "auditlogs/list";
    }
}
