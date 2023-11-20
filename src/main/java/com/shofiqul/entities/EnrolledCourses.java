package com.shofiqul.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "enrolled_courses")
@Data
public class EnrolledCourses {
	
	@Embeddable
	@Data
	public static class EnrolledCoursesId {
		public EnrolledCoursesId(long userId, long courseId) {
			this.userId = userId;
			this.courseId = courseId;
		}

		@Column(name = "user_id")
		public long userId;
		
		@Column(name = "course_id")
		public long courseId;
	}
	
	@EmbeddedId
	private EnrolledCoursesId enrolledCoursesId;
}
