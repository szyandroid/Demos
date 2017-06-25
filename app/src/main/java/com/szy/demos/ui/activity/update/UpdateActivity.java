package com.szy.demos.ui.activity.update;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.dou361.update.UpdateHelper;
import com.dou361.update.listener.ForceListener;
import com.dou361.update.type.UpdateType;
import com.szy.demos.MainActivity;
import com.szy.demos.R;

public class UpdateActivity extends Activity implements View.OnClickListener {
    public static final String update_url = "http://imtt.dd.qq.com/16891/8C3E058EAFBFD4F1EFE0AAA815250716.apk";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        init();
    }

    private void init() {
        findViewById(R.id.btn_update_1).setOnClickListener(this);
        findViewById(R.id.btn_update_2).setOnClickListener(this);
        findViewById(R.id.btn_update_3).setOnClickListener(this);
        findViewById(R.id.btn_update_4).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_update_1:
                UpdateHelper.getInstance()
                        .setUpdateType(UpdateType.autoupdate)//自动检查更新
                        .setForceListener(new ForceListener() {//强制更新监听，true则退出app
                            @Override
                            public void onUserCancel(boolean force) {
                                if (force) {
                                    finish();
                                }
                            }
                        }).check(this);
                break;
            case R.id.btn_update_2:
                break;
            case R.id.btn_update_3:
                break;
            case R.id.btn_update_4:
                break;
        }
    }
}
