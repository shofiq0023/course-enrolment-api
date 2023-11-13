package com.shofiqul.dto;

import lombok.Data;

@Data
public class UserUpdateReq {
	private String name;
	private String email;
	private String mobile;
	private String address;
}
