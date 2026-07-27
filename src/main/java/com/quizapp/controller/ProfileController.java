package com.quizapp.controller;

import com.quizapp.entity.User;
import com.quizapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ProfileController {

    private final UserRepository userRepository;

    @GetMapping("/profile")
    public String profile(Authentication authentication, Model model) {
        model.addAttribute("user",
                userRepository.findByEmail(authentication.getName()).orElseThrow());
        return "profile";
    }

    @GetMapping("/profile/edit")
    public String editProfile(Authentication authentication, Model model) {
        model.addAttribute("user",
                userRepository.findByEmail(authentication.getName()).orElseThrow());
        return "editProfile";
    }

    @PostMapping("/profile/update")
    public String updateProfile(@ModelAttribute User updatedUser,
                                Authentication authentication) {

        User user = userRepository.findByEmail(authentication.getName()).orElseThrow();

        log.info("Form Name: {}", updatedUser.getFullName());
        log.info("Form Phone: {}", updatedUser.getPhone());

        log.info("Before Save: {} - {}", user.getFullName(), user.getPhone());

        user.setFullName(updatedUser.getFullName());
        user.setPhone(updatedUser.getPhone());

        userRepository.save(user);

        User saved = userRepository.findByEmail(authentication.getName()).orElseThrow();

        log.info("After Save: {} - {}", saved.getFullName(), saved.getPhone());

        return "redirect:/profile";
    }
}