package com.integration.rxjavademo.demo;

import android.os.Looper;
import android.view.View;

import java.util.concurrent.atomic.AtomicBoolean;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

/**
 * @author Wang
 * @version 1.0
 * @date 2021/7/27 - 0:31
 * @Description com.integration.rxjavademo.demo
 */
public class ViewClickObservable extends Observable<Object> {

    private final View mView;

    public static final Object EVENT = new Object();

    private static Object EVENT_2;

    public ViewClickObservable(View view) {
        mView = view;
    }

    @Override
    protected void subscribeActual(Observer<? super Object> observer) {

        MyListener myListener = new MyListener(mView, observer);
        //
        observer.onSubscribe(myListener);
        // 设置监听事件
        mView.setOnClickListener(myListener);

    }

    static final class MyListener implements View.OnClickListener, Disposable {

        private final View mView;
        //存一份
        private Observer<Object> mObserver;
        //
        private final AtomicBoolean isDisposable = new AtomicBoolean();

        private MyListener(View view, Observer<Object> observer) {
            mView = view;
            mObserver = observer;
        }

        @Override
        public void onClick(View v) {


            if (!isDisposed()) {
                mObserver.onNext(EVENT);
            }
        }

        /**
         * 如果调用了 ，就中断
         */
        @Override
        public void dispose() {
            //如果没有中断过 才有资格  取消 点击事件 v.setOnClickListener(null);
            if (isDisposable.compareAndSet(false, true)) {
                //主线程
                if (Looper.myLooper() == Looper.getMainLooper()) {
                    mView.setOnClickListener(null);

                } else {
                    //主线程 通过Handler 切换
                   /* new Handler(Looper.getMainLooper()){
                        @Override
                        public void handleMessage(@NonNull Message msg) {
                            super.handleMessage(msg);
                            mView.setOnClickListener(null);
                        }
                    };*/

                    //
                    AndroidSchedulers.mainThread().scheduleDirect(new Runnable() {
                        @Override
                        public void run() {
                            mView.setOnClickListener(null);
                        }
                    });
                }
            }
        }

        @Override
        public boolean isDisposed() {
            return isDisposable.get();
        }
    }
}
