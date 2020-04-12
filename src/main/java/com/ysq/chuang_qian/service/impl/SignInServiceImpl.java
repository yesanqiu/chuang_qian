package com.ysq.chuang_qian.service.impl;

import com.ysq.chuang_qian.base.service.impl.BaseServiceImpl;
import com.ysq.chuang_qian.entity.Course;
import com.ysq.chuang_qian.entity.SignIn;
import com.ysq.chuang_qian.mapper.CourseMapper;
import com.ysq.chuang_qian.mapper.SignInMapper;
import com.ysq.chuang_qian.service.CourseService;
import com.ysq.chuang_qian.service.SignInService;
import org.springframework.stereotype.Service;

@Service
public class SignInServiceImpl extends BaseServiceImpl<SignInMapper, SignIn> implements SignInService {
}
