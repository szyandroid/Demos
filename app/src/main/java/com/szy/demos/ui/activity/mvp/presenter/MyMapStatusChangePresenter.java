package com.szy.demos.ui.activity.mvp.presenter;

import android.view.MotionEvent;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatus;
import com.szy.demos.application.MyApplication;
import com.szy.demos.ui.activity.mvp.ui.viewinterface.IMapView;

/**
 * Created by xueshanshan on 2017/6/18.
 */

public class MyMapStatusChangePresenter extends BasePresenter implements BaiduMap.OnMapTouchListener, BaiduMap.OnMapStatusChangeListener {
    private IMapView iMapView;
    private boolean changeState;  //手指触摸屏幕状态位
    public boolean mapState;   //地图位置改变状态位

    public MyMapStatusChangePresenter(IMapView iMapView) {
        this.iMapView = iMapView;
    }


    @Override
    public void onDestroy() {
        iMapView = null;
    }

    @Override
    public void onTouch(MotionEvent motionEvent) {
        if (motionEvent.getAction() == MotionEvent.ACTION_MOVE) {
            changeState = true;
            mapState = true;
        }
    }


    @Override
    public void onMapStatusChangeStart(MapStatus mapStatus) {
        if (!changeState) {
            return;
        }
        if (iMapView == null) {
            return;
        }
        MyApplication.getAppHandler().post(new Runnable() {
            @Override
            public void run() {
                iMapView.onMapStateChangeStart();
            }
        });
    }

    @Override
    public void onMapStatusChange(MapStatus mapStatus) {

    }

    @Override
    public void onMapStatusChangeFinish(final MapStatus mapStatus) {
        if (!changeState) {
            return;
        }
        if (iMapView == null) {
            return;
        }
        changeState = false;
        MyApplication.getAppHandler().post(new Runnable() {
            @Override
            public void run() {
                iMapView.onMapStateChangeFinish(mapStatus.target);
            }
        });
    }
}
