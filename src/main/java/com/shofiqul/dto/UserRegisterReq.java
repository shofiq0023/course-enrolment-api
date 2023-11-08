package com.shofiqul.dto;

import java.sql.Date;
import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class UserRegisterReq {
	private String name;
	private String username;
	private String email;
	private String password;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Dhaka")
	private Timestamp signupDate;
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Dhaka")
	private Date dateOfBirth;
	private String mobile;
	private String address;
}
