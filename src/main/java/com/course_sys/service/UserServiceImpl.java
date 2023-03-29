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
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    private final CourseRepository courseRepository;

    public UserServiceImpl(UserRepository userRepository, CourseRepository courseRepository) {
        this.userRepository = userRepository;
        this.courseRepository = courseRepository;
    }

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
        Optional<User> optional = userRepository.findById(id);
        if (optional.isPresent()) {
            user = optional.get();
        }
        return user;
    }

    @Override
    public void deleteUser(Integer id) {
        userRepository.deleteById(id);
    }

    //Метод для добавления курса в купленные
    @Override
    public User assignCourse(Integer courseId) {
        User user = getUserByAuth();
        Course course = courseRepository.findById(courseId).get();
        Set<Course> assignSet = user.getAssignedCourses();
        Set<Course> wishSet = user.getWishListCourses();
        if (wishSet.contains(course)) {
            wishSet.remove(course);
            user.setWishListCourses(wishSet);
        }
        assignSet.add(course);
        user.setAssignedCourses(assignSet);
        return userRepository.save(user);
    }

    //Метод для добавления курса в список избранных
    @Override
    public User wishCourse(Integer courseId) {
        User user = getUserByAuth();
        Course course = courseRepository.findById(courseId).get();
        Set<Course> wishSet = user.getWishListCourses();
        if (user.getAssignedCourses().contains(course)) {
            System.out.println("The course has already been purchased");
        } else {
            wishSet.add(course);
            user.setWishListCourses(wishSet);
        }
        return userRepository.save(user);
    }

    //Метод для изменения имени
    @Override
    public void updateFirstName(String firstName) {
        User user = getUserByAuth();
        user.setFirstname(firstName);
        userRepository.save(user);
    }

    //Метод для изменения фамилии
    @Override
    public void updateLastName(String lastname) {
        User user = getUserByAuth();
        user.setLastname(lastname);
        userRepository.save(user);
    }

    //Метод для получения модели аутентифицированного пользователя
    private User getUserByAuth() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        return userRepository.findByEmail(currentPrincipalName).get();
    }
}
