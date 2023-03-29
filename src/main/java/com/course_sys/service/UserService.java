package com.course_sys.service;


import com.course_sys.entity.User;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();

    void saveUser(User user);

    User getUser(Integer id);

    void deleteUser(Integer id);

    User assignCourse(Integer courseId);

    User wishCourse(Integer courseId);

    void updateFirstName(String firstName);

    void updateLastName(String lastName);
}
