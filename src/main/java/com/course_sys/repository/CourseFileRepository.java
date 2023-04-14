package com.course_sys.repository;

import com.course_sys.entity.CourseFile;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseFileRepository extends CrudRepository<CourseFile,String> {
}
