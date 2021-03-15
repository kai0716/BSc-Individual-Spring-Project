package com.repositories;

import org.springframework.data.repository.CrudRepository;

import com.domain.Answer;

public interface AnswerRepo extends CrudRepository<Answer, Integer> {

	Answer findByAnswerID(int answerID); 
}
