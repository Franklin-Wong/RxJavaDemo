package com.integration.rxjavademo.source;

import android.os.Bundle;

import com.integration.rxjavademo.R;

import androidx.appcompat.app.AppCompatActivity;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.operators.observable.ObservableCreate;

/**
 * 1 Observer源码看看
 * 2 Observable创建过程 源码分析
 * 3 subscribe订阅过程 源码分析
 *
 */
public class SourceActivity2 extends AppCompatActivity {
    private static final String TAG = "SourceActivity2";

    ClassLoader mClassLoader;
    ObservableCreate mObservableCreate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_source2);

        //结论：new ObservableCreate() { source == 自定义 source }
        // 2 Observable创建过程 源码分析
        Observable.create(
                //自定义source
                new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<String> emitter) throws Exception {
                emitter.onNext("A");
            }
            //3 subscribe订阅过程 源码分析
//             即  ObservableCreate.subscribe
        }).subscribe(
                //这是自定义观察者 Observer
                //1 Observer源码看看
                new Observer<String>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                mObservableCreate.subscribe();
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
}