package com.ysq.chuang_qian.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ysq.chuang_qian.base.dto.ResultDTO;
import com.ysq.chuang_qian.base.util.ResultUtil;
import com.ysq.chuang_qian.config.ErrorCode;
import com.ysq.chuang_qian.dto.*;
import com.ysq.chuang_qian.entity.*;
import com.ysq.chuang_qian.service.*;
import com.ysq.chuang_qian.utils.HttpClientUtil;
import com.ysq.chuang_qian.utils.MyUtil;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserController {

    @Value("${range}")
    private Double range;


    @Autowired
    private UserService userService;

    @Autowired
    private OrganizationService organizationService;

    @Autowired
    private SignInService signInService;

    @Autowired
    private SignInTaskService signInTaskService;

    @Autowired
    private CourseService courseService;

    /**
     * 登录接口返回openid 以及 session_key
     * @param code
     * @return
     */
    @PostMapping("/login")
    @ApiOperation("登录接口返回openid 以及 session_key")
    public ResultDTO login(String code)throws Exception{
        if(!StringUtils.isNotBlank(code)){
            return ResultUtil.Error(ErrorCode.UNKNOWERROR);
        }
        String aid = "wx1c31c3e561f234db";
        String as = "236feb4479b626b2649a3efdb199aeaa";
        String apiUrl="https://api.weixin.qq.com/sns/jscode2session?appid="+aid+"&secret="+as+"&js_code="+code+"&grant_type=authorization_code";
        System.out.println(apiUrl);
        String responseBody = HttpClientUtil.doGet(apiUrl);
        System.out.println(responseBody);
        JSONObject jsonObject = JSON.parseObject(responseBody);

        return ResultUtil.Success(jsonObject);
    }

    /**
     * 提交用户信息，若已存在则对比信息是否一致，否则更新数据库
     * @param userInfo
     * @return
     * @throws Exception
     */
    @PostMapping("/saveUserInfo")
    @ApiOperation("提交用户信息，若已存在则对比信息是否一致，否则更新数据库")
    public ResultDTO saveUserInfo(UserInfo userInfo, HttpServletRequest request)throws Exception{
        System.out.println("userInfo"+userInfo);
        List<User> users = userService.findByParams(new User(userInfo.getOpenId()));

        if(users.size()==0){
            //从未登录过,保存用户信息
            return ResultUtil.Success(userService.save(new User(MyUtil.getId("u"),userInfo)));
        }else{
            User user = users.get(0);
            //登录过的用户,判断信息是否有更改
            if(userInfo.equals(new UserInfo(user))){
                //信息没变,不做操作
                request.getSession().setAttribute("uid",user.getId());
                return ResultUtil.Success(user);
            }else{
                //信息有变,更新信息
                userService.update(new User(user.getId(),userInfo));
                return ResultUtil.Success(userService.get(user.getId()));
            }
        }

    }

    @PostMapping("/createOrganization")
    public ResultDTO createOrganization(String uId,String oName)throws Exception{
        User user = userService.get(uId);
        if(user != null) {
            if(user.getOId() != null){
                return ResultUtil.Error(ErrorCode.ISJOIN);
            }
            if (organizationService.findByParams(new Organization(oName)).size() != 0) {
                return ResultUtil.Error(ErrorCode.ISEXIST);
            } else {
                userService.update(new User(uId, true));
                return ResultUtil.Success(organizationService.save(new Organization(MyUtil.getId("o"), oName, uId)));
            }
        }else{
            return ResultUtil.Error(ErrorCode.NOEXIST);
        }
    }

    /**
     * 加入组织
     * @param oId 组织id
     * @return
     * @throws Exception
     */
    @PostMapping("/joinOrganization")
    public ResultDTO joinOrganization(String uId,String oId)throws Exception{
        User user = userService.get(uId);
        if(user != null) {
            if (user.getOId() != null) {
                return ResultUtil.Error(ErrorCode.ISJOIN);
            }
            if (organizationService.get(oId) == null) {
                return ResultUtil.Error(ErrorCode.NOEXIST);
            } else {
                userService.update(new User(uId, oId));
                return ResultUtil.Success(userService.get(uId));
            }
        }else {
            return ResultUtil.Error(ErrorCode.NOEXIST);
        }
    }


    /**
     * 设置签到任务
     * @param uId
     * @param longitude
     * @param latitude
     * @return
     * @throws Exception
     */
    @PostMapping("/setSignIn")
    public ResultDTO setSignIn(String uId,Double longitude,Double latitude)throws Exception{
        User user = userService.get(uId);
        if(user != null){
            if(user.getIsLeader()){

                return ResultUtil.Success(signInService.save(new SignIn(MyUtil.getId("s"),user.getOId(),longitude,latitude)));
            }else{
                return ResultUtil.Error(ErrorCode.NOlEADER);
            }
        }else {
            return ResultUtil.Error(ErrorCode.NOEXIST);
        }
    }


    @GetMapping("/getMyCourse")
    public ResultDTO getMyCourse(String uId)throws Exception{
        User user = userService.get(uId);
        if(user != null){
            List<Course> list = courseService.findByParams(new Course(uId));
            if(list.size()==0){
                return ResultUtil.Error(ErrorCode.NOEXIST);
            }
            String[] courseStrs = list.get(0).getCourse().split("#");
            List<OneDayCourse> courses = new ArrayList<>();
            for(int day = 0; day<7;day++){
                OneDayCourse oneDayCourse = new OneDayCourse();
                String[] courseOne = courseStrs[day * 5].split("<br>");
                String[] courseTwo = courseStrs[day*5+1].split("<br>");
                String[] courseThree = courseStrs[day*5+2].split("<br>");
                String[] courseFour = courseStrs[day*5+3].split("<br>");
                String[] courseFive = courseStrs[day*5+4].split("<br>");
                oneDayCourse.setOne(new CourseDTO(courseOne,signInTaskService.findThisWeek(uId,day+"_1")));
                oneDayCourse.setTwo(new CourseDTO(courseTwo,signInTaskService.findThisWeek(uId,day+"_2")));
                oneDayCourse.setThree(new CourseDTO(courseThree,signInTaskService.findThisWeek(uId,day+"_3")));
                oneDayCourse.setFour(new CourseDTO(courseFour,signInTaskService.findThisWeek(uId,day+"_4")));
                oneDayCourse.setFive(new CourseDTO(courseFive,signInTaskService.findThisWeek(uId,day+"_5")));
                courses.add(oneDayCourse);
            }
            return ResultUtil.Success(courses);
        }else {
            return ResultUtil.Error(ErrorCode.NOEXIST);
        }
    }

    /**
     * 签到
     * @param uId
     * @param longitude
     * @param latitude
     * @return
     * @throws Exception
     */
    @PostMapping("/signIn")
    public ResultDTO signIn(String uId,Double longitude,Double latitude ,String courseIndex)throws Exception{
        User user = userService.get(uId);

        if(user != null){
            List<SignIn> signIns = signInService.findByParams(new SignIn(user.getOId()));
            if(signIns.size() == 1){
                SignIn signIn = signIns.get(0);
                if(MyUtil.getTwoPointDist(latitude,longitude,signIn.getLatitude(),signIn.getLongitude()).compareTo(range) <=0 ) {
                    signInTaskService.save(new SignInTask(MyUtil.getId("st"),uId,longitude,latitude,courseIndex,new Date(),0));
                    return ResultUtil.Success();
                }

            }
        }else {
            return ResultUtil.Error(ErrorCode.NOEXIST);
        }
        return ResultUtil.Success();
    }

    /**
     * 获得我的队友列表
     * @param uId
     * @return
     * @throws Exception
     */
    @GetMapping("/getMyOrganizationments")
    public ResultDTO getMyOrganizationments(String uId)throws Exception{
        User user = userService.get(uId);
        if(user!= null) {
            User find = new User();
            find.setOId(user.getOId());
            return ResultUtil.Success(new OrganizationmentsDTO(organizationService.get(user.getOId()),userService.findByParams(find)));
        }else{
            return ResultUtil.Error(ErrorCode.NOEXIST);
        }
    }





}
