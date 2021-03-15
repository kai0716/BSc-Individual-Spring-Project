package com.repositories;

import org.springframework.data.repository.CrudRepository;

import com.domain.Exam;

public interface ExamRepo extends CrudRepository<Exam, Integer>{
	Exam findByExamID(int examID);
}
