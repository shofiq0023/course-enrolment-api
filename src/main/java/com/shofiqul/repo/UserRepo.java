package com.shofiqul.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shofiqul.entities.UserModel;

public interface UserRepo extends JpaRepository<UserModel, Long> {
	Optional<UserModel> findByUsername(String username);
}
