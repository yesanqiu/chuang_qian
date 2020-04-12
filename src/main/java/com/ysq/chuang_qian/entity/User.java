package com.ysq.chuang_qian.entity;

import javax.persistence.*;

import com.ysq.chuang_qian.dto.UserInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private String id;

    @Column(name = "open_id")
    private String openId;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 性别
     */
    private Integer gender;

    /**
     * 头像
     */
    @Column(name = "avatar_url")
    private String avatarUrl;

    /**
     * 城市
     */
    private String city;

    /**
     * 省份
     */
    private String province;

    /**
     * 国家
     */
    private String country;

    /**
     * 是否队长
     */
    @Column(name = "is_leader")
    private Boolean isLeader;

    /**
     * 组织id
     */
    @Column(name = "o_id")
    private String oId;

    public static final String ID = "id";

    public static final String OPEN_ID = "openId";

    public static final String NICKNAME = "nickname";

    public static final String GENDER = "gender";

    public static final String AVATAR_URL = "avatarUrl";

    public static final String CITY = "city";

    public static final String PROVINCE = "province";

    public static final String COUNTRY = "country";

    public static final String IS_LEADER = "isLeader";

    public static final String O_ID = "oId";

    public User(String openId){
        this.openId = openId;
    }

    public User(String id, UserInfo userInfo){
        this.id = id;
        this.isLeader = false;
        this.openId = userInfo.getOpenId();
        this.nickname = userInfo.getNickName();
        this.avatarUrl = userInfo.getAvatarUrl();
        this.gender = userInfo.getGender();
        this.province = userInfo.getProvince();
        this.city = userInfo.getCity();
        this.country = userInfo.getCountry();
    }

    public User(String uId,String oId){
        this.id = uId;
        this.oId = oId;
    }

    public User(String uId,boolean isLeader){
        this.id = uId;
        this.isLeader = isLeader;
    }
}