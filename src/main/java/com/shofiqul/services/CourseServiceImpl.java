package com.shofiqul.services;

import java.sql.Timestamp;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

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

}
