package com.szy.demos.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.szy.demos.R;
import com.szy.demos.ui.activity.time.DataPickerActivity;
import com.szy.demos.ui.activity.time.TimeActivity;
import com.szy.demos.ui.activity.time.TimePickerActivity;
import com.szy.demos.ui.activity.time.TimeThridActivity;

public class TimeCheckActivity extends Activity implements View.OnClickListener {
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_check);

        mContext = this;
        init();
    }

    private void init() {
        findViewById(R.id.btn_DatePicker).setOnClickListener(this);
        findViewById(R.id.btn_TimePicker).setOnClickListener(this);
        findViewById(R.id.btn_time_3).setOnClickListener(this);
        findViewById(R.id.btn_time_4).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.btn_DatePicker:
                intent = new Intent(mContext, DataPickerActivity.class);
                break;
            case R.id.btn_TimePicker:
                intent = new Intent(mContext, TimePickerActivity.class);
                break;
            case R.id.btn_time_3:
                intent = new Intent(mContext, TimeActivity.class);
                break;
            case R.id.btn_time_4:
                intent = new Intent(mContext, TimeThridActivity.class);
                break;
        }
        if (intent != null) {
            startActivity(intent);
        }
    }
}
