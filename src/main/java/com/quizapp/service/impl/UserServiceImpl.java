package com.quizapp.service.impl;

import com.quizapp.entity.Role;
import com.quizapp.entity.User;
import com.quizapp.repository.RoleRepository;
import com.quizapp.repository.UserRepository;
import com.quizapp.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id " + id));
    }

    @Override
    @Transactional
    public User save(User user, Set<Long> roleIds, boolean isNew) {
        if (isNew) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        } else {
            // keep existing password if the form field was left blank on edit
            User existing = findById(user.getId());
            if (user.getPassword() == null || user.getPassword().isBlank()) {
                user.setPassword(existing.getPassword());
            } else {
                user.setPassword(passwordEncoder.encode(user.getPassword()));
            }
        }

        Set<Role> roles = roleIds == null ? new HashSet<>() :
                roleIds.stream()
                       .map(id -> roleRepository.findById(id)
                               .orElseThrow(() -> new EntityNotFoundException("Role not found: " + id)))
                       .collect(Collectors.toSet());
        user.setRoles(roles);

        return userRepository.save(user);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void toggleEnabled(Long id) {
        User user = findById(id);
        user.setEnabled(!user.getEnabled());
        userRepository.save(user);
    }
}
