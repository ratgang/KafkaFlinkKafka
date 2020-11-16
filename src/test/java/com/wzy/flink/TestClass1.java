package com.wzy.flink;

/**
 * Created by wenzhiyu on 2020/7/27 16:33
 */
public class TestClass1 {
    private String name;
    public String age;
    public void setName(String name){
        this.name = name;
    }
    public String getName(){
        return this.name;
    }

    void printText1(){
        System.out.println("1111");
    }
    private void printText1(String str){
        System.out.println(str);
    }
}
