package com.szy.demos.ui.activity.time;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.szy.demos.R;
import com.szy.demos.mode.Time;
import com.szy.demos.utils.AssetsUtils;
import com.szy.demos.view.DatePickerTimeDialog;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import cn.qqtheme.framework.entity.CarNumberCity;
import cn.qqtheme.framework.entity.CarNumberProvince;
import cn.qqtheme.framework.entity.City;
import cn.qqtheme.framework.entity.County;
import cn.qqtheme.framework.entity.Province;
import cn.qqtheme.framework.picker.AddressPicker;
import cn.qqtheme.framework.picker.CarNumberPicker;
import cn.qqtheme.framework.picker.ColorPicker;
import cn.qqtheme.framework.picker.DatePicker;
import cn.qqtheme.framework.picker.DateTimePicker;
import cn.qqtheme.framework.picker.DoublePicker;
import cn.qqtheme.framework.picker.FilePicker;
import cn.qqtheme.framework.picker.LinkagePicker;
import cn.qqtheme.framework.picker.NumberPicker;
import cn.qqtheme.framework.picker.OptionPicker;
import cn.qqtheme.framework.picker.SinglePicker;
import cn.qqtheme.framework.picker.TimePicker;
import cn.qqtheme.framework.picker.WheelPicker;
import cn.qqtheme.framework.util.ConvertUtils;

public class TimeThridActivity extends Activity implements View.OnClickListener {
    private ArrayList data;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_thrid);

        mContext = this;
        init();
        initData();
    }

    //初始化数据
    private void initData() {
        data = new ArrayList<Province>();
        new Thread(new Runnable() {
            @Override
            public void run() {
                String json = AssetsUtils.readText(mContext, "city.json");
                data.addAll(JSON.parseArray(json, Province.class));
            }
        }).start();
    }

    private void init() {
        findViewById(R.id.btn_thrid_1).setOnClickListener(this);
        findViewById(R.id.btn_thrid_2).setOnClickListener(this);
        findViewById(R.id.btn_thrid_3).setOnClickListener(this);
        findViewById(R.id.btn_thrid_4).setOnClickListener(this);
        findViewById(R.id.btn_thrid_5).setOnClickListener(this);
        findViewById(R.id.btn_thrid_6).setOnClickListener(this);
        findViewById(R.id.btn_thrid_7).setOnClickListener(this);
        findViewById(R.id.btn_thrid_8).setOnClickListener(this);
        findViewById(R.id.btn_thrid_9).setOnClickListener(this);
        findViewById(R.id.btn_thrid_10).setOnClickListener(this);
        findViewById(R.id.btn_thrid_11).setOnClickListener(this);
        findViewById(R.id.btn_thrid_12).setOnClickListener(this);
        findViewById(R.id.btn_thrid_13).setOnClickListener(this);
        findViewById(R.id.btn_thrid_14).setOnClickListener(this);
        findViewById(R.id.btn_thrid_15).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //日期选择器
            case R.id.btn_thrid_1: {
                DatePicker picker = new DatePicker(this);
                picker.setTitleText("日期选择器");
                picker.setRangeStart(1900, 1, 1);
                picker.setRangeEnd(2020, 12, 31);
                picker.setSelectedItem(2017, 6, 21);
                picker.setOnDatePickListener(new DatePicker.OnYearMonthDayPickListener() {
                    @Override
                    public void onDatePicked(String year, String month, String day) {
                        showToast(year + "-" + month + "-" + day);
                    }
                });
                picker.show();
            }
            break;
            //时间选择器
            case R.id.btn_thrid_2: {
                TimePicker picker = new TimePicker(this);
                picker.setTitleText("时间选择器");
                picker.setTopLineVisible(false);
                picker.setSelectedItem(10, 20);
                picker.setOnTimePickListener(new TimePicker.OnTimePickListener() {
                    @Override
                    public void onTimePicked(String hour, String minute) {
                        showToast(hour + ":" + minute);
                    }
                });
                picker.show();
            }
            break;
            case R.id.btn_thrid_14: {
                DateTimePicker picker = new DateTimePicker(this, DateTimePicker.YEAR_MONTH_DAY, DateTimePicker.HOUR_24);
                picker.setTitleText("日期时间选择器");
                picker.setDateRangeStart(2000, 1, 1);
                picker.setDateRangeEnd(2020, 12, 31);
                picker.setTimeRangeStart(9, 0);
                picker.setTimeRangeEnd(18, 59);
                picker.setUseWeight(true);
                picker.setSelectedItem(2017, 6, 21, 10, 30);
                picker.setOnDateTimePickListener(new DateTimePicker.OnYearMonthDayTimePickListener() {
                    @Override
                    public void onDateTimePicked(String s, String s1, String s2, String s3, String s4) {
                        showToast(s + "-" + s1 + "-" + s2 + " " + s3 + ":" + s4);
                    }
                });
                picker.show();
            }
            break;
            //单项选择器
            case R.id.btn_thrid_3: {
                OptionPicker picker = new OptionPicker(this, new String[]{
                        "第一项", "第二项", "这是一个很长很长很长很长很长很长很长很长很长的很长很长的很长很长的项"
                });
                picker.setOffset(2);
                picker.setSelectedIndex(1);
                picker.setTextSize(11);
                picker.setOnOptionPickListener(new OptionPicker.OnOptionPickListener() {
                    @Override
                    public void onOptionPicked(int i, String s) {
                        showToast(s);
                    }
                });
                picker.show();
            }
            break;
            //数字选择器
            case R.id.btn_thrid_4: {
                NumberPicker picker = new NumberPicker(this);
                picker.setOffset(2);//偏移量
                picker.setRange(145, 200);//数字范围
                picker.setSelectedItem(172);
                picker.setLabel("厘米");
                picker.setOnNumberPickListener(new NumberPicker.OnNumberPickListener() {
                    @Override
                    public void onNumberPicked(int i, Number number) {
                        showToast(number + "");
                    }
                });
                picker.show();
            }
            break;
            //地址选择器（含省级、地级、县级）
            case R.id.btn_thrid_5: {
                if (data == null || data.size() < 1) {
                    Toast.makeText(mContext, "数据加载中...", Toast.LENGTH_SHORT).show();
                    return;
                }
                AddressPicker picker = new AddressPicker(this, data);
                picker.setSelectedItem("北京", "北京", "海淀");
                picker.setOnAddressPickListener(new AddressPicker.OnAddressPickListener() {
                    @Override
                    public void onAddressPicked(Province province, City city, County county) {
                        showToast(province.getAreaName() + city.getAreaName() + county.getAreaName());
                    }
                });
                picker.show();
            }
            break;
            //地址选择器（含地级、县级）
            case R.id.btn_thrid_6: {
                if (data == null || data.size() < 1) {
                    Toast.makeText(mContext, "数据加载中...", Toast.LENGTH_SHORT).show();
                    return;
                }
                AddressPicker picker = new AddressPicker(this, data);
                picker.setSelectedItem("河南", "郑州", "郑州");
                picker.setHideProvince(true);
                picker.setOnAddressPickListener(new AddressPicker.OnAddressPickListener() {
                    @Override
                    public void onAddressPicked(Province province, City city, County county) {
                        showToast(province.getAreaName() + city.getAreaName() + county.getAreaName());
                    }
                });
                picker.show();
            }
            break;
            //车牌号选择器
            case R.id.btn_thrid_7: {
                CarNumberPicker picker = new CarNumberPicker(this);
                picker.setSelectedItem(new CarNumberProvince("北京"), new CarNumberCity("海淀"));
                picker.show();
            }
            break;
            //二级选择
            case R.id.btn_thrid_8: {
                final List<String> list1 = new ArrayList<>();
                final List<String> list2 = new ArrayList<>();
                for (int i = 0; i < 20; i++) {
                    list1.add("data-" + i);
                    list2.add("item-" + i);
                }
                DoublePicker picker = new DoublePicker(this, list1, list2);
                picker.show();
                picker.setOnPickListener(new DoublePicker.OnPickListener() {
                    @Override
                    public void onPicked(int i, int i1) {
                        showToast(list1.get(i) + "," + list2.get(i1));
                    }
                });
            }
            break;
            case R.id.btn_thrid_9: {
                List<String> list = new ArrayList<>();
                for (int i = 0; i < 30; i++) {
                    list.add("item-" + i);
                }
                SinglePicker<String> picker = new SinglePicker<String>(this, list);
                picker.setSelectedIndex(3);
                picker.show();
                picker.setOnItemPickListener(new SinglePicker.OnItemPickListener<String>() {
                    @Override
                    public void onItemPicked(int i, String s) {
                        showToast(i + "," + s);
                    }
                });
            }
            break;
            //颜色选择
            case R.id.btn_thrid_12: {
                ColorPicker picker = new ColorPicker(this);
                picker.setInitColor(0xFFDD00DD);
                picker.setOnColorPickListener(new ColorPicker.OnColorPickListener() {
                    @Override
                    public void onColorPicked(int pickedColor) {
                        showToast(ConvertUtils.toColorString(pickedColor));
                    }
                });
                picker.show();
            }
            break;
            //文件选择
            case R.id.btn_thrid_13: {
                FilePicker picker = new FilePicker(this, FilePicker.FILE);
                picker.setOnFilePickListener(new FilePicker.OnFilePickListener() {
                    @Override
                    public void onFilePicked(String currentPath) {
                        showToast(currentPath);
                    }
                });
                picker.show();
            }
            break;
            //自定义时间选择
            case R.id.btn_thrid_10:
                showDialog();
                break;
        }
    }

    private void showDialog() {
        DatePickerTimeDialog.Builder builder = new DatePickerTimeDialog.Builder(this)
                .setDialogTitle("选择时间")
//                .setButtonText("确定")
                .setCurrentDate(System.currentTimeMillis())
                .setTitleChangeByTimeFlag(false)
                .setShowHourAndMinute(true)
                .setWheelCyclic(false)
                .setGravity(Gravity.BOTTOM);

        DatePickerTimeDialog dialog = builder.create();
        dialog.setOnConfirmListener(new DatePickerTimeDialog.OnConfirmListener() {
            @Override
            public void onConfirm(Calendar calendar) {
                Date time = calendar.getTime();
                DateFormat format = new SimpleDateFormat("yyyy年M月d日 HH:mm", Locale.getDefault());
                String s = format.format(time);
                Toast.makeText(mContext, "选中时间=" + s, Toast.LENGTH_SHORT).show();
            }
        });
        dialog.show();
    }

    private void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}