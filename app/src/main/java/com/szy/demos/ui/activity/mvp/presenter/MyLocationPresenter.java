package com.szy.demos.ui.activity.mvp.presenter;

/**
 * Created by Administrator on 2017/6/13.
 */

import android.content.Context;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.szy.demos.ui.activity.mvp.ui.viewinterface.IMapView;

/**
 * 位置监听器
 * 监听函数，有新位置的时候，格式化成字符串，输出到屏幕中
 */
public class MyLocationPresenter extends BasePresenter implements BDLocationListener {
    private IMapView mapView;
    private LocationClient client;

    public MyLocationPresenter(Context context, IMapView mapView) {
        this.mapView = mapView;
        setLocationClient(context);
    }


    @Override
    public void onReceiveLocation(BDLocation location) {
        if (location == null || location.getLocType() == BDLocation.TypeServerError) {
            if (mapView != null) {
                mapView.onGetData(IMapView.RES_LOCATION, "服务端定位失败，请您检查是否禁用获取位置信息权限，尝试重新请求定位");
            }
        } else {
            if (mapView != null) {
                mapView.onGetData(IMapView.RES_LOCATION, location);
            }
        }
    }

    @Override
    public void onConnectHotSpotMessage(String s, int i) {

    }

    /**
     * 定位设置
     */
    private void setLocationClient(Context context) {
        client = new LocationClient(context);
        LocationClientOption option = new LocationClientOption();
        //设置定位模式 高精度
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        //设置定位返回的坐标系
        option.setCoorType("GCJ02");
        //只定位一次
        option.setScanSpan(0);
        //设置是否需要地址信息
        option.setIsNeedAddress(true);
        //设置是否使用gps
        option.setOpenGps(true);
        //设置位置描述
        option.setIsNeedLocationDescribe(true);
        client.setLocOption(option);
        client.start();
        client.registerLocationListener(this);
    }

    public void onPause() {
        client.stop();
    }

    @Override
    public void onDestroy() {
        mapView = null;
        onPause();
        client.unRegisterLocationListener(this);
    }
}