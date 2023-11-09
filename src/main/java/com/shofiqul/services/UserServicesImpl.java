package com.shofiqul.services;

import static com.shofiqul.utils.Consts.COMMA_WITH_OR_WITHOUT_SPACE;
import static com.shofiqul.utils.Consts.ROLE_USER;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
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
import com.shofiqul.dto.UserDto;
import com.shofiqul.dto.UserRegisterReq;
import com.shofiqul.dto.UserRoleUpdateDto;
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
		UserModel model = Utility.copyProperties(req, UserModel.class);
		model.setRoles(ROLE_USER);
		model.setPassword(passwordEncoder.encode(model.getPassword()));
		
		UserModel savedModel = userRepo.save(model);
		
		if (savedModel != null) {
			return resService.createSuccessResponse(savedModel, HttpStatus.CREATED);
		}
		return null;
	}

	@Override
	public ResponseEntity<?> getUserDetails(long userId) {
		Optional<UserModel> userOpt = userRepo.findById(userId);
		
		if (userOpt.isPresent()) {
			UserDto user = Utility.copyProperties(userOpt.get(), UserDto.class);
			
			return resService.createSuccessResponse(user, "User found", HttpStatus.FOUND);
		} else {
			return resService.createFailedResponse("No user found", HttpStatus.NOT_FOUND);
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
			return resService.createSuccessResponse(userResponseList, "Users found", HttpStatus.FOUND);
		} else {
			return resService.createSuccessResponse(userResponseList, "No users were found", HttpStatus.NO_CONTENT);
		}
	}

	@Override
	public ResponseEntity<?> updateUser(long userId, UserUpdateReq reqDto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<?> makeAdmin(long userId, UserRoleUpdateDto req) {
		Optional<UserModel> userOpt = userRepo.findById(userId);
		List<String> givenRoles = req.getRoles();
		
		if (userOpt.isPresent()) {
			UserModel user = userOpt.get();
			
			List<String> userExistingRoles = new ArrayList<String>(
					Arrays.asList(user.getRoles().split(COMMA_WITH_OR_WITHOUT_SPACE)));
			
			for (String role : givenRoles) {
				if (!userExistingRoles.contains(role)) {
					userExistingRoles.add(role);
				} else {
					continue;
				}
				
			}
			
			user.setRoles(String.join(", ", userExistingRoles));
			UserModel savedUser = userRepo.save(user);
			
			if (savedUser == null) {
				return resService.createFailedResponse("Failed", HttpStatus.INTERNAL_SERVER_ERROR);
			}
			
			return resService.createSuccessResponse("Role updated", HttpStatus.OK);
			
		} else {
			return resService.createFailedResponse("No user found", HttpStatus.NOT_FOUND);
		}
	}


}
