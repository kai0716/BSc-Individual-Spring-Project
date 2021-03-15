package com.repositories;

import org.springframework.data.repository.CrudRepository;

import com.domain.Role;

public interface RoleRepository extends CrudRepository<Role, Integer> {
	
	public Role findById(int id);
	public Role findByRole(String role);
}
