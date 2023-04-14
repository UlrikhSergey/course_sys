package com.course_sys.entity;


import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

@RedisHash("CourseFile")
public class CourseFile implements Serializable {
    private String id;

}
