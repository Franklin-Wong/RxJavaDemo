package com.integration.rxjavademo.jsondemo;

import com.google.gson.annotations.SerializedName;

/**
 * @author Wang
 * @version 1.0
 * @date 2021/6/19 - 20:36
 * @Description com.integration.rxjavademo.jsondemo
 */
public class JsonAdapterTest {


    class MyClass {

        @SerializedName("name")
        String a;
        @SerializedName(value = "name1", alternate = {"name2", "name3"})
        String b;

        String c;

        public MyClass(String a, String b, String c) {
            this.a = a;
            this.b = b;
            this.c = c;
        }



    }


}
