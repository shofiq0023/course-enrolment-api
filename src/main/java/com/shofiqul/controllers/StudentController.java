package com.shofiqul.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.shofiqul.dto.StudentEnrollmentReqDto;
import com.shofiqul.interfaces.CourseService;
import com.shofiqul.interfaces.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/v1/student")
@RequiredArgsConstructor
public class StudentController {
	private final CourseService courseService;
	private final UserService userService;
	
	@GetMapping("/courses")
	public ResponseEntity<?> getAllCoursesByStatus() {
		return courseService.getAllCoursesByStatus();
	}
	
	@GetMapping("/courses/topic")
	public ResponseEntity<?> getCoursesByTopic(@RequestParam("topic") String topic) {
		return courseService.getCoursesByTopic(topic);
	}
	
	@GetMapping("/courses/search")
	public ResponseEntity<?> searchCourses(@RequestParam("t") String searchText) {
		return courseService.searchCourses(searchText);
	}
	
	@PutMapping("/enroll")
	public ResponseEntity<?> studentEnrollment(@RequestBody StudentEnrollmentReqDto dto) {
		return courseService.studentEnrollment(dto);
	}
	
	@PutMapping("/deroll")
	public ResponseEntity<?> studentDerollment(@RequestBody StudentEnrollmentReqDto dto) {
		return courseService.studentDerollment(dto);
	}
	
	@GetMapping("/course/{id}")
	public ResponseEntity<?> getCourseWithStudentInfo(@PathVariable("id") long courseId) {
		return courseService.getCourseWithStudentInfo(courseId);
	}
	
	@GetMapping()
	public ResponseEntity<?> getStudentInformation() {
		return userService.getUserDetails(0);
	}
}
