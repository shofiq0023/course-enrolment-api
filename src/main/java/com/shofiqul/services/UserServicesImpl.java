package com.shofiqul.services;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.shofiqul.config.JwtService;
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
	private final AuthenticationManager authManager;
	private final JwtService jwtService;
	
	@Override
	public ResponseEntity<?> userAuthenticate(UserAuthReq req) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		
		Optional<UserModel> userOpt = userRepo.findByUsername(req.getUsername());
		boolean isPasswordMatches = encoder.matches(req.getPassword(), userOpt.get().getPassword());
		
		if (userOpt.isEmpty() || !isPasswordMatches) {
			return resService.createFailedResponse("No user found", HttpStatus.NOT_FOUND);
		}
		
		UserModel user = userOpt.get();
		Map<String, Object> claims = new HashMap<String, Object>();
		authManager.authenticate(
				new UsernamePasswordAuthenticationToken(req.getUsername(), req.getPassword())
			);
		claims.put("sub", user.getUsername());
		claims.put("userId", user.getId());
		claims.put("email", user.getEmail());
		
		String token = jwtService.generateToken(claims, user.getUsername());
		
		return resService.createSuccessResponse(token, "User authenticated", HttpStatus.CREATED);
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
