package com.szy.demos.ui.activity.mvp.ui;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ZoomControls;

import com.baidu.location.BDLocation;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMapOptions;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.szy.demos.R;
import com.szy.demos.ui.activity.mvp.ui.adapter.BaiduMapAdapter;
import com.szy.demos.ui.activity.mvp.presenter.MyGetGeoCoderResultPresenter;
import com.szy.demos.ui.activity.mvp.presenter.MyLocationPresenter;
import com.szy.demos.ui.activity.mvp.presenter.MyMapStatusChangePresenter;
import com.szy.demos.ui.activity.mvp.ui.viewinterface.IMapView;
import com.szy.demos.ui.activity.mvp.utils.DataUtils;

import java.util.ArrayList;
import java.util.List;

public class BaiduMapActivity extends Activity implements View.OnClickListener, AdapterView.OnItemClickListener, IMapView {
    private Context mContext;
    //我的位置
    private BDLocation myLocation;
    private TextView refreshText;
    private ListView listView;
    private ImageView original;
    private LinearLayout linear_back;
    private BaiduMapAdapter adapter;

    static MapView mMapView = null;
    // 定位相关
    public MyLocationPresenter locationPresenter;
    //反地理检索
    private GeoCoder mSearch = null;
    private MyGetGeoCoderResultPresenter coderResultPresenter;
    private MyMapStatusChangePresenter myMapStatusChangePresenter;

    private List<PoiInfo> datas;
    private PoiInfo lastInfo = null;
    private BaiduMap mBaiduMap;
    private MapStatusUpdate myselfU;
    private int preCheckedPosition = 0;//点击的前一个位置

    private BaiduSDKReceiver mBaiduReceiver;
    private Handler mHandler = new Handler();

    private boolean isSearchFinished, isGeoCoderFinished;

    //当前地图级别
    private float mapLevel;

    /**
     * 构造广播监听类，监听 SDK key 验证以及网络异常广播
     */
    public class BaiduSDKReceiver extends BroadcastReceiver {
        public void onReceive(Context context, Intent intent) {
            String s = intent.getAction();
            if (s.equals(SDKInitializer.SDK_BROADTCAST_ACTION_STRING_PERMISSION_CHECK_ERROR)) {
                String st2 = "key validation error!Please on AndroidManifest.xml file check the key set";
                Toast.makeText(mContext, st2, Toast.LENGTH_SHORT).show();
            } else if (s.equals(SDKInitializer.SDK_BROADCAST_ACTION_STRING_NETWORK_ERROR)) {
                Toast.makeText(mContext, "网络异常", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mvp);
        mContext = this;
        fillStaticUI();

        locationPresenter = new MyLocationPresenter(mContext, this);
        myMapStatusChangePresenter = new MyMapStatusChangePresenter(this);

        //注册事件
        registerEvent();
    }


    protected void fillStaticUI() {
        mapLevel = 17.0f;
        View view = findViewById(R.id.title);
        linear_back = (LinearLayout) view.findViewById(R.id.left_title_layout);
        linear_back.setOnClickListener(this);
        original = (ImageView) findViewById(R.id.bmap_local_myself);
        original.setOnClickListener(this);
        listView = (ListView) findViewById(R.id.bmap_listview);
        mMapView = (MapView) findViewById(R.id.bmap_View);
        refreshText = (TextView) findViewById(R.id.bmap_refresh);

        datas = new ArrayList<>();
        adapter = new BaiduMapAdapter(this, datas);
        listView.setAdapter(adapter);

        MyLocationConfiguration.LocationMode mCurrentMode = MyLocationConfiguration.LocationMode.NORMAL;
        mMapView.showZoomControls(false);
        mMapView.showScaleControl(false);
        mBaiduMap = mMapView.getMap();

        // 隐藏百度logo ZoomControl
        int count = mMapView.getChildCount();
        for (int i = 0; i < count; i++) {
            View child = mMapView.getChildAt(i);
            if (child instanceof ImageView || child instanceof ZoomControls) {
                child.setVisibility(View.INVISIBLE);
            }
        }
        // 隐藏比例尺
        mMapView.showScaleControl(true);
        mMapView = new MapView(this, new BaiduMapOptions());
        mBaiduMap.setMyLocationConfigeration(new MyLocationConfiguration(
                mCurrentMode, true, null));
        mBaiduMap.setMyLocationEnabled(true);

        // 注册 SDK 广播监听者
        IntentFilter iFilter = new IntentFilter();
        iFilter.addAction(SDKInitializer.SDK_BROADTCAST_ACTION_STRING_PERMISSION_CHECK_ERROR);
        iFilter.addAction(SDKInitializer.SDK_BROADCAST_ACTION_STRING_NETWORK_ERROR);
        mBaiduReceiver = new BaiduSDKReceiver();
        registerReceiver(mBaiduReceiver, iFilter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.left_title_layout:
                finish();
                break;
            case R.id.bmap_local_myself:
                if (!myMapStatusChangePresenter.mapState) {
                    return;
                } else {
                    myMapStatusChangePresenter.mapState = false;
                }
                if (myLocation != null) {
                    datas.clear();
                    adapter.notifyDataSetChanged();
                    mapLevel = 17.0f;
                    refreshText.setVisibility(View.VISIBLE);
                    listView.setVisibility(View.GONE);
                    markMyLocation(myLocation);
                }
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (preCheckedPosition != position) {
            adapter.setSelection(position);
            View view1 = listView.getChildAt(preCheckedPosition - listView.getFirstVisiblePosition());
            ImageView checked = null;
            if (view1 != null) {
                checked = (ImageView) view1.findViewById(R.id.adapter_baidumap_location_checked);
                checked.setVisibility(View.INVISIBLE);
            }
            checked = (ImageView) view.findViewById(R.id.adapter_baidumap_location_checked);
            checked.setVisibility(View.VISIBLE);

            preCheckedPosition = position;
            myMapStatusChangePresenter.mapState = true;
            PoiInfo info = datas.get(position);
            lastInfo = info;

            LatLng convertLatLng = new LatLng(info.location.latitude, info.location.longitude);
            myselfU = MapStatusUpdateFactory.newLatLngZoom(convertLatLng, mapLevel);
            mBaiduMap.animateMapStatus(myselfU);
        }
    }

    /**
     * 注册事件
     */
    private void registerEvent() {
        listView.setOnItemClickListener(this);
        /*
        *拖拽地图时回调该方法
        * 将MapView中央的物理坐标转换成对应的地理坐标，再通过反地理编码获取周边信息
        * */
        mBaiduMap.setOnMapTouchListener(myMapStatusChangePresenter);
        //监听地理位置发生改变
        mBaiduMap.setOnMapStatusChangeListener(myMapStatusChangePresenter);

        //地理位置反编码
        mSearch = GeoCoder.newInstance();
        coderResultPresenter = new MyGetGeoCoderResultPresenter(this);
        mSearch.setOnGetGeoCodeResultListener(coderResultPresenter);
    }

    //更新操作
    private void refreshAdapter() {
        if (isSearchFinished || isGeoCoderFinished) {
            adapter.notifyDataSetChanged();
            preCheckedPosition = 0;
            listView.setSelection(0);
            adapter.setSelection(0);
            refreshText.setVisibility(View.GONE);
            listView.setVisibility(View.VISIBLE);
            isSearchFinished = false;
            isGeoCoderFinished = false;

            if (datas != null && datas.size() > 0) {
                PoiInfo info = datas.get(0);

                if (lastInfo == null) {
                    lastInfo = new PoiInfo();
                }
                lastInfo.location = info.location;
                lastInfo.address = info.address;
                lastInfo.name = info.name;
            }
        }
    }

    private void markMyLocation(BDLocation location) {
        mBaiduMap.clear();

        //设置地图缩放级别
        LatLng convertLatLng = new LatLng(location.getLatitude(), location.getLongitude());
        myselfU = MapStatusUpdateFactory.newLatLngZoom(convertLatLng, mapLevel);
        mBaiduMap.animateMapStatus(myselfU);

        OverlayOptions myselfOOA = new MarkerOptions().position(convertLatLng).icon(BitmapDescriptorFactory
                .fromResource(R.mipmap.bd_weizhi))
                .zIndex(4).draggable(true);
        mBaiduMap.addOverlay(myselfOOA);

        registerSearch(convertLatLng, location.getStreet());
    }

    //注册检索
    private void registerSearch(LatLng latLng, String key) {
        if (latLng != null) {
            // 反Geo检索
            mSearch.reverseGeoCode(new ReverseGeoCodeOption().location(latLng));
        }
    }

    @Override
    public void onGetData(int code, Object data) {
        DataUtils utils = DataUtils.newInstance();
        if (code == IMapView.RES_POI) {
            if (data != null) {
                if (data instanceof List) {
                    List<PoiInfo> list = (List<PoiInfo>) data;
                    utils.addData(list, datas);
                }
            }
            isSearchFinished = true;
        } else if (code == IMapView.RES_CODER) {
            if (data != null) {
                if (data instanceof List) {
                    List<PoiInfo> list = (List<PoiInfo>) data;
                    utils.addData(list, datas);
                }
                isGeoCoderFinished = true;
            }
        } else if (code == IMapView.RES_LOCATION) {
            if (data != null) {
                if (data instanceof String) {
                    String ret = (String) data;
                    Toast.makeText(mContext, ret, Toast.LENGTH_SHORT).show();
                } else if (data instanceof BDLocation) {
                    myLocation = (BDLocation) data;
                    markMyLocation(myLocation);
                }
            }
        }
        refreshAdapter();
    }

    @Override
    public void onMapStateChangeStart() {
        if (refreshText != null && refreshText.getVisibility() != View.VISIBLE) {
            refreshText.setVisibility(View.VISIBLE);
            listView.setVisibility(View.GONE);
        }
    }

    @Override
    public void onMapStateChangeFinish(LatLng latLng) {
        if (datas != null && datas.size() > 0) {
            datas.clear();
            adapter.notifyDataSetChanged();
        }
        registerSearch(latLng, null);

        mapLevel = mBaiduMap.getMapStatus().zoom;
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mMapView.onPause();
        locationPresenter.onPause();
        lastInfo = null;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
        locationPresenter.onDestroy();
        coderResultPresenter.onDestroy();
        myMapStatusChangePresenter.onDestroy();
        unregisterReceiver(mBaiduReceiver);
    }
}
