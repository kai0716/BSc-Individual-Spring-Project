package com.repositories;

import org.springframework.data.repository.CrudRepository;

import com.domain.Question;

public interface QuestionRepo extends CrudRepository<Question, Integer> {

	Question findByQuestionID(int questionID);
	
}
