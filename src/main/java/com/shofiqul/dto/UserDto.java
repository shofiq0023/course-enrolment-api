package com.shofiqul.dto;

import java.util.List;

import lombok.Data;

@Data
public class UserDto {
	private long id;
	private String name;
	private String username;
	private String email;
	private List<String> roles;
}
