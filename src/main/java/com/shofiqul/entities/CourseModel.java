package com.shofiqul.entities;

import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "courses")
@Data
public class CourseModel {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String title;
	private String description;
	private String topic;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm")
	private Timestamp createdTime;
	private boolean active = true;
	
	@ManyToOne
	@JoinColumn(name = "instructor_id")
	private UserModel instructor;
	
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(
		name = "enrolled_courses",
		joinColumns = @JoinColumn(name = "course_id"),
		inverseJoinColumns = @JoinColumn(name = "user_id")
	)
	private List<UserModel> students;
}













