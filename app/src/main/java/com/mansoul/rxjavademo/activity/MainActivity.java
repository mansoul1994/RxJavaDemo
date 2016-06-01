package com.mansoul.rxjavademo.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.mansoul.rxjavademo.R;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.functions.Action1;

public class MainActivity extends AppCompatActivity {

    private static String TAG = "RxJava";

    private Observable<String> mObservable;
    private Observer<String> mObserver;
    private Action1<String> onNextAction;
    private Subscriber<String> mSubscriber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //初始化被观察者
        initObservable();
        //初始化观察者(订阅者)
        initObserver();

        //关联二者(订阅)
//        mObservable.subscribe(mObserver);
//        mObservable.subscribe(onNextAction);
        mObservable.subscribe(mSubscriber);
    }

    /**
     * 初始化被观察者
     * 1.Observable.create
     * 2.Observable.just(T...)    将会依次调用：onNext("Hello");    onNext("Hi");   onNext("Aloha");    onCompleted();
     * 3.Observable.from(T[]) / from(Iterable<? extends T>)
     */
    private void initObservable() {
        //被观察者

        mObservable = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext("Hello");
                subscriber.onNext("Hi");
                subscriber.onNext("Alpha");
                subscriber.onCompleted();
            }
        });

        //just(T...): 将传入的参数依次发送出来。
        mObservable = Observable.just("Hello", "Hi", "Alpha");

        //from(T[]) / from(Iterable<? extends T>) : 将传入的数组或 Iterable 拆分成具体对象后，依次发送出来。
        String[] words = {"Hello", "Hi", "Aloha"};
        mObservable = Observable.from(words);
    }

    /**
     * 初始化观察者(订阅者)
     * 1.定义Observer
     * 2.定义Action  Actions 可以定义 Subscriber 的每一个部分，Observable.subscribe()函数能够处理一个，
     * 两个或者三个Action参数，分别表示onNext()，onError()和onComplete()函数
     * 3.定义Subscriber( subscribe 过程中，Observer 总是会先被转换成一个 Subscriber 再使用)
     * 通常使用Subscriber
     */
    private void initObserver() {

        //观察者(订阅者)

//        mObserver = new Observer<String>() {
//            @Override
//            public void onCompleted() {
//                System.out.println("onCompleted");
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                System.out.println(e);
//            }
//
//            @Override
//            public void onNext(String s) {
//                Log.i(TAG, s);
//            }
//        };


        Action1<String> onNextAction = new Action1<String>() {
            @Override
            public void call(String s) {
                Log.i(TAG, s);
            }
        };

        mSubscriber = new Subscriber<String>() {
            @Override
            public void onCompleted() {
                Log.i(TAG, "onCompleted");

            }

            @Override
            public void onError(Throwable e) {
                Log.i(TAG, e.toString());
            }

            @Override
            public void onNext(String s) {
                Log.i(TAG, s);

            }
        };
    }
}
