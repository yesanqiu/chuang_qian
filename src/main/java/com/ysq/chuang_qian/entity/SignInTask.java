package com.ysq.chuang_qian.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "sign_in_task")
public class SignInTask implements Serializable {
    /**
     * 签到任务id
     */
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "sit_id")
    private String sitId;

    /**
     * 用户id
     */
    @Column(name = "user_id")
    private String userId;

    /**
     * 经度
     */
    private Double longitude;

    /**
     * 维度
     */
    private Double latitude;

    /**
     * 课程标识
     */
    @Column(name = "course_index")
    private String courseIndex;

    /**
     * 时间
     */
    private Date time;

    /**
     * 状态（0为正常，1为迟到，3为请假）
     */
    private Integer state;

    public static final String SIT_ID = "sitId";

    public static final String USER_ID = "userId";

    public static final String LONGITUDE = "longitude";

    public static final String LATITUDE = "latitude";

    public static final String COURSE_INDEX = "courseIndex";

    public static final String TIME = "time";

    public static final String STATE = "state";
}