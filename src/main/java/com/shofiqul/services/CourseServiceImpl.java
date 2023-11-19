package com.shofiqul.services;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.shofiqul.dto.CourseDto;
import com.shofiqul.dto.CourseReqDto;
import com.shofiqul.entities.CourseModel;
import com.shofiqul.entities.UserModel;
import com.shofiqul.interfaces.CourseRepo;
import com.shofiqul.interfaces.CourseService;
import com.shofiqul.repo.UserRepo;
import com.shofiqul.utils.Utility;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {
	private final CourseRepo courseRepo;
	private final UserRepo userRepo;
	private final ResponseService resService;

	@Override
	public ResponseEntity<?> createCourse(CourseReqDto dto) {
		boolean courseExists = courseRepo.existsByTitleAndActive(dto.getTitle(), true);
		if (courseExists) return resService.createResponse("Course with the given title already exist", HttpStatus.CONFLICT);
		
		CourseModel course = Utility.copyProperties(dto, CourseModel.class);
		
		// Get the user-name of the currently authenticated user
		String name = SecurityContextHolder.getContext().getAuthentication().getName();
		Optional<UserModel> user = userRepo.findByUsername(name);
		
		course.setCreatedTime(new Timestamp(System.currentTimeMillis()));
		course.setInstructor(user.get());
		
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

	@Override
	public ResponseEntity<?> updateCourse(long courseId, CourseDto dto) {
		Optional<CourseModel> existingCourse = courseRepo.findById(courseId);
		
		if (existingCourse.isEmpty()) return resService.createResponse("Course not found", HttpStatus.NOT_FOUND);
		
		CourseModel course = existingCourse.get();
		course.setTitle(dto.getTitle());
		course.setDescription(dto.getDescription());
		course.setTopic(dto.getTopic());
		course.setActive(dto.isActive());
		
		CourseModel savedCourse = courseRepo.save(course);
		
		if (savedCourse == null) return resService.createResponse("Could not update the course", HttpStatus.CONFLICT);
		
		return resService.createResponse("Course updated", HttpStatus.OK);
	}

	@Override
	public ResponseEntity<?> getAllCoursesByStatus() {
		List<CourseModel> courses = new ArrayList<CourseModel>();
		
		courseRepo.findAllByActive(true).forEach(courses::add);
		
		return resService.createResponse(courses, "Courses found", HttpStatus.FOUND);
	}

	@Override
	public ResponseEntity<?> deleteCourse(long courseId) {
		boolean courseExist = courseRepo.existsById(courseId);
		
		if (courseExist) {
			courseRepo.deleteById(courseId);
			return resService.createResponse("Course deleted", HttpStatus.OK);
		} else {
			return resService.createResponse("Course not found", HttpStatus.NOT_FOUND);
		}
		
	}

	@Override
	public ResponseEntity<?> getCourseByInstructor(long instructorId) {
		boolean instructorExists = userRepo.existsById(instructorId);
		
		if (!instructorExists) return resService.createResponse("Instructor was not found", HttpStatus.NOT_FOUND);
		
		List<CourseModel> courses = courseRepo.findAllByActiveAndInstructorId(true, instructorId);
		
		if (courses.isEmpty()) return resService.createResponse("Not courses under the given instructor", HttpStatus.NOT_FOUND);
		
		return resService.createResponse(courses, HttpStatus.OK);
	}

}
