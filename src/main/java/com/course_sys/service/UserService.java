package com.course_sys.service;



import com.course_sys.entity.User;

import java.util.List;

public interface UserService {
    public List<User> getAllUsers();
    public void saveUser(User user);
    public User getUser (Integer id);
    public void deleteUser (Integer id);
    public User assignCourse(Integer empId, Integer courseId);
    public User wishCourse(Integer empId, Integer courseId);
}
