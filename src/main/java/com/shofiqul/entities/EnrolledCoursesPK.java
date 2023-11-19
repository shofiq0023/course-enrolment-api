package com.shofiqul.entities;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

@Embeddable
@Data
public class EnrolledCoursesPK implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name = "user_id")
	private long userId;
	
	@Column(name = "course_id")
	private long courseId;
}
