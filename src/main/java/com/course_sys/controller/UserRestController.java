package com.course_sys.controller;


import com.course_sys.entity.User;
import com.course_sys.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping(value = "/users")
public class UserRestController {

    private final UserService userService;

    public UserRestController(UserService userService) {
        this.userService = userService;
    }


    //Получаем всех пользователей
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    //Получаем пользователя по id
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/{id}")
    public User getUser(@PathVariable Integer id) {
        return userService.getUser(id);
    }

    //Добавляем пользователя
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public User addNewUser(@RequestBody User user) {
        userService.saveUser(user);
        return user;
    }

    //Метод для изменения имени
    @PutMapping("/updatefirstname")
    public void updateFirstName(@RequestBody String firstName) {
        userService.updateFirstName(firstName);
    }

    //Метод для изменения фамилии
    @PutMapping("/updatelastname")
    public void updateLastName(@RequestBody String lastname) {
        userService.updateLastName(lastname);
    }

    //Увеличиваем рейтинг ученику на 1 пункт за легкое задание
    @PreAuthorize("hasAuthority('TEACHER')")
    @PutMapping("/raiserating1/{id}")
    public User updateRatingOnePoint(@PathVariable Integer id) {
        User user = userService.getUser(id);
        user.setRating(user.getRating() + 1);
        userService.saveUser(user);
        return user;
    }

    //Увеличиваем рейтинг ученику на 5 пунктов за сложное задание
    @PreAuthorize("hasAuthority('TEACHER')")
    @PutMapping("/raiserating5/{id}")
    public User updateRatingFivePoint(@PathVariable Integer id) {
        User user = userService.getUser(id);
        user.setRating(user.getRating() + 5);
        userService.saveUser(user);
        return user;
    }

    //Покупка курса
    @PutMapping("/assignecourse/{courseId}")
    public User assignCourse(@PathVariable Integer courseId) {
        return userService.assignCourse(courseId);
    }

    //Добавление курса в избранное
    @PutMapping("/wishcourse/{courseId}")
    public User wishCourse(@PathVariable Integer courseId) {
        return userService.wishCourse(courseId);
    }

}
