package com.quizapp.repository;

import com.quizapp.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    @Query("SELECT c FROM Category c WHERE LOWER(c.name) = LOWER(:name)")
    List<Category> findByNameIgnoreCase(String name);
}
