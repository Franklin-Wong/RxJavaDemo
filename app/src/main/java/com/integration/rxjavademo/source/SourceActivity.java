package com.integration.rxjavademo.source;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;

import com.integration.rxjavademo.R;

import androidx.appcompat.app.AppCompatActivity;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.plugins.RxJavaPlugins;

/**
 * Rxjava Hook 点
 */
public class SourceActivity extends AppCompatActivity {
    private static final String TAG = "SourceActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_source);

        //hook 之前的监听
        RxJavaPlugins.setOnObservableAssembly(new Function<Observable, Observable>() {
            @NonNull
            @Override
            public Observable apply(@NonNull Observable observable) throws Exception {
                //不破坏人家的功能,全局监听项目
                Log.d(TAG,"apply: 全局监听项目，有多少地方使用了RxJava " + observable.toString());
                return observable;
            }
        });



        testHook();
    }

    @SuppressLint("CheckResult")
    public static void testHook() {

        Observable.create(new ObservableOnSubscribe<Object>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Object> e) throws Exception {
                Log.i(TAG, "subscribe: ");
                e.onNext("A");
            }
        }).map(new Function<Object, Boolean>() {
                    @NonNull
                    @Override
                    public Boolean apply(@NonNull Object o) throws Exception {
                        Log.i(TAG, "apply: ");
                        return true;
                    }
                }).subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(@NonNull Boolean aBoolean) throws Exception {
                        Log.i(TAG, "accept: ");
                    }
                });

    }
}