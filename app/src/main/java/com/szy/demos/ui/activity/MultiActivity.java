package com.szy.demos.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.szy.demos.R;
import com.szy.demos.adapter.MultiAdapter;
import com.szy.demos.mode.Item;
import com.szy.demos.utils.DpUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MultiActivity extends Activity {
    private ListView listView;
    private List<Item> list;
    private String[] data;
    private MultiAdapter adapter;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi);

        init();
    }

    private void init() {
        mContext = this;
        list = new ArrayList<>();
        listView = (ListView) findViewById(R.id.listView);
        adapter = new MultiAdapter(mContext, list);
        listView.setAdapter(adapter);
        initData();
    }

    private void initData() {
        data = new String[]{"http://pic.58pic.com/58pic/14/27/45/71r58PICmDM_1024.jpg", "http://pic17.nipic.com/20111122/6759425_152002413138_2.jpg"
                , "http://pic.58pic.com/58pic/13/87/72/73t58PICjpT_1024.jpg", "http://pic.qiantucdn.com/58pic/11/31/58/97p58PICV26.jpg"
                , "http://pic.qiantucdn.com/58pic/11/31/58/97p58PICV26.jpg", "http://img.taopic.com/uploads/allimg/140326/235113-1403260U22059.jpg",
                "http://img3.imgtn.bdimg.com/it/u=3242709860,2221903223&fm=214&gp=0.jpg",
                "http://pic28.photophoto.cn/20130827/0005018362405048_b.jpg"};
        Random random = new Random();
        for (int i = 0; i < 200; i++) {
            Item item = new Item();
            item.type = random.nextInt(3);
            item.content = "item_conetnt_" + i;
            item.path = data[i % data.length];
            list.add(item);
        }
        adapter.notifyDataSetChanged();
    }
}
