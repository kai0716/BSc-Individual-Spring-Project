package com.repositories;

import org.springframework.data.repository.CrudRepository;

import com.domain.Cheat;

public interface CheatRepo extends CrudRepository<Cheat, Integer>{
	Cheat findByCheatID(int cheatId);
}
