package com.shofiqul.interfaces;

import org.springframework.http.ResponseEntity;

import com.shofiqul.dto.UserRoleUpdateDto;

public interface RoleService {

	ResponseEntity<?> makeAdmin(long userId, UserRoleUpdateDto req);

	ResponseEntity<?> getRoles();

	ResponseEntity<?> removeRole(long userId, UserRoleUpdateDto req);
	
}
