package com.wzy.flink;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Created by wenzhiyu on 2020/10/21 14:15
 */
public class test04 {
    public static void main(String[] args) {
        try {
            FileOutputStream fos = new FileOutputStream("E://pyTest/javaFile/hainan/zipTest.zip");
            ZipOutputStream zos = new ZipOutputStream(fos);
            zos.putNextEntry(new ZipEntry("aaa/"));
            for (int i = 1; i < 6; i++){
                zos.putNextEntry(new ZipEntry("aaa/test0" + i + ".txt"));
                FileInputStream fis = new FileInputStream("E://pyTest/test0" +i+".txt" );
                byte[] bytes = new byte[1024];
                int len;
                while ((len = fis.read(bytes)) > -1){
                    fis.read(bytes);
                    zos.write(bytes);
                }
                fis.close();
                zos.closeEntry();
            }
            zos.flush();
            zos.close();
            fos.flush();
            fos.close();

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
