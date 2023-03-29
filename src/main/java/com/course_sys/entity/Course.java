package com.course_sys.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;


import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "courses")
public class Course implements Comparable<Course> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "area")
    private String area;

    @Column(name = "name")
    private String name;

    @Column(name = "cost")
    private int cost;

    @JsonIgnore
    @ManyToMany(mappedBy = "assignedCourses")
    private Set<User> users = new HashSet<>();

    @JsonIgnore
    @ManyToMany(mappedBy = "wishListCourses")
    private Set<User> usersWishList = new HashSet<>();


    @Override
    public int compareTo(Course o) {
        return o.id - this.id;
    }
}
