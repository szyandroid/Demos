package com.szy.demos.ui.activity.mvp.presenter;

/**
 * Created by Administrator on 2017/6/13.
 */

import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.szy.demos.ui.activity.mvp.ui.viewinterface.IMapView;

import java.util.List;

/**
 * 根据经纬度进行反地理编码
 */
public class MyGetGeoCoderResultPresenter extends BasePresenter implements OnGetGeoCoderResultListener {
    private IMapView iMapView;

    public MyGetGeoCoderResultPresenter(IMapView iMapView) {
        this.iMapView = iMapView;
    }

    @Override
    public void onGetGeoCodeResult(GeoCodeResult geoCodeResult) {

    }

    @Override
    public void onGetReverseGeoCodeResult(ReverseGeoCodeResult result) {
        if (result.getPoiList() != null && result.getPoiList().size() > 0) {
            List<PoiInfo> poiList = result.getPoiList();
            if (iMapView != null) {
                iMapView.onGetData(IMapView.RES_CODER, poiList);
            }
        }
    }

    @Override
    public void onDestroy() {
        iMapView = null;
    }
}
