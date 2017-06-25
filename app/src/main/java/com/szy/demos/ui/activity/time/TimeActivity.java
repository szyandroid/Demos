package com.szy.demos.ui.activity.time;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import com.szy.demos.R;

import java.util.Calendar;

public class TimeActivity extends Activity {
    private DatePicker mDatePicker;
    private TimePicker mTimePicker;
    private EditText mEditText;

    // 定义5个记录当前时间的变量
    private int mYear;
    private int mMonth;
    private int mDay;
    private int mHour;
    private int mMinute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time);

        init();
    }

    private void init() {
        mDatePicker = (DatePicker) findViewById(R.id.datePicker);
        mTimePicker = (TimePicker) findViewById(R.id.timePicker);
        mEditText = (EditText) findViewById(R.id.show);
        // 获取当前的年、月、日、小时、分钟
        Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        mHour = c.get(Calendar.HOUR);
        mMinute = c.get(Calendar.MINUTE);


        //初始化DatePicker组件，初始化时指定监听器
        mDatePicker.init(mYear, mMonth, mDay, new DatePicker.OnDateChangedListener() {

            @Override
            public void onDateChanged(DatePicker arg0, int year, int month, int day) {
                mYear = year;
                mMonth = month;
                mDay = day;
                // 显示当前日期、时间
                showDate(mYear, mMonth, mDay, mHour, mMinute);
            }
        });

        // 为TimePicker指定监听器
        mTimePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker arg0, int hour, int minute) {
                mHour = hour;
                mMinute = minute;
                // 显示当前日期、时间
                showDate(mYear, mMonth, mDay, hour, minute);
            }
        });
    }

    private void showDate(int year, int month, int day, int hour, int minute) {
        mEditText.setText("日期为：" + year + "年" + month + "月" + day + "日  " + hour + "时" + minute + "分");
    }
}
