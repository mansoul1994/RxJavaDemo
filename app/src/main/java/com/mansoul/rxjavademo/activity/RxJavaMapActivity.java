package com.mansoul.rxjavademo.activity;

import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.mansoul.rxjavademo.R;
import com.mansoul.rxjavademo.model.Student;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;
import rx.functions.Func1;

public class RxJavaMapActivity extends AppCompatActivity {

    private ImageView mImageView;
    private List<Student> students = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_java_map);
        mImageView = (ImageView) findViewById(R.id.iv_img);

        initDate();
        init();


    }

    private void initDate() {
        List<Student.Course> courses = new ArrayList<>();
        courses.add(new Student.Course());
        courses.add(new Student.Course());

        Student a = new Student();
        a.name = "a";
        a.course = courses;
        students.add(a);
    }

    /**
     * map使用
     * map(): 事件对象的直接变换
     * flatMap() 中返回的是个 Observable 对象
     */
    private void init() {
        //Map
        Observable.just(R.drawable.b)
                .map(new Func1<Integer, Drawable>() {
                    @Override
                    public Drawable call(Integer integer) {
                        return getResources().getDrawable(integer);
                    }
                })
                .subscribe(new Action1<Drawable>() {
                    @Override
                    public void call(Drawable drawable) {
                        mImageView.setImageDrawable(drawable);
                    }
                });


        //flatMap
        Observable.from(students)
                .flatMap(new Func1<Student, Observable<Student.Course>>() {
                    @Override
                    public Observable<Student.Course> call(Student student) {
                        return Observable.from(student.course);
                    }
                })
                .subscribe(new Subscriber<Student.Course>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Student.Course course) {
                        Log.i("tag", course.toString());
                    }
                });
    }
}
