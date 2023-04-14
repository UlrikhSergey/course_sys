package com.course_sys.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;


import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "courses",uniqueConstraints = { @UniqueConstraint(name = "UniqueNumberAndStatus", columnNames = { "name" }) })
public class Course implements Comparable<Course> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String area;
    private String name;
    private int cost;


    @Override
    public int hashCode() {
        return Objects.hash(id, area, name, cost, users, usersWishList);
    }

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
