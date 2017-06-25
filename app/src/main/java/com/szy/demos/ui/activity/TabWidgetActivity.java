package com.szy.demos.ui.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.FragmentManager;
import android.app.TabActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.view.View;
import android.widget.TabHost;
import android.widget.TextView;

import com.szy.demos.R;
import com.szy.demos.ui.fragment.BaseFragment;
import com.szy.demos.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class TabWidgetActivity extends FragmentActivity {
    private TabHost tabHost;
    private Context mContext;
    private List<TabItem> tabItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_widget);

        mContext = this;
        init();
        initTabData();
        initTabHost();
    }

    private void init() {
        tabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);

//        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
//            @Override
//            public void onTabChanged(String tabId) {
//                Dialog dialog = new AlertDialog.Builder(mContext)
//                        .setTitle("提示")
//                        .setMessage("当前选中:" + tabId + "标签")
//                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                dialog.cancel();
//                            }
//                        }).create();
//                dialog.show();
//            }
//        });
    }

    private void initTabData() {
        tabItems = new ArrayList<>();
        tabItems.add(new TabItem("待确定", BaseFragment.class));
        tabItems.add(new TabItem("待保养", BaseFragment.class));
        tabItems.add(new TabItem("待完成", BaseFragment.class));
        tabItems.add(new TabItem("全部", BaseFragment.class));
    }

    private void initTabHost() {
        tabHost.setup();
        tabHost.getTabWidget().setDividerDrawable(R.color.colorAccent);
        for (int i = 0; i < tabItems.size(); i++) {
            TabItem tabItem = tabItems.get(i);
//            TabHost.TabSpec tabSpec = tabHost.
        }
    }

    class TabItem {
        private String titleString;
        private String title;
        public View view;
        public TextView tvTitle;
        public View imgLine;
        public TextView tvTip;

        //tab对应的Fragment
        public Class<? extends Fragment> fragment;

        public TabItem(String title, Class<? extends Fragment> fragment) {
            this.title = title;
            this.fragment = fragment;
        }

        public View getView() {
            if (this.view == null) {
                this.view = getLayoutInflater().inflate(R.layout.widget_maintain_itemindicator, null);
                this.tvTitle = (TextView) this.view.findViewById(R.id.tvtitle);
                this.imgLine = this.view.findViewById(R.id.imgline);
                this.tvTitle = (TextView) this.view.findViewById(R.id.tvtip);
            }
            return this.view;
        }

        public void setTipState(int state) {
            tvTip.setVisibility(state);
        }

        //切换tab的方法
        public void setChecked(boolean isChecked) {
            if (tvTitle != null) {
                if (isChecked) {
                    tvTitle.setSelected(true);
                } else {
                    tvTitle.setSelected(false);
                }
            }
            if (imgLine != null) {
                if (isChecked) {
                    imgLine.setVisibility(View.VISIBLE);
                } else {
                    imgLine.setVisibility(View.INVISIBLE);
                }
            }
        }

    }
}
