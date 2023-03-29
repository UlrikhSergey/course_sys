package com.course_sys.service;


import com.course_sys.entity.Course;

import java.util.List;

public interface CourseService {
    public List<Course> getAllCourses();

    public void saveCourse(Course course);

    public Course getCourse(Integer id);

    public void deleteCourse(Integer id);

    public List<Course> findByArea(String area);

    public List<Course> findByCostBefore(int cost);

    public List<Course> findByCostAfter(int cost);

    public List<Course> findByCostBetween(int costLower, int costHigher);

    public List<Course> findByNameContains(String name);

}
