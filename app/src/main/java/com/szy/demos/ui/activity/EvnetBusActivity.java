package com.szy.demos.ui.activity;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.szy.demos.R;
import com.szy.demos.mode.EventData;
import com.szy.demos.utils.StringUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class EvnetBusActivity extends Activity implements View.OnClickListener {
    private Button btn;
    private TextView tv_content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evnet_bus);

        init();
    }

    private void init() {
        btn = (Button) findViewById(R.id.btn);
        btn.setOnClickListener(this);
        tv_content = (TextView) findViewById(R.id.tv_content);

        EventBus.getDefault().register(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn:
                EventBus.getDefault().post(new EventData("这是来自EventBus的数据"));
                break;
        }
    }

    @Subscribe
    public void onEventMainThread(EventData data) {
        if (data != null) {
            String msg = data.getData();
            if (!StringUtils.isEmpty(msg)) {
                tv_content.setText(msg);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
