package com.integration.rxjavademo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.LayoutInflater;

import com.integration.rxjavademo.observer_model.Observable;
import com.integration.rxjavademo.observer_model.UserPserson;
import com.integration.rxjavademo.observer_model.WechatServerObsevable;
import com.integration.rxjavademo.source.SourceActivity;

import java.util.Arrays;
import java.util.Stack;

import androidx.annotation.NonNull;

public class MainActivity extends Activity {

    Object mObject;
    Arrays mArrays;
    Stack mStack;
    LayoutInflater mLayoutInflater;

//    ActivityThread mActivityThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        testObservers();

        startActivity(new Intent(this, SourceActivity.class));

    }

    private void testObservers() {

        //编辑文案
        String msg = "好好学习天天行啊";
        //创建一个微信公众服务号
        Observable serverObservable = new WechatServerObsevable();
        //创建用户
        UserPserson pserson01 = new UserPserson("01");
        UserPserson pserson02 = new UserPserson("02");
        UserPserson pserson03 = new UserPserson("03");
        UserPserson pserson04 = new UserPserson("04");
        //订阅公众号
        serverObservable.addObserver(pserson01);
        serverObservable.addObserver(pserson02);
        serverObservable.addObserver(pserson03);
        serverObservable.addObserver(pserson04);

        //公众号更新
        serverObservable.pushMessage(msg);
        // 用户取消订阅
        serverObservable.removeObserver(pserson03);
        System.out.println("取消订阅的分割线——————————————————————————————————————————");
        //公众号再一次更新
        serverObservable.pushMessage(msg);
        //



    }

    Handler mThreadHandler;

    public void loadClass() {

        //BootClassLoader加载的string类，加载Android Framework层class文件
        ClassLoader classLoader = String.class.getClassLoader();

        //是PathClassLoader加载的，通过调用loadClass 方法，
        ClassLoader loader = Activity.class.getClassLoader();

    }
    public void sort() {

        String[] strings = { "ramer", "jelly", "bean", "cake" };
        System.out.println("\t" + Arrays.toString(strings));
        for (int start = 0, end = strings.length - 1; start < end; start++, end--) {
            String temp = strings[end];
            strings[end] = strings[start];
            strings[start] = temp;
        }
    }
    private void test() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                if (mThreadHandler == null) {
                    Looper.prepare();
                    mThreadHandler = new Handler(){
                        @Override
                        public void handleMessage(@NonNull Message msg) {
                            super.handleMessage(msg);

                        }
                    };
                    Looper.loop();
                }

            }
        }).start();
    }
}