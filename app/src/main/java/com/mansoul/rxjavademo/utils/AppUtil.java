package com.mansoul.rxjavademo.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.mansoul.rxjavademo.model.AppInfo;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 获取手机App信息列表
 * Created by Mansoul on 16/5/31.
 */
public class AppUtil {

    private static List<AppInfo> mAppInfoList;

    public static List<AppInfo> getAppInfo(Context context) {
        mAppInfoList = new ArrayList<>();

        List<PackageInfo> packageInfoList = context.getPackageManager().getInstalledPackages(PackageManager.GET_ACTIVITIES);

        for (PackageInfo packageInfo : packageInfoList) {
            AppInfo appInfo = new AppInfo();

            appInfo.setName(packageInfo.applicationInfo.loadLabel(context.getPackageManager()).toString());
            appInfo.setIcon(packageInfo.applicationInfo.loadIcon(context.getPackageManager()));
            appInfo.setInstallTime(getFormatTime(packageInfo.firstInstallTime));
            appInfo.setVersionCode(packageInfo.versionCode);
            appInfo.setVersionName(packageInfo.versionName);

            mAppInfoList.add(appInfo);

        }
        return mAppInfoList;
    }

    //格式化时间
    public static String getFormatTime(long time) {
        if (time <= 0) {
            return "";
        }
        return SimpleDateFormat.getDateInstance(DateFormat.FULL).format(new Date(time));
    }
}




