package com.shofiqul.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.shofiqul.entities.UserModel;

import jakarta.transaction.Transactional;

public interface UserRepo extends JpaRepository<UserModel, Long> {
	Optional<UserModel> findByUsername(String username);

	@Query("FROM UserModel WHERE roles LIKE %:roleInstructor%")
	List<UserModel> findAllByRolesLike(String roleInstructor);
	
	@Transactional
	void deleteByUsername(String username);
}
