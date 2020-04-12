package com.ysq.chuang_qian.service.impl;

import com.ysq.chuang_qian.base.service.impl.BaseServiceImpl;
import com.ysq.chuang_qian.entity.Course;
import com.ysq.chuang_qian.entity.Organization;
import com.ysq.chuang_qian.mapper.CourseMapper;
import com.ysq.chuang_qian.mapper.OrganizationMapper;
import com.ysq.chuang_qian.service.CourseService;
import com.ysq.chuang_qian.service.OrganizationService;
import org.springframework.stereotype.Service;

@Service
public class OrganiztionServiceImpl extends BaseServiceImpl<OrganizationMapper, Organization> implements OrganizationService {
}
