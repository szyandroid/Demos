package com.szy.demos.application;

import android.app.Application;
import android.os.Handler;

import com.baidu.mapapi.CoordType;
import com.baidu.mapapi.SDKInitializer;
import com.dou361.update.util.UpdateConstants;
import com.szy.demos.service.LocationService;

/**
 * Created by Administrator on 2017/5/31.
 */

public class MyApplication extends Application {
    public LocationService locationService;
    private static Handler appHandler;
    private static MyApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        init();
        instance = this;
        appHandler = new Handler();
    }

    public static Handler getAppHandler() {
        return appHandler;
    }

    private void init() {
        locationService = new LocationService(getApplicationContext());
        //百度地图初始化
        SDKInitializer.initialize(getApplicationContext());
        SDKInitializer.setCoordType(CoordType.GCJ02);
    }

    public static synchronized MyApplication getInstance() {
        return instance;
    }
}
