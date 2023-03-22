package com.course_sys.repository;


import com.course_sys.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Integer> {
    public List<Course> findByArea (String area);
    public List<Course> findByCostBefore(int cost);
    public List<Course> findByCostAfter(int cost);
    public List<Course> findByCostBetween(int costLower,int costHigher);
    public List<Course> findByNameContains (String name);

}
