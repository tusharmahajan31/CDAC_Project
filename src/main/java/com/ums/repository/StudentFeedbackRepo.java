package com.ums.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ums.entity.StudentFeedback;
public interface StudentFeedbackRepo extends JpaRepository<StudentFeedback,Integer>{


}
