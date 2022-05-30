package com.integration.rxjavademo.jsondemo;

import com.google.gson.Gson;

/**
 * @author Wang
 * @version 1.0
 * @date 2021/6/19 - 19:21
 * @Description com.integration.rxjavademo.jsondemo
 */
public class GsonTest {

    static class GsonBean {
        public int mInt;
        public String mString;

    }

    public static void main(String[] args) {

        System.out.println("main OK");
        Gson gson = new Gson();
        System.out.println(gson.toJson(1));
        System.out.println(gson.toJson("wang"));

        int[] values = {1, 2, 3, 4,};
        System.out.println(gson.toJson(values));

        Integer integer = gson.fromJson("1", int.class);
        System.out.println("integer = " + integer);
        GsonBean gsonBean = new GsonBean();
        gsonBean.mInt = 9;
        gsonBean.mString = "str";
        String s = gson.toJson(gsonBean);
        System.out.println("s = " + s);

        GsonBean bean = gson.fromJson(s, GsonBean.class);
        System.out.println("bean = " + bean);


    }

}
