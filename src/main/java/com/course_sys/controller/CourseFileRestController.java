package com.course_sys.controller;

import com.course_sys.entity.CourseFile;
import com.course_sys.repository.CourseFileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CourseFileRestController {

    private final CourseFileRepository courseFileRepository;

    public CourseFileRestController(CourseFileRepository courseFileRepository) {
        this.courseFileRepository = courseFileRepository;
    }


    @GetMapping(value = "/files")
    public List<CourseFile> getAllFiles() {

        return (List<CourseFile>) courseFileRepository.findAll();
    }
}
