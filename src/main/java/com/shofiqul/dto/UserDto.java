package com.shofiqul.dto;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class UserDto {
	private long id;
	private String name;
	private String username;
	private String email;
	private String mobile;
	private String address;
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Dhaka")
	private Date dateOfBirth;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Dhaka")
	private Timestamp signupDate;
	private List<String> roles;
	private List<CourseDto> enrolledCourses;
}
