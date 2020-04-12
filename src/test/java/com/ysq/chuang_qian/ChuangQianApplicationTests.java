package com.ysq.chuang_qian;

import com.ysq.chuang_qian.mapper.SignInTaskMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(classes = ChuangQianApplication.class)
@RunWith(SpringRunner.class)
public class ChuangQianApplicationTests {

    @Autowired
    private SignInTaskMapper signInTaskMapper;

    @Test
    public void contextLoads() {
    }

}
