package com.shofiqul.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shofiqul.dto.UserAuthReq;
import com.shofiqul.dto.UserRegisterReq;
import com.shofiqul.dto.UserUpdateReq;
import com.shofiqul.interfaces.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/v1/user")
@RequiredArgsConstructor
public class UserController {
	private final UserService userService;
	
	@PostMapping("/authenticate")
	public ResponseEntity<?> userAuthenticate(@RequestBody UserAuthReq req) {
		return userService.userAuthenticate(req);
	}
	
	@PostMapping("/register")
	public ResponseEntity<?> userRegister(@RequestBody UserRegisterReq req) {
		return userService.userRegister(req);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getUserDetails(@PathVariable("id") long userId) {
		return userService.getUserDetails(userId);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> updateUser(@PathVariable("id") long userId, @RequestBody UserUpdateReq reqDto) {
		return userService.updateUser(userId, reqDto);
	}
}
