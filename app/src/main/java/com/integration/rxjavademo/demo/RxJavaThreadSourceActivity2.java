package com.integration.rxjavademo.demo;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import java.util.concurrent.Callable;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.plugins.RxJavaPlugins;

/**
 * @author Wang
 * @version 1.0
 * @date 2021/7/24 - 20:37
 * @Description com.integration.rxjavademo.demo
 */
public class RxJavaThreadSourceActivity2 extends AppCompatActivity {
    private static final String TAG = "RxJavaThreadSource";
    private Disposable mDisposable;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new Thread() {
            @Override
            public void run() {
                super.run();
                test00();
            }
        }.start();
        new Thread(){
            @Override
            public void run() {
                super.run();
                // 保证在子线程可以使用
                new Handler(Looper.getMainLooper()){
                    @Override
                    public void handleMessage(@androidx.annotation.NonNull Message msg) {
                        super.handleMessage(msg);
                        //UI操作 ui线程没问题


                    }
                };
            }
        }.start();
    }

    /**
     * RxJava 的 io.reactivex.android 部分 用于线程调度
     */
    private void test00() {
        /**
         * hook  IO 传递
         * 全局 监听
         */
        RxJavaPlugins.setIoSchedulerHandler(new Function<Scheduler, Scheduler>() {
            @NonNull
            @Override
            public Scheduler apply(@NonNull Scheduler scheduler) throws Exception {
                Log.d(TAG, "apply:全局监听  Scheduler  ： "+ scheduler);
                return scheduler;
            }
        });
        RxJavaPlugins.setInitIoSchedulerHandler(new Function<Callable<Scheduler>, Scheduler>() {
            @NonNull
            @Override
            public Scheduler apply(@NonNull Callable<Scheduler> schedulerCallable) throws Exception {
                Log.d(TAG, "apply : 全局监听  schedulerCallable.call() ："+ schedulerCallable.call());
                return schedulerCallable.call();
            }
        });

        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<String> e) throws Exception {
                e.onNext("WANG ——————");
                Log.d(TAG, "subscribe: 自定义source的 ："+Thread.currentThread().getName());
            }
        })
                //给下面的代码分配线程
                .observeOn(
                        // 创建一个主线程的handler
                        AndroidSchedulers.mainThread()


                // 给上面的代码分配线程
                //把 new IOSscheduler()  传递进去
//                .subscribeOn(
                        //io 耗时操作的异步  new IOSscheduler() ---> 线程池管理
//                        Schedulers.io()
                )
                // 终点
                //是由 ObservableSubscribeOn.subscribe 对象调用的
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        /**
                         * 回收 Disposable
                         */
                        mDisposable = d;

                        Log.d(TAG, "onSubscribe: "+Thread.currentThread().getName());
                    }

                    @Override
                    public void onNext(@NonNull String s) {

                        Log.d(TAG, "onNext: "+Thread.currentThread().getName()+"; s= "+s);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete: "+Thread.currentThread().getName()+";");
                    }
                });

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mDisposable != null) {
            if (!mDisposable.isDisposed()) {
                mDisposable.dispose();
            }
        }

    }
}
