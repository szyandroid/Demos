package com.szy.demos.ui.activity.mvp.ui.viewinterface;

import com.baidu.mapapi.model.LatLng;

/**
 * Created by xueshanshan on 2017/6/18.
 */

public interface IMapView {
    //poi检索
    public static final int RES_POI = 1;
    //反地理位置检索
    public static final int RES_CODER = 2;
    //定位
    public static final int RES_LOCATION = 3;

    void onGetData(int code, Object data);

    void onMapStateChangeStart();

    void onMapStateChangeFinish(LatLng latLng);
}
