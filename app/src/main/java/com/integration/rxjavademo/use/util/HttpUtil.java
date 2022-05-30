package com.integration.rxjavademo.use.util;

/**
 * @author Wang
 * @version 1.0
 * @date 2021/3/17 - 19:02
 * @Description com.integration.rxjavademo.use.util
 */
public class HttpUtil {
    private static final String TAG = "HttpUtil";

    public static String BASE_URL = "";

    public static String getBaseUrl() {
        return BASE_URL;
    }

    public static void setBaseUrl(String baseUrl) {
        BASE_URL = baseUrl;
    }
}
