package com.ysq.chuang_qian.service.impl;

import com.ysq.chuang_qian.base.service.impl.BaseServiceImpl;
import com.ysq.chuang_qian.entity.Course;
import com.ysq.chuang_qian.entity.SignIn;
import com.ysq.chuang_qian.entity.SignInTask;
import com.ysq.chuang_qian.mapper.CourseMapper;
import com.ysq.chuang_qian.mapper.SignInTaskMapper;
import com.ysq.chuang_qian.service.CourseService;
import com.ysq.chuang_qian.service.SignInTaskService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SignInTaskServiceImpl extends BaseServiceImpl<SignInTaskMapper, SignInTask> implements SignInTaskService {


    @Override
    public int findThisWeek(String uid,String index) {
        return mapper.findThisWeek(uid,index);
    }
}
