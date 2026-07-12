package com.quizapp.service;

import com.quizapp.entity.User;

import java.util.List;
import java.util.Set;

public interface UserService {

    List<User> findAll();

    User findById(Long id);

    User save(User user, Set<Long> roleIds, boolean isNew);

    void deleteById(Long id);

    void toggleEnabled(Long id);
}
