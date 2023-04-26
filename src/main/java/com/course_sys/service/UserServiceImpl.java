package com.course_sys.service;


import com.course_sys.entity.Course;
import com.course_sys.entity.User;
import com.course_sys.exception.AlreadyExistException;
import com.course_sys.exception.UserNotFoundException;
import com.course_sys.repository.CourseRepository;
import com.course_sys.repository.UserRepository;
import lombok.SneakyThrows;
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

    @SneakyThrows
    @Override
    public User saveUser(User user) {
        Optional<User> savedUser = userRepository.findByEmail(user.getEmail());
        if (savedUser.isPresent()) {
            throw new AlreadyExistException("User already exist");
        } else {
            return userRepository.save(user);
        }

    }

    @SneakyThrows
    @Override
    public User getUser(Integer id) {
        Optional<User> optional = userRepository.findById(id);
        if (optional.isPresent()) {
            return optional.get();
        } else {
            throw new UserNotFoundException("User with id - " + id + " not found");
        }

    }

    @Override
    public void deleteUser(Integer id) {
        userRepository.deleteById(id);
    }

    //Метод для добавления курса в купленные
    @Override
    public User assignCourse(Integer courseId) {
        User user = getUserByAuth();
        Course course = null;
        Optional<Course> optional = courseRepository.findById(courseId);
        if (optional.isPresent()) {
            course = optional.get();
        }
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
        Course course = null;
        Optional<Course> optional = courseRepository.findById(courseId);
        if (optional.isPresent()) {
            course = optional.get();
        }
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
    public User updateFirstName(String firstName) {
        User user = getUserByAuth();
        user.setFirstname(firstName);
        userRepository.save(user);
        return user;
    }

    //Метод для изменения фамилии
    @Override
    public User updateLastName(String lastname) {
        User user = getUserByAuth();
        user.setLastname(lastname);
        userRepository.save(user);
        return user;
    }

    //Метод для получения модели аутентифицированного пользователя
    private User getUserByAuth() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        User user = null;
        Optional<User> optional = userRepository.findByEmail(currentPrincipalName);
        if (optional.isPresent()) {
            user = optional.get();
        }
        return user;
    }
}
