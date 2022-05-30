package com.integration.rxjavademo.source;

import android.graphics.Bitmap;
import android.os.Bundle;

import com.integration.rxjavademo.R;

import androidx.appcompat.app.AppCompatActivity;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.internal.operators.observable.ObservableCreate;

/**
 * 1 Observer源码看看
 * 2 Observable创建过程 源码分析
 * 3 subscribe订阅过程 源码分析
 *
 */
public class SourceActivity4 extends AppCompatActivity {
    private static final String TAG = "SourceActivity3";

    ObservableCreate mObservableCreate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_source2);

        Observable.create(
                //这是自定义的 source
                new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<String> e) throws Exception {

            }

        })
                //是ObservableCreate 调用了map
                .flatMap(new Function<String, ObservableSource<Bitmap>>() {
                    @NonNull
                    @Override
                    public ObservableSource<Bitmap> apply(@NonNull String s) throws Exception {
                        return null;
                    }
                })
        .subscribe(new Observer<Bitmap>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull Bitmap bitmap) {

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