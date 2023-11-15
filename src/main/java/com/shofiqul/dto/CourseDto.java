package com.shofiqul.dto;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class CourseDto {
	private long id;
	private String title;
	private String description;
	private String topic;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm")
	private Timestamp createdTime;
	private boolean active;
}
