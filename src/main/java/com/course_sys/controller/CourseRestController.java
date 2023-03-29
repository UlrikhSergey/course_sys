package com.course_sys.controller;


import com.course_sys.entity.Course;
import com.course_sys.service.CourseService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
public class CourseRestController {

    private final CourseService courseService;

    public CourseRestController(CourseService courseService) {
        this.courseService = courseService;
    }


    //Получаем список всех курсов на платформе
    @GetMapping("/courses")
    public List<Course> getAllCourses() {

        List<Course> allCourses = courseService.getAllCourses();
        Collections.sort(allCourses);

        return allCourses;
    }

    //Получаем отдельно взятый курс по id
    @GetMapping("/courses/{id}")
    public Course getCourse(@PathVariable Integer id) {
        return courseService.getCourse(id);
    }

    //Преподаватель добавляет курс
    @PreAuthorize("hasAuthority('TEACHER')")
    @PostMapping("/courses")
    public Course addNewCourse(@RequestBody Course course) {
        courseService.saveCourse(course);
        return course;
    }

    //Преподаватель изменяет данные о курсе
    @PreAuthorize("hasAuthority('TEACHER')")
    @PutMapping("/courses")
    public Course updateBook(@RequestBody Course course) {
        courseService.saveCourse(course);
        return course;
    }

    //Удаление курса
    @PreAuthorize("hasAuthority('TEACHER')")
    @DeleteMapping("/courses/{id}")
    public String deleteCourse(@PathVariable Integer id) {
        courseService.deleteCourse(id);
        return "Course with id = " + id + " was deleted";
    }

    //Фильтр курса по областям
    @GetMapping("/courses/areafilter/{area}")
    public List<Course> getCoursesByArea(@PathVariable String area) {
        return courseService.findByArea(area.toUpperCase());
    }

    //Фильтр курса по стоимости (все, что дороже)
    @GetMapping("/courses/costfilterafter/{cost}")
    public List<Course> getCoursesCostAfter(@PathVariable int cost) {
        return courseService.findByCostAfter(cost);
    }

    //Фильтр курса по стоимости (все, что дешевле)
    @GetMapping("/courses/costfilterbefore/{cost}")
    public List<Course> getCoursesCostBefore(@PathVariable int cost) {
        return courseService.findByCostBefore(cost);
    }

    //Фильтр курса по стоимости (все, что в промежутке между двумя ценами)
    @GetMapping("/courses/costfilterbetween/{costLower}/{costHigher}")
    public List<Course> getCoursesCostBefore(@PathVariable int costLower,
                                             @PathVariable int costHigher) {
        return courseService.findByCostBetween(costLower, costHigher);
    }

    //Фильтр курса по названию
    @GetMapping("/courses/namecontainsfilter/{name}")
    public List<Course> getCoursesNameContains(@PathVariable String name) {
        return courseService.findByNameContains(name);
    }


}
