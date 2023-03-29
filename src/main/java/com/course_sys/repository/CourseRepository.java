package com.course_sys.repository;


import com.course_sys.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Integer> {
    List<Course> findByArea(String area);

    List<Course> findByCostBefore(int cost);

    List<Course> findByCostAfter(int cost);

    List<Course> findByCostBetween(int costLower, int costHigher);

    List<Course> findByNameContains(String name);

}
