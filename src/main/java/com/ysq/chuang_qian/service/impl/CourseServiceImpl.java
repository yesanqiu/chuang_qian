package com.ysq.chuang_qian.service.impl;

import com.ysq.chuang_qian.base.service.impl.BaseServiceImpl;
import com.ysq.chuang_qian.entity.AskForLeave;
import com.ysq.chuang_qian.entity.Course;
import com.ysq.chuang_qian.mapper.AskForLeaveMapper;
import com.ysq.chuang_qian.mapper.CourseMapper;
import com.ysq.chuang_qian.service.AskForLeaveService;
import com.ysq.chuang_qian.service.CourseService;
import org.springframework.stereotype.Service;

@Service
public class CourseServiceImpl extends BaseServiceImpl<CourseMapper, Course> implements CourseService {
}
