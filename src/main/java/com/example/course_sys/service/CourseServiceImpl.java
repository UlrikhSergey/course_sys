package com.example.course_sys.service;


import com.example.course_sys.entity.Course;
import com.example.course_sys.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseServiceImpl implements CourseService{
    @Autowired
    private CourseRepository courseRepository;

    @Override
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    @Override
    public void saveCourse(Course course) {
        courseRepository.save(course);
    }

    @Override
    public Course getCourse(Integer id) {
        Course course = null;
        Optional<Course> optional =  courseRepository.findById(id);
        if(optional.isPresent()){
            course = optional.get();
        }
        return course;

    }

    @Override
    public void deleteCourse(Integer id) {
        courseRepository.deleteById(id);
    }

    @Override
    public List<Course> findByArea(String area) {
       List<Course> courses =  courseRepository.findByArea(area);
       return courses;
    }

    @Override
    public List<Course> findByCostBefore(int cost) {
        List<Course> courses = courseRepository.findByCostBefore(cost);
        return courses;
    }

    @Override
    public List<Course> findByCostAfter(int cost) {
        List<Course> courses = courseRepository.findByCostAfter(cost);
        return courses;
    }

    @Override
    public List<Course> findByCostBetween(int costLower, int costHigher) {
        List<Course> courses = courseRepository.findByCostBetween(costLower,costHigher);
        return courses;
    }

    @Override
    public List<Course> findByNameContains(String name) {
        List<Course> courses = courseRepository.findByNameContains(name);
        return courses;
    }
}
