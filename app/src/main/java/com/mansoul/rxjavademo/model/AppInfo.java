package com.mansoul.rxjavademo.model;

import android.graphics.drawable.Drawable;

/**
 * 应用信息
 * Created by Mansoul on 16/5/31.
 */
public class AppInfo {

    private String name;
    private String installTime;
    private int versionCode;
    private String versionName;
    private Drawable icon;

    @Override
    public String toString() {
        return "AppInfo{" +
                "name='" + name + '\'' +
                ", installTime='" + installTime + '\'' +
                ", versionCode=" + versionCode +
                ", versionName='" + versionName + '\'' +
                ", icon=" + icon +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInstallTime() {
        return installTime;
    }

    public void setInstallTime(String installTime) {
        this.installTime = installTime;
    }

    public int getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public Drawable getIcon() {
        return icon;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }
}
