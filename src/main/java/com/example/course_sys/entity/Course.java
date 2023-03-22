package com.example.course_sys.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "courses")
public class Course implements Comparable<Course>{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column (name = "area")
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
        return (int) (o.id - this.id);
    }
}
