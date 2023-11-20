package com.shofiqul.services;

import static com.shofiqul.utils.Consts.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.shofiqul.config.JwtService;
import com.shofiqul.dto.UserAuthReq;
import com.shofiqul.dto.UserDto;
import com.shofiqul.dto.UserRegisterReq;
import com.shofiqul.dto.UserUpdateReq;
import com.shofiqul.entities.UserModel;
import com.shofiqul.interfaces.UserService;
import com.shofiqul.repo.UserRepo;
import com.shofiqul.utils.Utility;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServicesImpl implements UserService {
	private final UserRepo userRepo;
	private final PasswordEncoder passwordEncoder;
	private final AuthenticationManager authManager;
	private final ResponseService resService;
	private final JwtService jwtService;
	
	@Override
	public ResponseEntity<?> userAuthenticate(UserAuthReq req) {
		Optional<UserModel> userOpt = userRepo.findByUsername(req.getUsername());
		
		if (userOpt.isEmpty()) return resService.createResponse("User not found", HttpStatus.NOT_FOUND);
		
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		boolean isPasswordMatches = encoder.matches(req.getPassword(), userOpt.get().getPassword());
		
		if (!isPasswordMatches) return resService.createResponse("No user found", HttpStatus.NOT_FOUND);
		
		UserModel user = userOpt.get();
		Map<String, Object> claims = new HashMap<String, Object>();
		authManager.authenticate(
				new UsernamePasswordAuthenticationToken(req.getUsername(), req.getPassword())
			);
		claims.put("sub", user.getUsername());
		claims.put("userId", user.getId());
		claims.put("email", user.getEmail());
		
		String token = jwtService.generateToken(claims, user.getUsername());
		
		return resService.createResponse(token, "User authenticated", HttpStatus.CREATED);
	}

	@Override
	public ResponseEntity<?> userRegister(UserRegisterReq req, String role) {
		UserModel savedModel = null;
		UserModel model = Utility.copyProperties(req, UserModel.class);
		
		if (!role.equals(ROLE_USER)) {
			model.setRoles(ROLE_USER + ", " + role);
		} else {
			model.setRoles(role);
		}
		
		model.setPassword(passwordEncoder.encode(model.getPassword()));

		try {
			savedModel = userRepo.save(model);
		} catch (DataIntegrityViolationException e) {
			return resService.createDuplicateKeyResponse(e);
		}

		if (savedModel != null) {
			return resService.createResponse(savedModel, HttpStatus.CREATED);
		}
		return null;
	}

	@Override
	public ResponseEntity<?> getUserDetails(long userId) {
		Optional<UserModel> userOpt = Optional.empty();
		
		if (userId == 0) {
			String username = SecurityContextHolder.getContext().getAuthentication().getName();
			userOpt = userRepo.findByUsername(username);
		} else {
			userOpt = userRepo.findById(userId);
		}
		
		if (userOpt.isPresent()) {
			UserDto user = Utility.copyProperties(userOpt.get(), UserDto.class);
			user.setRoles(
					Arrays.asList(userOpt.get().getRoles().split(COMMA_WITH_OR_WITHOUT_SPACE))
				);
			
			return resService.createResponse(user, "User found", HttpStatus.FOUND);
		} else {
			return resService.createResponse("No user found", HttpStatus.NOT_FOUND);
		}
	}

	@Override
	public ResponseEntity<?> getAllUsers() {
		List<UserModel> users = new ArrayList<UserModel>();
		List<UserDto> userResponseList = new ArrayList<UserDto>();
		
		userRepo.findAll().forEach(users::add);
		
		if (!users.isEmpty()) {
			for (UserModel u : users) {
				UserDto dto = Utility.copyProperties(u, UserDto.class);
				
				dto.setRoles(Arrays.asList(u.getRoles().split(COMMA_WITH_OR_WITHOUT_SPACE)));
				
				userResponseList.add(dto);
			}
			return resService.createResponse(userResponseList, "Users found", HttpStatus.FOUND);
		} else {
			return resService.createResponse(userResponseList, "No users were found", HttpStatus.NO_CONTENT);
		}
	}

	@Override
	public ResponseEntity<?> updateUser(UserUpdateReq reqDto) {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		Optional<UserModel> userOpt = userRepo.findByUsername(username);
		
		if (userOpt.isEmpty()) {
			return resService.createResponse("User not found", HttpStatus.NOT_FOUND);
		}
		
		UserModel user = userOpt.get();
		user.setEmail(reqDto.getEmail());
		user.setName(reqDto.getName());
		user.setMobile(reqDto.getMobile());
		user.setAddress(reqDto.getAddress());
		
		UserModel updatedUserModel = userRepo.save(user);
		
		if (updatedUserModel == null) {
			return resService.createResponse("Could not update information", HttpStatus.CONFLICT);
		}
		
		return resService.createResponse("User information updated", HttpStatus.OK);
	}


	@Override
	public ResponseEntity<?> deleteUser(long userId) {
		Optional<UserModel> userOpt = Optional.empty();
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		
		if (userId == 0) {
			userOpt = userRepo.findByUsername(username);
		} else {
			userOpt = userRepo.findById(userId);
		}
		
		if (userOpt.isEmpty()) {
			return resService.createResponse("User not found", HttpStatus.NOT_FOUND);
		} else {
			if (userId == 0) {
				userRepo.deleteByUsername(username);
			} else {
				userRepo.deleteById(userId);
			}
		}
		
		
		return resService.createResponse("User delete successful", HttpStatus.OK);
	}

}
