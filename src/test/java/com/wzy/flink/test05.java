package com.wzy.flink;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by wenzhiyu on 2020/10/26 16:46
 */
public class test05 {
    public static void main(String[] args) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            String a = "42108719961204081X";
            Date d1 = sdf.parse(a.substring(a.length()-8));
            Date d2 = sdf.parse("20201030");
            System.out.println(a.substring(6,14));
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
