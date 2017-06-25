package com.szy.demos.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.bumptech.glide.Glide;
import com.stx.xhb.xbanner.XBanner;
import com.szy.demos.R;

import java.util.ArrayList;
import java.util.List;

public class XbannerActivity extends Activity {
    private XBanner xBanner;
    private ListView listView;

    private List<String> images;
    private List<String> titles;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xbanner);

        mContext = this;
        init();
    }

    private void init() {
        xBanner = (XBanner) findViewById(R.id.xBanner);
        listView = (ListView) findViewById(R.id.listView);

        initData();
    }

    private void initData() {
        // 初始化XBanner中展示的数据
        images = new ArrayList<>();
        images.add("http://imageprocess.yitos.net/images/public/20160910/99381473502384338.jpg");
        images.add("http://imageprocess.yitos.net/images/public/20160910/77991473496077677.jpg");
        images.add("http://imageprocess.yitos.net/images/public/20160906/1291473163104906.jpg");

        xBanner.setData(images);
        xBanner.setmAdapter(new XBanner.XBannerAdapter() {
            @Override
            public void loadBanner(XBanner banner, View view, int position) {
                Glide.with(mContext).load(images.get(position)).into((ImageView) view);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        xBanner.stopAutoPlay();
    }

    @Override
    protected void onStop() {
        super.onStop();
        xBanner.stopAutoPlay();
    }
}
