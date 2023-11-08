package com.shofiqul.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shofiqul.dto.UserRoleUpdateDto;
import com.shofiqul.interfaces.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/v1/super")
@RequiredArgsConstructor
public class SuperAdminController {
	private final UserService userService;
	
	@PostMapping("/update/roles/{id}")
	public ResponseEntity<?> makeAdmin(@PathVariable("id") long userId, @RequestBody UserRoleUpdateDto req) {
		return userService.makeAdmin(userId, req);
	}
}
