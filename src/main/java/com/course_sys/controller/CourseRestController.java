package com.course_sys.controller;


import com.course_sys.entity.Course;
import com.course_sys.service.CourseService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping(value = "/courses")
public class CourseRestController {

    private final CourseService courseService;

    public CourseRestController(CourseService courseService) {
        this.courseService = courseService;
    }


    //Получаем список всех курсов на платформе
    @GetMapping
    public List<Course> getAllCourses() {

        List<Course> allCourses = courseService.getAllCourses();
        Collections.sort(allCourses);

        return allCourses;
    }

    //Получаем отдельно взятый курс по id
    @GetMapping(value = "/{id}")
    public Course getCourse(@PathVariable Integer id) {
        return courseService.getCourse(id);
    }

    //Преподаватель добавляет курс
    @PreAuthorize("hasAuthority('TEACHER')")
    @PostMapping
    public Course addNewCourse(@RequestBody Course course) {
        courseService.saveCourse(course);
        return course;
    }

    //Преподаватель изменяет данные о курсе
    @PreAuthorize("hasAuthority('TEACHER')")
    @PutMapping
    public Course updateCourse(@RequestBody Course course) {
        courseService.saveCourse(course);
        return course;
    }

    //Удаление курса
    @PreAuthorize("hasAuthority('TEACHER')")
    @DeleteMapping(value = "/{id}")
    public String deleteCourse(@PathVariable Integer id) {
        courseService.deleteCourse(id);
        return "Course with id = " + id + " was deleted";
    }

    //Фильтр курса по областям
    @GetMapping(params = "area")
    public List<Course> getCoursesByArea(@RequestParam("area") String area) {
        return courseService.findByArea(area.toUpperCase());
    }

    //Фильтр курса по стоимости (все, что дороже)
    @GetMapping(params = "min")
    public List<Course> getCoursesCostAfter(@RequestParam("min") int cost) {
        return courseService.findByCostAfter(cost);
    }

    //Фильтр курса по стоимости (все, что дешевле)
    @GetMapping(params = "max")
    public List<Course> getCoursesCostBefore(@RequestParam("max") int cost) {
        return courseService.findByCostBefore(cost);
    }

    //Фильтр курса по стоимости (все, что в промежутке между двумя ценами)
    @GetMapping(params = {"min", "max"})
    public List<Course> getCoursesCostBetween(@RequestParam("min") int costLower,
                                             @RequestParam("max") int costHigher) {
        return courseService.findByCostBetween(costLower, costHigher);
    }

    //Фильтр курса по названию
    @GetMapping(params = "name")
    public List<Course> getCoursesNameContains(@RequestParam("name") String name) {
        return courseService.findByNameContains(name);
    }


}
