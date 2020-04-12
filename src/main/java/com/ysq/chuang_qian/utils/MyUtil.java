package com.ysq.chuang_qian.utils;

import org.springframework.data.geo.Distance;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

public class MyUtil {

    public static void cutPartCourse(String html){
    }

    public static String getCourse(String html) {
        if(html.equals("")){
            return null;
        }
        String[][] courses = new String[7][5];
        String[] trs = new String[12];
        int i = 0;
        while (i<12){
            trs[i] = html.substring(html.indexOf("<tr>"),html.indexOf("</tr>"));
            html = html.substring(html.indexOf("</tr>")+1);
            i++;
        }
        for(int tr = 2;tr<12;tr+=2){
            String[] tds = new String[9];
            i = 0;
            String str = trs[tr];
            while (i<9){
                if(str.indexOf("<td")!=-1){
                    tds[i] = str.substring(str.indexOf("<td"),str.indexOf("</td>"));
                    str = str.substring(str.indexOf("</td>")+1);
                }
                i++;

            }
            int index;
            if(tds[8]!=null){
                index = 2;
            }else{
                index = 1;
            }
            i = 0;
            while(i<7){
                String td = tds[index];
                String course = td.substring(td.indexOf(">")+1);
                if(!course.equals("&nbsp;")){
                    courses[i][tr/2-1] = course;
                }
                index++;
                i++;
            }
        }

        String courseStr = "";
        for(int l = 0; l<7; l++){
            for(int o = 0;o<5;o++){
                System.out.println(courses[l][o]);
                courseStr+=courses[l][o]+"#";
            }
        }
        System.out.println(courseStr);
        return courseStr;



    }


    public static String getId(String str){
        return str + "-" + UUID.randomUUID().toString().substring(24);
    }

    private static double rad(double d)
    {
        return d * Math.PI / 180.0;
    }

    public static Double getTwoPointDist(double lat1, double lng1, double lat2, double lng2){
        double EARTH_RADIUS = 6371.393;
        double radLat1 = rad(lat1);
        double radLat2 = rad(lat2);
        double a = radLat1 - radLat2;
        double b = rad(lng1) - rad(lng2);
        double s = 2 * Math.asin(Math.sqrt(Math.abs(Math.pow(Math.sin(a/2),2) +
                Math.cos(radLat1)*Math.cos(radLat2)*Math.pow(Math.sin(b/2),2))));
        s = s * EARTH_RADIUS;
        s = Math.round(s * 1000);
        return s;
    }


    public static void main(String[] args) {
        Date date =new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        System.out.println(c.get(Calendar.DAY_OF_WEEK));
        System.out.println(c.get(Calendar.HOUR_OF_DAY));

    }
}
