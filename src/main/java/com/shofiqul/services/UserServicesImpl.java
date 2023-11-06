package com.shofiqul.services;

import java.sql.Timestamp;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.shofiqul.dto.UserAuthReq;
import com.shofiqul.dto.UserRegisterReq;
import com.shofiqul.entities.UserModel;
import com.shofiqul.interfaces.UserService;
import com.shofiqul.repo.UserRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServicesImpl implements UserService {
	private final UserRepo userRepo;
	private final PasswordEncoder passwordEncoder;
	private final ResponseService resService;
	
	@Override
	public ResponseEntity<?> userAuthenticate(UserAuthReq req) {
		return null;
	}

	@Override
	public ResponseEntity<?> userRegister(UserRegisterReq req) {
		UserModel model = new UserModel();
		model.setUsername(req.getUsername());
		model.setEmail(req.getEmail());
		model.setName(req.getName());
		model.setRoles(req.getRoles());
		model.setPassword(passwordEncoder.encode(req.getPassword()));
		model.setSignupDate(new Timestamp(System.currentTimeMillis()));
		
		UserModel savedModel = userRepo.save(model);
		
		if (savedModel != null) {
			return resService.createSuccessResponse(savedModel, HttpStatus.CREATED);
		}
		return null;
	}

}
