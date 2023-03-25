package com.course_sys.service;


import com.course_sys.entity.Course;
import com.course_sys.entity.User;
import com.course_sys.repository.CourseRepository;
import com.course_sys.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public void saveUser(User user) {
        userRepository.save(user);
    }

    @Override
    public User getUser(Integer id) {
        User user = null;
        Optional<User> optional =  userRepository.findById(id);
        if(optional.isPresent()){
            user = optional.get();
        }
        return user;
    }

    @Override
    public void deleteUser(Integer id) {
        userRepository.deleteById(id);
    }

    @Override
    public User assignCourse(Integer empId, Integer courseId) {
        Set<Course> courseSet = null;
        User user = userRepository.findById(empId).get();
        Course course = courseRepository.findById(courseId).get();
        courseSet = user.getAssignedCourses();
        if (user.getWishListCourses().contains(course)){
            user.getWishListCourses().remove(course);
        }
        courseSet.add(course);
        user.setAssignedCourses(courseSet);
        return userRepository.save(user);
    }

    @Override
    public User wishCourse(Integer empId, Integer courseId) {
        Set<Course> courseSet = null;
        User user = userRepository.findById(empId).get();
        Course course = courseRepository.findById(courseId).get();
        courseSet = user.getWishListCourses();
        if (user.getAssignedCourses().contains(course)){
            System.out.println("Курс уже приобретен");
        }
        else {
            courseSet.add(course);
            user.setWishListCourses(courseSet);
        }
        return userRepository.save(user);
    }

    @Override
    public void updateFirstName(String firstName) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        User user = userRepository.findByEmail(currentPrincipalName).get();
        user.setFirstname(firstName);
        userRepository.save(user);
    }

    @Override
    public void updateLastName(String lastname) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        User user = userRepository.findByEmail(currentPrincipalName).get();
        user.setLastname(lastname);
        userRepository.save(user);
    }
}
