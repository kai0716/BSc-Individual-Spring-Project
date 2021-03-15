package com.repositories;

import org.springframework.data.repository.CrudRepository;

import com.domain.Answer;
import com.domain.SelectedOption;

public interface SelectedOptionRepo extends CrudRepository<SelectedOption, Integer> {

}
