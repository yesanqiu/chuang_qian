package com.ysq.chuang_qian.dto;

import com.ysq.chuang_qian.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfo {

    private String openId;
    private String nickName;
    private String avatarUrl;
    private Integer gender;
    private String province;
    private String city;
    private String country;

    public UserInfo(User user) {
        this.openId = user.getOpenId();
        this.nickName = user.getNickname();
        this.avatarUrl = user.getAvatarUrl();
        this.gender = user.getGender();
        this.province = user.getProvince();
        this.city = user.getCity();
        this.country = user.getCountry();
    }


    @Override
    public boolean equals(Object obj) {
        if(obj == null){
            System.out.println(1);
            return false;
        }
        if(this == obj){
            System.out.println(2);
            return true;
        }

        if(obj instanceof UserInfo){
            UserInfo userInfo = (UserInfo)obj;
            System.out.println(userInfo.toString());
            System.out.println(this.country);
            if(userInfo.openId .equals(this.openId)  &&
                    userInfo.nickName.equals(this.nickName) &&
                    userInfo.avatarUrl.equals(this.avatarUrl)  &&
                    userInfo.province.equals(this.province)  &&
                    userInfo.city.equals( this.city) &&
                    userInfo.country.equals(this.country)  &&
                    userInfo.gender.equals(this.gender) ){
                System.out.println(3);
                return true;
            }else {
                System.out.println(4);
                return false;
            }
        }
        System.out.println(5);
        return false;
    }
}