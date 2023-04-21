package com.course_sys.service;


import com.course_sys.entity.Course;
import com.course_sys.exception.AlreadyExistException;
import com.course_sys.exception.CourseNotFoundException;
import com.course_sys.repository.CourseRepository;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseServiceImpl implements CourseService {
    private final CourseRepository courseRepository;

    public CourseServiceImpl(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @Override
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    @SneakyThrows
    @Override
    public Course saveCourse(Course course) {

        Optional<Course> savedCourse = courseRepository.findCourseByName(course.getName());
        if (savedCourse.isPresent()) {
            throw new AlreadyExistException("Course already exist");
        }
        else {
            return courseRepository.save(course);
        }
    }

    @SneakyThrows
    @Override
    public Course getCourse(Integer id) {
        Optional<Course> optional = courseRepository.findById(id);
        if (optional.isPresent()) {
          return optional.get();
        }
        else {
            throw new CourseNotFoundException("Course with id - " + id + " not found");
        }
    }

    @Override
    public void deleteCourse(Integer id) {
        courseRepository.deleteById(id);
    }

    @Override
    public List<Course> findByArea(String area) {
        return courseRepository.findByArea(area);
    }

    @Override
    public List<Course> findByCostBefore(int cost) {
        return courseRepository.findByCostBefore(cost);
    }

    @Override
    public List<Course> findByCostAfter(int cost) {
        return courseRepository.findByCostAfter(cost);
    }

    @Override
    public List<Course> findByCostBetween(int costLower, int costHigher) {
        return courseRepository.findByCostBetween(costLower, costHigher);
    }

    @Override
    public List<Course> findByNameContains(String name) {
        return courseRepository.findByNameContains(name);
    }
}
