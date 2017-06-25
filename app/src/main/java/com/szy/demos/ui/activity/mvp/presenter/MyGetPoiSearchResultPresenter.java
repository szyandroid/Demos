package com.szy.demos.ui.activity.mvp.presenter;

/**
 * Created by Administrator on 2017/6/13.
 */

import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
import com.baidu.mapapi.search.poi.PoiResult;
import com.szy.demos.ui.activity.mvp.ui.viewinterface.IMapView;

import java.util.List;

/**
 * 根据关键字查找附近的位置信息
 * 创建POI检索监听者
 */
public class MyGetPoiSearchResultPresenter extends BasePresenter implements OnGetPoiSearchResultListener {
    private IMapView iMapView;
    public int totalPage;

    public MyGetPoiSearchResultPresenter(IMapView iMapView) {
        this.iMapView = iMapView;
    }

    @Override
    public void onGetPoiResult(PoiResult poiResult) {
        if (poiResult != null && poiResult.getAllPoi() != null) {
            List<PoiInfo> allPoi = poiResult.getAllPoi();
            totalPage = poiResult.getTotalPageNum();
            if (iMapView != null) {
                iMapView.onGetData(IMapView.RES_POI, allPoi);
            }
        } else {
            totalPage = 0;
            if (iMapView != null) {
                iMapView.onGetData(IMapView.RES_POI, null);
            }
        }
    }

    @Override
    public void onGetPoiDetailResult(PoiDetailResult poiDetailResult) {

    }

    @Override
    public void onGetPoiIndoorResult(PoiIndoorResult poiIndoorResult) {

    }

    @Override
    public void onDestroy() {
        iMapView = null;
    }
}