package com.ysq.chuang_qian.service.impl;

import com.ysq.chuang_qian.base.service.impl.BaseServiceImpl;
import com.ysq.chuang_qian.entity.Course;
import com.ysq.chuang_qian.entity.User;
import com.ysq.chuang_qian.mapper.CourseMapper;
import com.ysq.chuang_qian.mapper.UserMapper;
import com.ysq.chuang_qian.service.CourseService;
import com.ysq.chuang_qian.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends BaseServiceImpl<UserMapper, User> implements UserService {
}
