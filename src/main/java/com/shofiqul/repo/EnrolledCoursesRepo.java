package com.shofiqul.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.shofiqul.entities.EnrolledCourses;

@Transactional
public interface EnrolledCoursesRepo extends JpaRepository<EnrolledCourses, EnrolledCourses.EnrolledCoursesId> {
	@Modifying
	@Query("DELETE FROM EnrolledCourses ec WHERE ec.enrolledCoursesId.userId=:userId AND ec.enrolledCoursesId.courseId=:courseId")
	int deleteByUserIdAndCourseId(long userId, long courseId);
	
	@Modifying
	@Query("DELETE FROM EnrolledCourses ec WHERE ec.enrolledCoursesId.userId=:userId")
	int deleteByUserId(long userId);
	
	@Modifying
	@Query("DELETE FROM EnrolledCourses ec WHERE ec.enrolledCoursesId.courseId=:courseId")
	int deleteByCourseId(long courseId);

	@Query("SELECT CASE WHEN COUNT(*) > 0 THEN true ELSE false END "
		 + "FROM EnrolledCourses ec "
		 + "WHERE ec.enrolledCoursesId.userId=:userId AND ec.enrolledCoursesId.courseId=:courseId")
	boolean existsByUserIdAndCourseId(long userId, long courseId);
}
