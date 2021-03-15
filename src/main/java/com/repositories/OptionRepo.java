package com.repositories;

import org.springframework.data.repository.CrudRepository;

import com.domain.Option;

public interface OptionRepo extends CrudRepository<Option, Integer> {

	Option findByOptionID(int optionID); 
}
