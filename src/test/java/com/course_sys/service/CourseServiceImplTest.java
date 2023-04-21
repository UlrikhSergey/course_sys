package com.course_sys.service;

import com.course_sys.entity.Course;
import com.course_sys.exception.AlreadyExistException;
import com.course_sys.exception.CourseNotFoundException;
import com.course_sys.repository.CourseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class CourseServiceImplTest {

    @Mock
    private CourseRepository courseRepository;

    @InjectMocks
    private CourseServiceImpl courseService;

    private Course course1;
    private Course course2;

    @BeforeEach
    public void setUp(){
        course1 = Course.builder()
                .id(1)
                .area("IT")
                .name("Backend")
                .cost(100)
                .build();
        course2 = Course.builder()
                .id(2)
                .area("Marketing")
                .name("Make your profile")
                .cost(250)
                .build();
    }
    @Test
    void getAllCourses() {

        given(courseRepository.findAll()).willReturn(List.of(course1,course2));
        List<Course> courseList = courseService.getAllCourses();
        assertThat(courseList).isNotNull();
        assertThat(courseList.size()).isEqualTo(2);
    }
    @Test
    void getAllCoursesNegativeScenario() {
        given(courseRepository.findAll()).willReturn(Collections.emptyList());
        List<Course> courseList = courseService.getAllCourses();
        assertThat(courseList).isEmpty();
    }

    @Test
    void saveCourse() {
        given(courseRepository.findById(course1.getId()))
                .willReturn(Optional.empty());

        given(courseRepository.save(course1)).willReturn(course1);
        Course savedCourse = courseService.saveCourse(course1);
        System.out.println(savedCourse);
        assertThat(savedCourse).isNotNull();
    }
    @Test
    void saveCourseWhenCourseAlreadyExist() {
        given(courseRepository.findById(course1.getId()))
                .willReturn(Optional.of(course1));

        assertThrows(AlreadyExistException.class, () -> courseService.saveCourse(course1));
    }

    @Test
    void getCourse() {
        given(courseRepository.findById(1)).willReturn(Optional.of(course1));

        // when
        Course savedCourse = courseService.getCourse(course1.getId());

        // then
        assertThat(savedCourse).isNotNull();
    }

    @Test
    void getCourseWhenCourseNotFound() {
        given(courseRepository.findById(1)).willReturn(Optional.empty());

        assertThrows(CourseNotFoundException.class, () -> courseService.getCourse(1));
    }

    @Test
    void deleteCourse() {
        int courseId = 1;
        willDoNothing().given(courseRepository).deleteById(courseId);
        courseService.deleteCourse(courseId);
        verify(courseRepository,times(1)).deleteById(courseId);
    }

    @Test
    void findByArea() {
        given(courseRepository.findByArea("IT")).willReturn(Collections.singletonList(course1));

        // when
        List <Course> courseWithArea = courseService.findByArea(course1.getArea());

        // then
        assertThat(courseWithArea.get(0)).isEqualTo(course1);
        assertThat(courseWithArea).isNotNull();
    }

    @Test
    void findByCostBefore() {
        given(courseRepository.findByCostBefore(120)).willReturn(Collections.singletonList(course1));

        // when
        List <Course> courseWithArea = courseService.findByCostBefore(120);

        // then
        assertThat(courseWithArea.get(0)).isEqualTo(course1);
        assertThat(courseWithArea).isNotNull();
    }

    @Test
    void findByCostAfter() {
        given(courseRepository.findByCostAfter(120)).willReturn(Collections.singletonList(course2));

        // when
        List <Course> courseWithArea = courseService.findByCostAfter(120);

        // then
        assertThat(courseWithArea.get(0)).isEqualTo(course2);
        assertThat(courseWithArea).isNotNull();
    }

    @Test
    void findByCostBetween() {
        given(courseRepository.findByCostBetween(200,300)).willReturn(Collections.singletonList(course2));

        // when
        List <Course> courseWithArea = courseService.findByCostBetween(200,300);

        // then
        assertThat(courseWithArea.get(0)).isEqualTo(course2);
        assertThat(courseWithArea).isNotNull();
    }

    @Test
    void findByNameContains() {
        given(courseRepository.findByNameContains("ckend")).willReturn(Collections.singletonList(course1));

        // when
        List <Course> courseWithArea = courseService.findByNameContains("ckend");

        // then
        assertThat(courseWithArea.get(0)).isEqualTo(course1);
        assertThat(courseWithArea).isNotNull();
    }
}