package com.ysq.chuang_qian.service;

import com.ysq.chuang_qian.base.service.BaseService;
import com.ysq.chuang_qian.entity.SignInTask;
import com.ysq.chuang_qian.entity.User;

import java.util.List;

public interface SignInTaskService extends BaseService<SignInTask> {

    int findThisWeek(String uid,String index);
}
