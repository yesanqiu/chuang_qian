package com.ysq.chuang_qian.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import lombok.Data;

@Data
@Table(name = "ask_for_leave")
public class AskForLeave implements Serializable {
    /**
     * 请假id
     */
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "afl_id")
    private String aflId;

    /**
     * 用户id
     */
    @Column(name = "user_id")
    private String userId;

    /**
     * 理由
     */
    private String reason;

    /**
     * 起始时间
     */
    @Column(name = "start_time")
    private Date startTime;

    /**
     * 结束时间
     */
    @Column(name = "end_time")
    private Date endTime;

    /**
     * 发起时间
     */
    @Column(name = "publish_time")
    private Date publishTime;

    /**
     * 状态
     */
    private Integer state;

    public static final String AFL_ID = "aflId";

    public static final String USER_ID = "userId";

    public static final String REASON = "reason";

    public static final String START_TIME = "startTime";

    public static final String END_TIME = "endTime";

    public static final String PUBLISH_TIME = "publishTime";

    public static final String STATE = "state";
}