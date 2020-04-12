package com.ysq.chuang_qian;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import tk.mybatis.spring.annotation.MapperScan;

@EnableScheduling
@SpringBootApplication
@EnableSwagger2
@MapperScan("com.ysq.chuang_qian.mapper")
public class ChuangQianApplication {

    public static void main(String[] args) {
        SpringApplication.run(ChuangQianApplication.class, args);
    }




}
