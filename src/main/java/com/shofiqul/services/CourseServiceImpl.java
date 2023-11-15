package com.shofiqul.services;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.shofiqul.dto.CourseDto;
import com.shofiqul.dto.CourseReqDto;
import com.shofiqul.entities.CourseModel;
import com.shofiqul.interfaces.CourseRepo;
import com.shofiqul.interfaces.CourseService;
import com.shofiqul.utils.Utility;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {
	private final CourseRepo courseRepo;
	private final ResponseService resService;

	@Override
	public ResponseEntity<?> createCourse(CourseReqDto dto) {
		CourseModel course = Utility.copyProperties(dto, CourseModel.class);
		course.setCreatedTime(new Timestamp(System.currentTimeMillis()));
		
		CourseModel savedCourse = courseRepo.save(course);
		
		if (savedCourse == null) return resService.createResponse("Could not create a course", HttpStatus.CONFLICT);
		
		return resService.createResponse("New Course created", HttpStatus.CREATED);
	}

	@Override
	public ResponseEntity<?> getCourse(long courseId) {
		Optional<CourseModel> course = courseRepo.findById(courseId);
		
		if (course.isEmpty()) return resService.createResponse("No course found", HttpStatus.NOT_FOUND);
		
		CourseDto courseRes = Utility.copyProperties(course.get(), CourseDto.class);
		
		return resService.createResponse(courseRes, "Course found", HttpStatus.OK);
	}

	@Override
	public ResponseEntity<?> getAllCourses() {
		List<CourseModel> courses = new ArrayList<CourseModel>();
		
		courseRepo.findAll().forEach(courses::add);
		
		return resService.createResponse(courses, HttpStatus.FOUND);
	}

}
