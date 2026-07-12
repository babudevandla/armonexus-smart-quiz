package com.quizapp.dto;

import com.quizapp.entity.QuizResult;
import com.quizapp.entity.User;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

/**
 * View-model aggregating every number the Admin dashboard needs, computed
 * once in DashboardController so the JSP stays free of business logic.
 */
@Getter
@Builder
public class AdminDashboardStats {

    // ---- People ----
    private long totalUsers;
    private long totalAdmins;
    private long totalInstructors;
    private long totalReviewers;
    private long totalStudents;

    // ---- Master data ----
    private long totalSubjects;

    // ---- Question Bank ----
    private long totalQuestions;
    private long questionsApproved;
    private long questionsPending;
    private long questionsRejected;
    private long questionsDraft;

    // ---- Quiz Management ----
    private long totalQuizzes;
    private long quizzesActive;
    private long quizzesInactive;
    private long quizzesNotScheduled;
    private long quizzesUpcoming;
    private long quizzesOpen;
    private long quizzesExpired;

    // ---- Results / Activity ----
    private long totalAttempts;   // completed only
    private long totalPassed;
    private long totalFailed;
    private double passRate;
    private long inProgressCount; // currently mid-exam, live monitoring
    private long certificatesIssuedCount;

    // ---- Recent activity feeds ----
    private List<QuizResult> recentAttempts;
    private List<User> recentUsers;
}
