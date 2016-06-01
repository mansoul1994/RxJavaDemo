package com.mansoul.rxjavademo.activity;

import android.annotation.TargetApi;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.mansoul.rxjavademo.R;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class AsyncRxJavaActivity extends AppCompatActivity {

    private ImageView mImageView;
    private int iconId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        mImageView = (ImageView) findViewById(R.id.iv_img);

        iconId = R.drawable.a;

        String[] pets = new String[]{"cat", "dog", "pig"};
        Observable.from(pets)
                .subscribe(new Action1<String>() { //直接订阅
                    @Override
                    public void call(String s) {
                        Log.i("tag", s);
                    }
                });

        //展示图片  简单的RxJava使用, 没有异步
//        Observable.create(new Observable.OnSubscribe<Drawable>() {
//            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
//            @Override
//            public void call(Subscriber<? super Drawable> subscriber) {
//                Drawable drawable = getTheme().getDrawable(iconId);
//                subscriber.onNext(drawable);
//                subscriber.onCompleted();
//            }
//        }).subscribe(new Subscriber<Drawable>() {
//            @Override
//            public void onCompleted() {
//
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                Log.i("tag", e.toString());
//            }
//
//            @Override
//            public void onNext(Drawable drawable) {
//                mImageView.setImageDrawable(drawable);
//            }
//        });

        asyncRxJavaTask();

    }

    /**
     * RxJava异步机制
     * Schedulers.immediate(): 直接在当前线程运行，相当于不指定线程。这是默认的 Scheduler。
     * Schedulers.newThread(): 总是启用新线程，并在新线程执行操作。
     * Schedulers.io(): I/O 操作(无数量上限的线程池，可以重用空闲的线程，因此多数情况下 io() 比 newThread() 更有效率)
     * Schedulers.computation(): 计算所使用的 Scheduler
     * Android 专用的 AndroidSchedulers.mainThread()
     */
    private void asyncRxJavaTask() {
        Observable.create(new Observable.OnSubscribe<Drawable>() {
            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void call(Subscriber<? super Drawable> subscriber) {
                Drawable drawable = getTheme().getDrawable(iconId);
                subscriber.onNext(drawable);
                subscriber.onCompleted();
            }
        })
                .subscribeOn(Schedulers.io())   // 指定subscribe()发生在IO线程
                .observeOn(AndroidSchedulers.mainThread()) // 指定 Subscriber 的回调发生在主线程
//                .observeOn(Schedulers.newThread())
                .subscribe(new Subscriber<Drawable>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("tag", e.toString());
                    }

                    @Override
                    public void onNext(Drawable drawable) {
                        mImageView.setImageDrawable(drawable);
                    }
                });
    }
}
