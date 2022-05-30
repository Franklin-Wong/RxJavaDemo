package com.integration.rxjavademo.demo;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import com.integration.rxjavademo.R;

import java.util.concurrent.TimeUnit;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * @author Wang
 * @version 1.0
 * @date 2021/7/24 - 20:37
 * @Description com.integration.rxjavademo.demo
 */
public class RxJavaActivity extends AppCompatActivity {

    private static final String TAG = "RxJavaActivity";
    private Disposable mDisposable;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx);


        customRxView();
        test00();
        test01();
        test02();
    }

    @SuppressLint("CheckResult")
    private void customRxView() {
        Button bt = findViewById(R.id.button);
        RxView.clicks(bt)
                .throttleFirst(2000, TimeUnit.MILLISECONDS)
                .subscribe(new Consumer<Object>() {
                    @SuppressLint("CheckResult")
                    @Override
                    public void accept(@NonNull Object o) throws Exception {
                        Observable.create(new ObservableOnSubscribe<String>() {
                            @Override
                            public void subscribe(@NonNull ObservableEmitter<String> e)
                                    throws Exception {
                                e.onNext("WANG-------------");
                            }
                        })
                                .subscribe(new Consumer<String>() {
                                    @Override
                                    public void accept(@NonNull String s) throws Exception {
                                        Log.d(TAG, "accept: 终点 ：" + s);

                                    }
                                });
                    }
                });

    }

    /**
     * 抱环问题
     * 注意回收 Disposable 对象
     */
    private void test02() {
         mDisposable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<String> e) throws Exception {
                e.onNext(" WANG");
            }
        })
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(@NonNull String s) throws Exception {

                    }
                });


    }

    /**
     * 注意回收 Disposable 对象
     */
    private void test01() {

        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<String> e) throws Exception {
            }
        }).subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                //处理分发对象
                mDisposable = d;
            }

            @Override
            public void onNext(@NonNull String s) {

            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });

    }

    /**
     * RxJava 的 io.reactivex.android 部分 用于线程调度
     */
    private void test00() {
        mDisposable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<String> e) throws Exception {
                e.onNext("A");
            }
        })
                //
                .observeOn(Schedulers.io())
                //
                .subscribeOn(AndroidSchedulers.mainThread())
                //
                .subscribe(new Consumer<String>() {
                    @SuppressLint("CheckResult")
                    @Override
                    public void accept(@NonNull String s) throws Exception {

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
