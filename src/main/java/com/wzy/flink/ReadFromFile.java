package com.wzy.flink;

import org.apache.flink.api.java.utils.ParameterTool;

/**
 * Created by wenzhiyu on 2020/5/20 16:36
 */
public class ReadFromFile {
    public static void main(String[] args) {
        String filePath = null;
        try {
            filePath = ParameterTool.fromArgs(args).get("path");
        }catch (Exception e){
            System.err.println("通过--path <path> 指定路径");
            return;
        }

    }

}
