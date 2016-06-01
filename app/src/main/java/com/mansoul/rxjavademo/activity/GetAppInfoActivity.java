package com.mansoul.rxjavademo.activity;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.mansoul.rxjavademo.R;
import com.mansoul.rxjavademo.adapter.AppInfoAdapter;
import com.mansoul.rxjavademo.model.AppInfo;
import com.mansoul.rxjavademo.utils.AppUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class GetAppInfoActivity extends AppCompatActivity {

    @BindView(R.id.srl_refresh)
    SwipeRefreshLayout mSwipeRefresh;
    @BindView(R.id.rv_app_list)
    RecyclerView mRvAppList;

    private List<AppInfo> infoList;
    private AppInfoAdapter adapter;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_appinfo);
        ButterKnife.bind(this);

        mContext = getApplicationContext();

        infoList = new ArrayList<>();
        adapter = new AppInfoAdapter(this, infoList);

        LinearLayoutManager llM = new LinearLayoutManager(this);
        mRvAppList.setAdapter(adapter);
        mRvAppList.setLayoutManager(llM);

        mSwipeRefresh.setColorSchemeColors(
                getResources().getColor(R.color.colorAccent),
                getResources().getColor(R.color.colorPrimary),
                getResources().getColor(R.color.colorAccent),
                getResources().getColor(R.color.colorPrimary));
        mSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getListByRxJava();
//                getInfoByRxJava();
            }
        });


    }

    public void getListByRxJava() {
        mSwipeRefresh.setRefreshing(true);
        infoList.clear();
        adapter.notifyDataSetChanged();

        Observable<List<AppInfo>> observable = Observable.create(new Observable.OnSubscribe<List<AppInfo>>() {
            @Override
            public void call(Subscriber<? super List<AppInfo>> subscriber) {
                List<AppInfo> appInfo = AppUtil.getAppInfo(getApplicationContext());
                subscriber.onNext(appInfo);
                subscriber.onCompleted();
            }
        });

        observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .filter(new Func1<List<AppInfo>, Boolean>() {  //添加过滤规则
                    @Override
                    public Boolean call(List<AppInfo> appInfos) {
//                        return appInfos.get();
                        return true;
                    }
                })
                .subscribe(new Observer<List<AppInfo>>() {
                    @Override
                    public void onCompleted() {
                        adapter.notifyDataSetChanged();
                        mSwipeRefresh.setRefreshing(false);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(List<AppInfo> appInfos) {
                        infoList.addAll(appInfos);
                    }
                });
    }

    public void getInfoByRxJava() {
        mSwipeRefresh.setRefreshing(true);
        infoList.clear();
        adapter.notifyDataSetChanged();

        Observable.create(new Observable.OnSubscribe<AppInfo>() {
            @Override
            public void call(Subscriber<? super AppInfo> subscriber) {
                List<PackageInfo> packages = mContext.getPackageManager().getInstalledPackages(PackageManager.GET_ACTIVITIES);
                for (PackageInfo packageInfo : packages) {
                    AppInfo appInfo = new AppInfo();
                    appInfo.setName(packageInfo.applicationInfo.loadLabel(mContext.getPackageManager()).toString());
                    appInfo.setIcon(packageInfo.applicationInfo.loadIcon(mContext.getPackageManager()));
                    appInfo.setInstallTime(AppUtil.getFormatTime(packageInfo.firstInstallTime));
                    appInfo.setVersionCode(packageInfo.versionCode);
                    appInfo.setVersionName(packageInfo.versionName);
                    subscriber.onNext(appInfo);
                }
                subscriber.onCompleted();
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<AppInfo>() {
                    @Override
                    public void onCompleted() {
                        adapter.notifyDataSetChanged();
                        mSwipeRefresh.setRefreshing(false);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(AppInfo appInfo) {
                        int i = 0;
                        i++;
                        System.out.println(i);
                        infoList.add(appInfo);
                    }
                });
    }
}
