package com.shofiqul.controllers;

import static com.shofiqul.utils.Consts.ROLE_INSTRUCTOR;
import static com.shofiqul.utils.Consts.ROLE_USER;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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
		return userService.userRegister(req, ROLE_USER);
	}
	
	@PostMapping("/register/instructor")
	public ResponseEntity<?> instructorRegister(@RequestBody UserRegisterReq req) {
		return userService.userRegister(req, ROLE_INSTRUCTOR);
	}
	
	@GetMapping()
	public ResponseEntity<?> getUserDetails() {
		return userService.getUserDetails(0);
	}
	
	@PutMapping()
	public ResponseEntity<?> updateUser(@RequestBody UserUpdateReq reqDto) {
		return userService.updateUser(reqDto);
	}
	
	@DeleteMapping()
	public ResponseEntity<?> deleteUser() {
		return userService.deleteUser(0);
	}
}
