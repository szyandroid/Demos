package com.szy.demos.ui.activity.android6;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.szy.demos.R;
import com.yanzhenjie.permission.AndPermission;

import java.util.ArrayList;
import java.util.List;

public class Android6Activity extends Activity implements View.OnClickListener {
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_android6);

        init();
    }

    private void init() {
        mContext = this;
        findViewById(R.id.btn_native).setOnClickListener(this);
        findViewById(R.id.btn_thrid_party).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_native:
                nativeAdaption();
                break;
            case R.id.btn_thrid_party:
                thirdAdaption();
                break;
        }

    }

    private void thirdAdaption() {
        //检查权限
        if (AndPermission.checkPermission(mContext, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            Toast.makeText(mContext, "您已经允许了sdk读写权限，能正常使用", Toast.LENGTH_SHORT).show();
        } else {
            //申请权限
            AndPermission.with(this)
                    .requestCode(100)
                    .permission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    .send();
        }
    }

    private void nativeAdaption() {
        if (checkPublishPermission()) {
            Toast.makeText(mContext, "您已经允许了sdk读写权限，能正常使用", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean checkPublishPermission() {
        boolean result = true;
        if (Build.VERSION.SDK_INT >= 23) {
            //检查是否具有权限
            if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(mContext, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                //申请权限
                ActivityCompat.requestPermissions(Android6Activity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100);
                result = false;
            }
        }
        return result;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 100:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(mContext, "您允许了sdk读写权限，能正常使用", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(mContext, "您拒绝了sdk读写权限，将影响使用", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
