package com.shofiqul.dto;

import lombok.Data;

@Data
public class UserRegisterReq {
	private String name;
	private String username;
	private String email;
	private String password;
	private String roles;
}
