package com.quizapp.controller;

import com.quizapp.entity.User;
import com.quizapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;




@Controller
@RequiredArgsConstructor
public class ProfileController {

    private final UserRepository userRepository;
    // removed incorrect/unused userService field

    @GetMapping("/profile")
    public String profile(Authentication authentication, Model model) {
        model.addAttribute("user", userRepository.findByEmail(authentication.getName()).orElseThrow());
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

        System.out.println("Form Name: " + updatedUser.getFullName());
        System.out.println("Form Phone: " + updatedUser.getPhone());

        System.out.println("Before Save: " + user.getFullName() + " - " + user.getPhone());

        user.setFullName(updatedUser.getFullName());
        user.setPhone(updatedUser.getPhone());

        userRepository.save(user);

        User saved = userRepository.findByEmail(authentication.getName()).orElseThrow();

        System.out.println("After Save: " + saved.getFullName() + " - " + saved.getPhone());

        return "redirect:/profile";
    }

}
