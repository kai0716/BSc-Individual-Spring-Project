package com.repositories;

import org.springframework.data.repository.CrudRepository;

import com.domain.Mark;

public interface MarkRepo extends CrudRepository<Mark, Integer> {

	Mark findById(int id);
	
}
