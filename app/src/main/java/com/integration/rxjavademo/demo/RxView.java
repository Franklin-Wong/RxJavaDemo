package com.integration.rxjavademo.demo;

import android.view.View;

import io.reactivex.Observable;

/**
 * @author Wang
 * @version 1.0
 * @date 2021/7/27 - 0:29
 * @Description com.integration.rxjavademo.demo
 */
public class RxView {

    public static final String TAG = RxView.class.getSimpleName().toString();

    /**
     * 自定义操作符 函数
     * @param view
     * @return
     */
    public static Observable<Object> clicks(View view) {

        return new ViewClickObservable(view);

    }



}
