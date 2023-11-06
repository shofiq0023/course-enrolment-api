package com.shofiqul.interfaces;

import org.springframework.http.ResponseEntity;

import com.shofiqul.dto.UserAuthReq;
import com.shofiqul.dto.UserRegisterReq;

public interface UserService {

	ResponseEntity<?> userAuthenticate(UserAuthReq req);

	ResponseEntity<?> userRegister(UserRegisterReq req);

}
