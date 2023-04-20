package com.course_sys.service;


import com.course_sys.entity.Course;

import java.util.List;

public interface CourseService {
    List<Course> getAllCourses();

    Course saveCourse(Course course);

    Course getCourse(Integer id);

    void deleteCourse(Integer id);

    List<Course> findByArea(String area);

    List<Course> findByCostBefore(int cost);

    List<Course> findByCostAfter(int cost);

    List<Course> findByCostBetween(int costLower, int costHigher);

    List<Course> findByNameContains(String name);

}
