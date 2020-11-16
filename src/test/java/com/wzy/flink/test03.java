package com.wzy.flink;

import sun.reflect.Reflection;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by wenzhiyu on 2020/9/11 15:44
 */
public class test03 {
    public static void main(String[] args) throws InterruptedException{
        Thread t1 = new Thread(){
            @Override
            public void run() {
                try {
                    sleep(1000);
                }catch (Exception e){
                    e.printStackTrace();
                }
                System.out.println("t1 run");
            }
        };
        t1.start();
        t1.join();
        Thread t2 = new Thread(){
            @Override
            public void run() {
                System.out.println("t2 run");
            }
        };
        t2.start();
        t2.join();
        System.out.println("main finish");
    }
    public void test(){
        
    }
}
