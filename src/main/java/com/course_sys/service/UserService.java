package com.course_sys.service;


import com.course_sys.entity.User;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();

    User saveUser(User user);

    User getUser(Integer id);

    void deleteUser(Integer id);

    User assignCourse(Integer courseId);

    User wishCourse(Integer courseId);

    User updateFirstName(String firstName);

    User updateLastName(String lastName);
}
