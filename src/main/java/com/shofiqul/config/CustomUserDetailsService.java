package com.shofiqul.config;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.shofiqul.entities.UserModel;
import com.shofiqul.repo.UserRepo;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {
	private final UserRepo userRepo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<UserModel> user = userRepo.findByUsername(username);
		CustomUserDetails userDetails = null;
		
		if (user.isPresent()) {
			userDetails = new CustomUserDetails(user.get());
		} else {
			throw new UsernameNotFoundException("No user found with the username: " + username);
		}
		
		return userDetails;
	}

}
