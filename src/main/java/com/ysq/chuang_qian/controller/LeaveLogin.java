package com.ysq.chuang_qian.controller;

import org.apache.http.client.methods.HttpGet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class LeaveLogin {

    static final String LOGIN_URL = "http://210.38.137.125:8016/default2.aspx";
    static final String GETCODE_URL = "http://210.38.137.125:8016/CheckCode.aspx";
    static final String COURSE_URL = "http://zfjw.gdou.edu.cn:8016/xskbcx.aspx?xh=201711701130&xm=&gnmkdm=";
    static final String indexURL = "http://zfjw.gdou.edu.cn:8016/xs_main.aspx";
    static final String dataURL = "http://zfjw.gdou.edu.cn:8016/xskbcx.aspx";

    RestTemplate client;

    static final String __VIEWSTATE = "dDwxNTMxMDk5Mzc0Ozs+oHR0TeFaplX14wgfE2ZakJztUwk=";
    List<String> cookies = new ArrayList<>();


//    @GetMapping("/getCode")
    public void getImage(HttpServletRequest request, HttpServletResponse response) throws Exception {


        ResponseEntity<Resource> resouce =  client.getForEntity(GETCODE_URL, Resource.class);
        byte[] data = new byte[1024 * 1024];
        response.setContentType("image/Gif; charset=gb2312");
        InputStream inputStream = resouce.getBody().getInputStream();
        OutputStream outputStream = response.getOutputStream();
        int n ;
        while( (n = inputStream.read(data)) != -1){
            System.out.println(n);
            outputStream.write(data);
        }
        outputStream.flush();
        inputStream.close();
        outputStream.close();

        System.out.println(resouce.getHeaders().get("cookie"));
        for (Cookie c : request.getCookies()) {
            if(c.getName().equals("ASP.NET_SessionId")){
                cookies.add(c.getValue());
            }
        }


    }

    @PostMapping("/loginl")
    public void login(String code,HttpServletRequest request) {
        //访问课程表


        System.out.println(code);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.put("ASP.NET_SessionId", cookies);
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        System.out.println(httpHeaders.get("ASP.NET_SessionId"));
        MultiValueMap<String, String> params = new LinkedMultiValueMap();
        params.set("__VIEWSTATE", __VIEWSTATE);
        params.set("txtUserName", "201711701130");
        params.set("Textbox1", "");
        params.set("TextBox2", "yesanqiu131719");
        params.set("txtSecretCode", code);
        params.set("RadioButtonList1", "");
        params.set("Button1", "");
        params.set("lbLanguage", "");
        params.set("hidPdrs", "");
        params.set("hidsc", "");
        System.out.println(params);
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(params, httpHeaders);
        ResponseEntity<String> response = client.postForEntity(LOGIN_URL, requestEntity, String.class);
        System.out.println(response.getStatusCodeValue());
        System.out.println(response.toString());
        for (Cookie c : request.getCookies()) {
            System.out.println(c.getName()+"="+c.getValue());
        }




        HttpMethod getMethod = HttpMethod.GET;

        //转发到首页
        HttpHeaders indexHeaders = new HttpHeaders();
        indexHeaders.put("ASP.NET_SessionId", cookies);
        HttpEntity<MultiValueMap<String, String>> indexRequestEntity = new HttpEntity<>(null, indexHeaders);
        ResponseEntity<String> index = client.exchange(indexURL+"?xh=201711701130",getMethod,indexRequestEntity,String.class);

        System.out.println(index.getStatusCodeValue());
        System.out.println("首页页面");
        System.out.println(index.toString());

        HttpEntity<MultiValueMap<String, String>> courseRequestEntity = new HttpEntity<>(null, httpHeaders);

        ResponseEntity<String> course = client.exchange(COURSE_URL,getMethod,courseRequestEntity,String.class);
        System.out.println(course.getStatusCodeValue());
        System.out.println("课程表页面");
        System.out.println(course.toString());

    }

//    public static List<String> getCookieList(HttpServletRequest request) {
//        List<String> cookieList = new ArrayList<>();
//
//        Cookie[] cookies = request.getCookies();
//        if (cookies == null || cookies.length == 0) {
//            return cookieList;
//        }
//
//        for (Cookie cookie : cookies) {
//            cookieList.add(cookie.getName() + "=" + cookie.getValue());
//            System.out.println(cookie.getName() + "=" + cookie.getValue());
//        }
//
//        return cookieList;
//    }
}
