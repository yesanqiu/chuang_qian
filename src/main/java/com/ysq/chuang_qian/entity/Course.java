package com.ysq.chuang_qian.entity;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Course implements Serializable {
    /**
     * 课程id
     */
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "course_id")
    private String courseId;

    /**
     * 用户id
     */
    @Column(name = "user_id")
    private String userId;

    /**
     * 课程
     */
    private String course;

    public static final String COURSE_ID = "courseId";

    public static final String USER_ID = "userId";

    public static final String COURSE = "course";

    public Course(String uId){
        this.userId = uId;
    }
}