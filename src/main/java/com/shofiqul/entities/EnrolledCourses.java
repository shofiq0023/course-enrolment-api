package com.shofiqul.entities;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "enrolled_courses")
@Data
public class EnrolledCourses {
	@EmbeddedId
	private EnrolledCoursesPK enrolledCoursesPK;
	
	@Column(name = "enrollment_date")
	private LocalDateTime enrollmentDate = LocalDateTime.now();
	
}
