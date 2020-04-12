package com.ysq.chuang_qian.controller;

import com.ysq.chuang_qian.base.dto.ResultDTO;
import com.ysq.chuang_qian.base.util.ResultUtil;
import com.ysq.chuang_qian.config.ErrorCode;
import com.ysq.chuang_qian.entity.Course;
import com.ysq.chuang_qian.service.CourseService;
import com.ysq.chuang_qian.service.UserService;
import com.ysq.chuang_qian.utils.ImageUtil;
import com.ysq.chuang_qian.utils.MyUtil;
import org.apache.commons.httpclient.Cookie;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.persistence.Column;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;


@RestController
@RequestMapping("/course")
@CrossOrigin
public class login {
    @Autowired
    private UserService userService;

    @Autowired
    private CourseService courseService;

    static final String LOGIN_URL = "http://210.38.137.125:8016/default2.aspx";
    static final String GETCODE_URL = "http://210.38.137.125:8016/CheckCode.aspx";
    static final String indexURL = "http://zfjw.gdou.edu.cn:8016/xs_main.aspx";
    static final String dataURL = "http://zfjw.gdou.edu.cn:8016/xskbcx.aspx";


    static final String __VIEWSTATE = "dDwxNTMxMDk5Mzc0Ozs+oHR0TeFaplX14wgfE2ZakJztUwk=";

    HttpClient httpClient = new HttpClient();
    StringBuffer tmpCookies = new StringBuffer();

    @PostMapping("/login")
    public ResultDTO login(String uId, String uName, String pwd, String code, HttpServletRequest request)throws Exception {
        String html = "";
        PostMethod postMethod = new PostMethod(LOGIN_URL);
//        System.out.println(request.getCookies().toString());
        System.out.println(tmpCookies.toString());
        postMethod.setRequestHeader("cookie", tmpCookies.toString());
        NameValuePair[] data = {
                new NameValuePair("__VIEWSTATE", __VIEWSTATE),
                new NameValuePair("txtUserName", uName),
                new NameValuePair("Textbox1", ""),
                new NameValuePair("TextBox2", pwd),
                new NameValuePair("txtSecretCode", code),
                new NameValuePair("RadioButtonList1", ""),
                new NameValuePair("Button1", ""),
                new NameValuePair("lbLanguage", ""),
                new NameValuePair("hidPdrs", ""),
                new NameValuePair("hidsc", "")
        };
        postMethod.setRequestBody(data);
        int statusCode = httpClient.executeMethod(postMethod);
        System.out.println(statusCode);
        if (statusCode == 302) {
            System.out.println("模拟登录成功!");

            httpClient.getParams().setCookiePolicy(CookiePolicy.BROWSER_COMPATIBILITY);
            Cookie[] cookies = httpClient.getState().getCookies();
            StringBuffer tmpCookies = new StringBuffer();
            for (Cookie c : cookies) {
                tmpCookies.append(c.toString() + ";");
                System.out.println("登录页面cookies：" + c.toString());
            }
            GetMethod getMethod2 = new GetMethod(indexURL + "?xh=" + uName);
            //每次访问需授权的网址时带上前面的cookie作为通行证
            getMethod2.setRequestHeader("cookie", tmpCookies.toString());
            httpClient.executeMethod(getMethod2);

            //访问课程表页面
            GetMethod getMethod3 = new GetMethod(dataURL + "?xh=" + uName + "&xm=&gnmkdm=");
            //每次访问需授权的网址时带上前面的cookie作为通行证
            getMethod3.setRequestHeader("referer", indexURL + "?xh=" + uName);
            getMethod3.setRequestHeader("cookie", tmpCookies.toString());
            httpClient.executeMethod(getMethod3);
            html = getMethod3.getResponseBodyAsString();
        }
        String course = MyUtil.getCourse(html);
        if(course == null){
            return ResultUtil.Error(ErrorCode.GETCOURSEFAILURE);
        }
        List<Course> courses = courseService.findByParams(new Course(uId));
        if(courses.size()!= 0 ){
            courseService.update(new Course(courses.get(0).getCourseId(),uId,MyUtil.getCourse(html)));
            return ResultUtil.Success();
        }
        return ResultUtil.Success(courseService.save(new Course(MyUtil.getId("c"),uId,MyUtil.getCourse(html))));
    }


    @GetMapping("/getCode")
    public void getImage(HttpServletRequest request, HttpServletResponse response) throws Exception {
        GetMethod getMethod = new GetMethod(GETCODE_URL);
        try {
            httpClient.getParams().setIntParameter("http.socket.timeout",3000);
            httpClient.getParams().setCookiePolicy(CookiePolicy.BROWSER_COMPATIBILITY);
            httpClient.executeMethod(getMethod);
            Cookie[] cookies = httpClient.getState().getCookies();

            for (Cookie c : cookies) {
                tmpCookies.append(c.toString() + ";");
                System.out.println("验证码页面cookies：" + c.toString());
            }

            response.setContentType("image/Gif; charset=gb2312");
            OutputStream outputStream = response.getOutputStream();
            InputStream ip = getMethod.getResponseBodyAsStream();
//            ImageUtil.removeBackground(ip);
            byte[] date = new byte[1024];
            int n;
            while ((n = ip.read(date)) != -1) {
                outputStream.write(date, 0, n);
            }
            ip.close();
            outputStream.close();
            System.out.println("请输入验证码");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}
