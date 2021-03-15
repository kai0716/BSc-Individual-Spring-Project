package com.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.domain.Module;


public interface ModuleRepo extends CrudRepository<Module, Integer>{
	Module findByModuleCode(String code); 
}
