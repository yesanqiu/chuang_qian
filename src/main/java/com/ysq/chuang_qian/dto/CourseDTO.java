package com.ysq.chuang_qian.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseDTO {

    private String c_title;

    private String c_time;

    private String c_teacher;

    private String c_address;

    private boolean isSignIn;

    public CourseDTO(String[] course,int isSignIn){
        this.c_time = "";
        if(course.length>4){
            int c_nums = course.length/5;
            for(int i=0;i<c_nums;i++){
                this.c_title = course[i*5];
                if(i != 0){
                    String timeStr = course[i*5+1];
                    StringBuilder time = new StringBuilder(c_time);
                    time.insert(time.length()-1,","+timeStr.substring(timeStr.indexOf("{")+1,timeStr.indexOf("}")));
                    this.c_time = time.toString();
                }else{
                    this.c_time += course[i*5+1];
                }
                this.c_teacher = course[i*5+2];
                this.c_address = course[i*5+3];
            }
        }
        if(course.length>1 &&course.length<=4) {
            this.c_title = course[0];
            this.c_time = course[1];
            this.c_teacher = course[2];
            this.c_address = course[3];
        }
        if(isSignIn==1){
            this.isSignIn = true;
        }else{
            this.isSignIn = false;
        }



    }
}
