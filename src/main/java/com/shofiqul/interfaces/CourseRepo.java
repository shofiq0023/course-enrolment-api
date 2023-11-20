package com.shofiqul.interfaces;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.shofiqul.entities.CourseModel;

public interface CourseRepo extends JpaRepository<CourseModel, Long> {

	List<CourseModel> findAllByActive(boolean b);

	boolean existsByTitleAndActive(String title, boolean b);

	List<CourseModel> findAllByActiveAndInstructorId(boolean b, long instructorId);

	@Query("FROM CourseModel WHERE topic LIKE %:topic% and active=:b")
	List<CourseModel> findAllByActiveAndTopicLike(boolean b, String topic);

	@Query("FROM CourseModel WHERE active=true AND title LIKE %:searchText% OR topic LIKE %:searchText%")
	List<CourseModel> searchCourse(String searchText);

	Optional<CourseModel> findByIdAndActive(long courseId, boolean b);

}
