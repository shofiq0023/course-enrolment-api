package com.shofiqul.interfaces;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.shofiqul.entities.CourseModel;

public interface CourseRepo extends JpaRepository<CourseModel, Long> {

	List<CourseModel> findAllByActive(boolean b);

	@Query("")
	boolean existsByTitleAndActive(String title, boolean b);

}
