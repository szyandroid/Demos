package com.szy.demos.ui.activity.project;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.szy.demos.R;

public class ProjectActivity extends Activity implements View.OnClickListener {
    private Button btn_http_0, btn_http_1, btn_http_2;

    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project);

        init();
    }

    private void init() {
        mContext = this;
        btn_http_0 = (Button) findViewById(R.id.btn_http_0);
        btn_http_0.setOnClickListener(this);
        btn_http_1 = (Button) findViewById(R.id.btn_http_1);
        btn_http_1.setOnClickListener(this);
        btn_http_2 = (Button) findViewById(R.id.btn_http_2);
        btn_http_2.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.btn_http_0:
                intent = new Intent(mContext, ICSActivity.class);
                break;
            case R.id.btn_http_1:
                intent = new Intent(mContext, TestActivity.class);
                break;
            case R.id.btn_http_2:
                intent = new Intent(mContext, Test2Activity.class);
                break;
        }
        if (intent != null) {
            startActivity(intent);
        }
    }
}
