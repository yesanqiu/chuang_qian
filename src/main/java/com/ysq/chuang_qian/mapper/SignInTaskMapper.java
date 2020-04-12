package com.ysq.chuang_qian.mapper;

import com.ysq.chuang_qian.entity.SignInTask;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface SignInTaskMapper extends Mapper<SignInTask> {


    @Select("SELECT COUNT(*) FROM sign_in_task WHERE YEARWEEK(date_format(time,'%Y-%m-%d')) = YEARWEEK(now()) and user_id = #{uid} and course_index = #{index};")
    int findThisWeek(String uid,String index);
}