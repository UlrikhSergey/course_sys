package com.example.course_sys.controller;


import com.example.course_sys.entity.User;
import com.example.course_sys.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
public class UserRestController {

    @Autowired
    private UserService userService;


    @GetMapping("/users")
    public List<User> getAllUsers(){
        List<User> allUsers = userService.getAllUsers();
        Collections.sort(allUsers);

        return allUsers;
    }

    @GetMapping("/users/{id}")
    public User getUser(@PathVariable Integer id){
       User user = userService.getUser(id);
       return user;
    }

    @PostMapping("/users")
    public User addNewUser (@RequestBody User user){
        userService.saveUser(user);
        return user;
    }

    @PutMapping("/users")
    public User updateUser (@RequestBody User user){
        userService.saveUser(user);
        return user;
    }
    @PutMapping("/users/raiserating1/{id}")
    public User updateRatingOnePoint (@PathVariable Integer id){
      User user = userService.getUser(id);
      user.setRating(user.getRating() + 1);
      userService.saveUser(user);
        return user;
    }
    @PutMapping("/users/raiserating5/{id}")
    public User updateRatingFivePoint (@PathVariable Integer id){
        User user = userService.getUser(id);
        user.setRating(user.getRating() + 5);
        userService.saveUser(user);
        return user;
    }

    @DeleteMapping("/users/{id}")
    public String deleteUser (@PathVariable Integer id){
        userService.deleteUser(id);
        return "User with id = " + id + " was deleted";
    }

    @PutMapping("/users/assignecourse/{empId}/{courseId}")
    public User assignCourse (@PathVariable Integer empId,
                              @PathVariable Integer courseId){
        return userService.assignCourse(empId,courseId);
    }

    @PutMapping("/users/wishcourse/{empId}/{courseId}")
    public User wishCourse (@PathVariable Integer empId,
                              @PathVariable Integer courseId){
        return userService.wishCourse(empId,courseId);
    }

}
