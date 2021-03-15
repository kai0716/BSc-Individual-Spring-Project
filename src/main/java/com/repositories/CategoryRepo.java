package com.repositories;

import org.springframework.data.repository.CrudRepository;
import com.domain.Category;

public interface CategoryRepo extends CrudRepository<Category, String> {

	Category findByCategoryName(String cn); 
}
