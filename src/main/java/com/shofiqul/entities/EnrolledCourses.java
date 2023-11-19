package com.shofiqul.entities;

import java.sql.Timestamp;

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
	@Column(name = "enrolment_date")
	private Timestamp enrolmentDate = new Timestamp(System.currentTimeMillis());
}
