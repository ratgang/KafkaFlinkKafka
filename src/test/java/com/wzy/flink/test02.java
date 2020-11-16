package com.wzy.flink;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wenzhiyu on 2020/9/2 13:46
 */
public class test02 {
    public static void main(String[] args) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Map<String,Object> map = new HashMap<>();
        String date1 = sdf.format(new Date());
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -3);
        int month1 = Integer.valueOf(date1.split("-")[1]);
        int month2 = Integer.valueOf(sdf.format(calendar.getTime()).split("-")[1]);


    }
}
