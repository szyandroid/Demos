package com.szy.demos;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.szy.demos.ui.activity.BdMapActivity;
import com.szy.demos.ui.activity.EvnetBusActivity;
import com.szy.demos.ui.activity.FastJsonActivity;
import com.szy.demos.ui.activity.MultiActivity;
import com.szy.demos.ui.activity.TabWidgetActivity;
import com.szy.demos.ui.activity.TimeCheckActivity;
import com.szy.demos.ui.activity.VolleyActivity;
import com.szy.demos.ui.activity.XbannerActivity;
import com.szy.demos.ui.activity.android6.Android6Activity;
import com.szy.demos.ui.activity.mvp.ui.BaiduMapActivity;
import com.szy.demos.ui.activity.project.ProjectActivity;
import com.szy.demos.ui.activity.retrofit.RetrofitActivity;
import com.szy.demos.ui.activity.update.UpdateActivity;
import com.szy.demos.ui.http.Test2Activity;
import com.szy.demos.ui.http.TestActivity;

public class MainActivity extends Activity implements View.OnClickListener {
    private Button btn_xBanner, btn_eventBus, btn_fastJosn, btn_tabwidget, btn_bdMap, btn_http_1, btn_http_2, btn_listview, btn_fragment, btn_volley,
            btn_project, btn_mvp, btn_android6, btn_retrofit, btn_mutillayout, btn_time_check;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = this;
        init();
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            //项目框架
            case R.id.btn_project:
                intent = new Intent(mContext, ProjectActivity.class);
                break;
            //MVP
            case R.id.btn_mvp:
                intent = new Intent(mContext, BaiduMapActivity.class);
                break;
            //Android 6.0适配
            case R.id.btn_android6:
                intent = new Intent(mContext, Android6Activity.class);
                break;
            //retrofit
            case R.id.btn_retrofit:
                intent = new Intent(mContext, RetrofitActivity.class);
                break;
            //多布局
            case R.id.btn_multi_layout:
                break;
            case R.id.btn_xBanner:
                intent = new Intent(mContext, XbannerActivity.class);
                break;
            case R.id.btn_eventBus:
                intent = new Intent(mContext, EvnetBusActivity.class);
                break;
            case R.id.btn_fastJson:
                intent = new Intent(mContext, FastJsonActivity.class);
                break;
            case R.id.btn_tabwidget:
                intent = new Intent(mContext, TabWidgetActivity.class);
                break;
            case R.id.btn_bdMap:
                intent = new Intent(mContext, BdMapActivity.class);
                break;
            case R.id.btn_volley:
                intent = new Intent(mContext, VolleyActivity.class);
                break;
            case R.id.btn_http_1:
                intent = new Intent(mContext, TestActivity.class);
                break;
            case R.id.btn_http_2:
                intent = new Intent(mContext, Test2Activity.class);
                break;
            case R.id.btn_listview:
                intent = new Intent(mContext, MultiActivity.class);
                break;
            case R.id.btn_fragment:
                break;
            case R.id.btn_time_check:
                intent = new Intent(mContext, TimeCheckActivity.class);
                break;
            case R.id.btn_update:
                intent = new Intent(mContext, UpdateActivity.class);
                break;

        }
        if (intent != null) {
            startActivity(intent);
        }
    }

    private void init() {
        btn_project = (Button) findViewById(R.id.btn_project);
        btn_project.setOnClickListener(this);

        btn_mvp = (Button) findViewById(R.id.btn_mvp);
        btn_mvp.setOnClickListener(this);

        btn_android6 = (Button) findViewById(R.id.btn_android6);
        btn_android6.setOnClickListener(this);

        btn_retrofit = (Button) findViewById(R.id.btn_retrofit);
        btn_retrofit.setOnClickListener(this);

        btn_mutillayout = (Button) findViewById(R.id.btn_multi_layout);
        btn_mutillayout.setOnClickListener(this);

        btn_xBanner = (Button) findViewById(R.id.btn_xBanner);
        btn_xBanner.setOnClickListener(this);
        btn_eventBus = (Button) findViewById(R.id.btn_eventBus);
        btn_eventBus.setOnClickListener(this);
        btn_fastJosn = (Button) findViewById(R.id.btn_fastJson);
        btn_fastJosn.setOnClickListener(this);
        btn_tabwidget = (Button) findViewById(R.id.btn_tabwidget);
        btn_tabwidget.setOnClickListener(this);
        btn_bdMap = (Button) findViewById(R.id.btn_bdMap);
        btn_bdMap.setOnClickListener(this);
        btn_http_1 = (Button) findViewById(R.id.btn_http_1);
        btn_http_1.setOnClickListener(this);
        btn_http_2 = (Button) findViewById(R.id.btn_http_2);
        btn_http_2.setOnClickListener(this);
        btn_listview = (Button) findViewById(R.id.btn_listview);
        btn_listview.setOnClickListener(this);
        btn_fragment = (Button) findViewById(R.id.btn_fragment);
        btn_fragment.setOnClickListener(this);
        btn_volley = (Button) findViewById(R.id.btn_volley);
        btn_volley.setOnClickListener(this);
        findViewById(R.id.btn_time_check).setOnClickListener(this);
        findViewById(R.id.btn_update).setOnClickListener(this);

    }
}
