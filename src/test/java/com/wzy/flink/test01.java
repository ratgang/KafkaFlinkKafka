package com.wzy.flink;

import org.mortbay.util.ajax.JSON;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by wenzhiyu on 2020/5/27 9:58
 */
public class test01 {
    public static void main(String[] args) {
        /*String a = "{\"deviceIds\":[5009906],\"cmsList\":[{\"deviceType\":500,\"pixelX\":\"\",\"alignment\":\"4\",\"style\":\"0\",\"dwellTime\":5,\"useType\":\"2\",\"cmsCommonDivList\":[{\"stepNo\":0,\"comment\":\"车辆速度过低\",\"linetype\":\"letter\",\"pictureName\":\"\",\"fontSize\":\"24\",\"color\":\"#ff0000\",\"letterSpacing\":\"0\",\"fontFamily\":\"宋体\"}]}]}";
        Object parse = JSON.parse(a);
        double random = Math.random();
        try {
            Class<?> aClass = Class.forName("com.wzy.flink.TestClass1");
            Method[] methods = aClass.getDeclaredMethods();
            for (Method method: methods){
                System.out.print(method.getName() + " ");
                System.out.println(method.isAccessible());
            }
        }catch (Exception e){
            e.printStackTrace();
        }*/

        //
        String str = "td_rk_id, td_rk_xm, td_rk_xm_id, td_rk_dkmc, td_rk_dkmc_id,\n" +
                "        td_rk_scdkbh, td_rk_dkbh, td_rk_rksj, td_rk_xzq, td_rk_xzq_id, td_rk_dkzl, td_rk_rkmj, td_rk_dkmj,\n" +
                "        td_rk_sfbdc, td_rk_cljsmj, td_rk_wlymj, td_rk_xzjsmj,\n" +
                "        td_rk_nzzmj, td_rk_nzzxzmj, td_rk_zshzynymj, td_rk_zscqjsmj, td_rk_shmj,\n" +
                "        td_rk_sgmj, td_rk_yxgmmj, td_rk_qtfsmj, td_rk_sfxz, td_rk_xzmj,\n" +
                "        td_rk_sfdxmj, td_rk_dxmj, td_rk_sfph, td_rk_phmj, td_rk_sfghyt,\n" +
                "        td_rk_rjl, td_rk_lhl, td_rk_jzmd, td_rk_tdjb, td_rk_tdjb_id,\n" +
                "        td_rk_pgjz, td_rk_jzdj, td_rk_pgfs, td_rk_pgfs_id, td_rk_pgfs_bah, td_rk_dswqk,\n" +
                "        td_rk_dswqk_sm, td_rk_qqkfqk, td_rk_qdfs,\n" +
                "        td_rk_qdcb, td_rk_fscb, td_rk_cbhs_qdcb, td_rk_nyzszy, td_rk_cqbcaz, td_rk_tdsh, td_rk_tdsg,\n" +
                "        td_rk_yxgm, td_rk_qtxg, td_rk_qdcbzdy, td_rk_qxkf, td_rk_tq,\n" +
                "        td_rk_qx, td_rk_tn, td_rk_tdpz, td_rk_tl, td_rk_gs,\n" +
                "        td_rk_ps, td_rk_gd, td_rk_qqkfzdy, td_rk_zjcb, td_rk_zjlx,\n" +
                "        td_rk_cldklx, td_rk_zjcbzdy, td_rk_qtcb, td_rk_djdc, td_rk_tddj,\n" +
                "        td_rk_djpg, td_rk_wqwl, td_rk_qtcbzdy, td_rk_zt, td_rk_jbr_id,\n" +
                "        td_rk_cjsj";
        String newStr = "";
        int i  = 0;
        for (String s: str.replace("\n","").split(",")){
            String s1 = s.trim();
            String[] s1Arr = s1.split("_");
            if (s1Arr.length == 3){
                newStr = newStr + s1 + " = " + "#{" + s1Arr[1] + s1Arr[2].substring(0,1).toUpperCase() + s1Arr[2].substring(1) + "}, ";
            }else {
                newStr = newStr + s1 + " = " + "#{" + s1Arr[1] + s1Arr[2].substring(0,1).toUpperCase() + s1Arr[2].substring(1) + s1Arr[3].substring(0,1).toUpperCase() + s1Arr[3].substring(1) + "}, ";
            }

            i++;
            if (i==5){
                newStr = newStr + "\n";
                i = 0;
            }
        }

        String paramStr = "{";
        for (String s: str.replace("\n","").split(",")){
            String s1 = s.trim();
            String[] s1Arr = s1.split("_");
            if (s1Arr.length == 3){
                paramStr = paramStr + '"' + s1Arr[1] + s1Arr[2].substring(0,1).toUpperCase() + s1Arr[2].substring(1) + '"' + ':' + '"' + '"' + ",";
            }else {
                paramStr = paramStr + '"' + s1Arr[1] + s1Arr[2].substring(0,1).toUpperCase() + s1Arr[2].substring(1) + s1Arr[3].substring(0,1).toUpperCase() + s1Arr[3].substring(1) + '"' + ':' + '"' + '"' + ",";
            }
        }

        int j = 0;
        String str2 = "";
        for (String s: str.replace("\n","").split(",")){
            String s1 = s.trim();
            String[] s1Arr = s1.split("_");
            if (s1Arr.length == 3){
                str2 = str2 + s1 + " " + s1Arr[1] + s1Arr[2].substring(0,1).toUpperCase() + s1Arr[2].substring(1) + ", ";
            }else {
                str2 = str2 + s1 + s1Arr[1] + s1Arr[2].substring(0,1).toUpperCase() + s1Arr[2].substring(1) + s1Arr[3].substring(0,1).toUpperCase() + s1Arr[3].substring(1) + ", ";
            }
            j++;
            if (j==5){
                str2 = str2 + "\n";
                j = 0;
            }
        }
        System.out.println(str2);
        /*SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH,-1);
        System.out.println(sdf.format(calendar.getTime()));
        calendar.add(Calendar.MONTH,-1);
        System.out.println(sdf.format(calendar.getTime()));
        calendar.add(Calendar.MONTH,-11);
        System.out.println(sdf.format(calendar.getTime()));
        Set<String> set = new HashSet<>();
        set.add("a");
        set.add("b");
        Iterator<String> iterator = set.iterator();
        while (iterator.hasNext()){
            String s = iterator.next();
            System.out.println(s);
        }
        int a;
        float b;
        double c;
        long d;
        boolean e;
        char f;
        byte g;
        short h;*/


    }
    private enum TYPE{
        FIREWALL("防水墙"),
        SECRET("公开信息"),
        BALANCER("自私");
        private String typeName;
        TYPE(String typeName){
            this.typeName = typeName;
        }
    }
}
