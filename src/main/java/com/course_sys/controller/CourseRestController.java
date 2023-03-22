package com.course_sys.controller;



import com.course_sys.entity.Course;
import com.course_sys.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
public class CourseRestController {
    @Autowired
    private CourseService courseService;


    @GetMapping("/courses")
    public List<Course> getAllCourses(){

        List<Course> allCourses= courseService.getAllCourses();
        Collections.sort(allCourses);

        return allCourses;
    }

    @GetMapping("/courses/{id}")
    public Course getCourse(@PathVariable Integer id){
        Course course = courseService.getCourse(id);
        return course;
    }

    @PostMapping("/courses")
    public Course addNewCourse (@RequestBody Course course){
        courseService.saveCourse(course);
        return course;
    }


    @PutMapping("/courses")
    public Course updateBook (@RequestBody Course course){
        courseService.saveCourse(course);
        return course;
    }


    @DeleteMapping("/courses/{id}")
    public String deleteCourse(@PathVariable Integer id){
        Course course = courseService.getCourse(id);
        courseService.deleteCourse(id);
        return "Course with id = " + id + " was deleted";
    }

    @GetMapping("/courses/areafilter/{area}")
    public List<Course> getCoursesByArea (@PathVariable String area){
        return courseService.findByArea(area.toUpperCase());
    }

    @GetMapping("/courses/costfilterafter/{cost}")
    public List<Course> getCoursesCostAfter (@PathVariable int cost){
        return courseService.findByCostAfter(cost);
    }

    @GetMapping("/courses/costfilterbefore/{cost}")
    public List<Course> getCoursesCostBefore (@PathVariable int cost){
        return courseService.findByCostBefore(cost);
    }

    @GetMapping("/courses/costfilterbetween/{costLower}/{costHigher}")
    public List<Course> getCoursesCostBefore (@PathVariable int costLower,
                                              @PathVariable int costHigher){
        return courseService.findByCostBetween(costLower,costHigher);
    }

    @GetMapping("/courses/namecontainsfilter/{name}")
    public List<Course> getCoursesNameContains (@PathVariable String name){
        return courseService.findByNameContains(name);
    }


}
