package com.shofiqul.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

@Embeddable
@Data
public class EnrolledCoursesPK {
	@Column(name = "user_id")
	private long userId;

	@Column(name = "course_id")
	private long courseId;
}
