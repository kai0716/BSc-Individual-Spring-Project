package com.repositories;

import org.springframework.data.repository.CrudRepository;

import com.domain.Users;

public interface UserRepo extends CrudRepository<Users, String> {

	Users findByEmail(String email);
}
