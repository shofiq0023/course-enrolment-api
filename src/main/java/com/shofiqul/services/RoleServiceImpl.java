package com.shofiqul.services;

import static com.shofiqul.utils.Consts.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.shofiqul.dto.UserRoleUpdateDto;
import com.shofiqul.entities.RoleDefinitionModel;
import com.shofiqul.entities.UserModel;
import com.shofiqul.interfaces.RoleService;
import com.shofiqul.interfaces.RolesRepo;
import com.shofiqul.repo.UserRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
	private final UserRepo userRepo;
	private final RolesRepo rolesRepo;
	private final ResponseService resService;
	
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
				return resService.createResponse("Failed", HttpStatus.INTERNAL_SERVER_ERROR);
			}
			
			return resService.createResponse("Role updated", HttpStatus.OK);
			
		} else {
			return resService.createResponse("No user found", HttpStatus.NOT_FOUND);
		}
	}

	@Override
	public ResponseEntity<?> getRoles() {
		List<RoleDefinitionModel> roles = new ArrayList<RoleDefinitionModel>();
		
		rolesRepo.findAll().forEach(roles::add);
		
		return resService.createResponse(roles, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<?> removeRole(long userId, UserRoleUpdateDto req) {
		Optional<UserModel> userOpt = userRepo.findById(userId);
		
		if (userOpt.isEmpty()) {
			return resService.createResponse("User not found", HttpStatus.NOT_FOUND);
		}
		
		UserModel user = userOpt.get();
		List<String> existingRoles = new ArrayList<String>(Arrays.asList(user.getRoles().split(COMMA_WITH_OR_WITHOUT_SPACE)));
		
		if (req.getRoles() == null || req.getRoles().isEmpty()) return resService.createResponse("No roles were provided", HttpStatus.PRECONDITION_FAILED); 
		
		for (String reqRole : req.getRoles()) {
			if (reqRole.equals(ROLE_USER)) {
				continue;
			}
			
			if (existingRoles.contains(reqRole)) {
				existingRoles.remove(reqRole);
			}
		}
		
		user.setRoles(String.join(", ", existingRoles));
		
		UserModel savedModel = userRepo.save(user);
		
		if (savedModel == null) {
			return resService.createResponse("Could not update roles", HttpStatus.CONFLICT);
		}
		
		return resService.createResponse("User roles updated", HttpStatus.OK);
	}
}
