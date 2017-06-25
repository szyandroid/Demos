package com.szy.demos.view;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.IntDef;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import com.szy.demos.R;
import com.szy.demos.mode.Time;
import com.szy.demos.utils.StringUtils;
import com.szy.demos.utils.TimeUtil;
import com.szy.demos.utils.ToastHelper;

public class DatePickerTimeDialog extends Dialog implements View.OnClickListener {
    private Context mContext;
    private TextView tvTitle, tvBottom, tv_cancel, tvConfirm;
    private View line;
    private TextView tvcancle;
    ///
    private DateTimePicker mDatePicker;
    //    private DateFormat format = new SimpleDateFormat("yyyy.MM.dd E", Locale.getDefault());
    private DateFormat format = new SimpleDateFormat("yyyy.MM.dd", Locale.getDefault());
    private DateFormat titleFormat = new SimpleDateFormat("yyyy年M月d日 E HH:mm", Locale.getDefault());
    private OnConfirmListener mListener;
    private boolean titleChangeByTimeFlag = true;
    public static final int TITLE_FORM_YYYYMMDDHHMM = 1;
    public static final int TITLE_FORM_COUNTDOWN = 0;


    @IntDef({TITLE_FORM_COUNTDOWN, TITLE_FORM_YYYYMMDDHHMM})
    @Retention(RetentionPolicy.SOURCE)
    public @interface TitleForm {
    }

    @TitleForm
    private int titleForm = 1;

    //设置dialog位置 居中 局底
    private int gravity;
    //时间显示控件布局
    private View view;

    public interface OnConfirmListener {
        public void onConfirm(Calendar calendar);
    }

    private DatePickerTimeDialog(Context context) {
        super(context, R.style.confirmDialogStyle);
        mContext = context;
        init();
    }

    private void init() {
        view = LayoutInflater.from(mContext).inflate(
                R.layout.widget_datatimepicker_dialog, new LinearLayout(mContext), false);
        addContentView(view, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        tvTitle = (TextView) view.findViewById(R.id.tvdialogname);
        mDatePicker = (DateTimePicker) view.findViewById(R.id.datePicker);

        tvBottom = (TextView) view.findViewById(R.id.tvBottom);
        line = view.findViewById(R.id.line);
        tvcancle = (TextView) view.findViewById(R.id.btncancle);
        tv_cancel = (TextView) findViewById(R.id.tv_cancel);
        tvConfirm = (TextView) findViewById(R.id.btnConfirm);
        tv_cancel.setOnClickListener(this);
        tvConfirm.setOnClickListener(this);
        mDatePicker.setOnDateChangedListener(new DateTimePicker.OnDateChangedListener() {
            @Override
            public void onChanged(Calendar calendar) {
                if (titleChangeByTimeFlag) {
                    setTitle(calendar);
                }
            }
        });

//        tvConfirm.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
    }

    public void setBeginDate(Calendar calendar, DateFormat formater) {
        if (mDatePicker != null)
            mDatePicker.setBeginDay(calendar, formater);
    }

    public void setShowHourAndMinute(boolean show) {
        if (mDatePicker != null) {
            mDatePicker.setShowModule(show, show);
        }
    }

    public void setHourData(int start, int end) {
        if (mDatePicker != null) {
            mDatePicker.setHourData(start, end);
        }
    }

    public void setCurrentDate(long milliseconds) {
        Calendar calendar = Calendar.getInstance(Locale.getDefault());
        if (milliseconds > 0)
            calendar.setTimeInMillis(milliseconds);
        if (titleChangeByTimeFlag) {
            setTitle(calendar);
        }
        mDatePicker.setDate(calendar);
    }

    public void setOnConfirmListener(OnConfirmListener listener) {
        mListener = listener;
    }


    private void setTitle(Calendar calendar) {
        String title = "";
        if (titleForm == TITLE_FORM_COUNTDOWN) {
            Calendar today = Calendar.getInstance(Locale.getDefault());

            long between_days = (calendar.getTimeInMillis() + 1 - DateTimePicker.formatCalendar(today).getTimeInMillis()) / (1000 * 3600 * 24);

            int count = Integer.parseInt(String.valueOf(between_days));
            title = count + "天后提醒";
        } else {
            title = titleFormat.format(calendar.getTime()).replace("星期", "周");
        }
        tvTitle.setText(title);
    }

    private void setTitle(String title) {
        tvTitle.setText(title);
    }

    @Override
    public void show() {
        WindowManager m = getWindow().getWindowManager();
        Display d = m.getDefaultDisplay();
        int width;

        WindowManager.LayoutParams lp = getWindow().getAttributes();
        if (gravity == Gravity.CENTER) {
            lp.gravity = Gravity.CENTER;
            lp.width = (int) (d.getWidth() * 0.8);
        } else {
            lp.gravity = Gravity.BOTTOM;
            lp.width = (int) (d.getWidth() * 1.0);
        }
        getWindow().setAttributes(lp);
        super.show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_cancel:
                dismiss();
                break;
            case R.id.btnConfirm:
                if (mListener != null) {
                    mListener.onConfirm(mDatePicker.getDate());
                }
                dismiss();
                break;
        }
    }

    public static class Builder {
        private DatePickerTimeDialog mDialog;
        private long currentDate;
        private Calendar calendar;

        public Builder(Context context) {
            mDialog = new DatePickerTimeDialog(context);
        }

        public Builder setDialogTitle(String title) {
            mDialog.setTitle(title);
            return this;
        }


        public Builder setBottomText(String text){
            if(!StringUtils.isEmpty(text)){
                mDialog.tvBottom.setText(text);
            }
            return this;
        }

        public Builder setTitleChangeByTimeFlag(boolean flag) {
            mDialog.titleChangeByTimeFlag = flag;
            return this;
        }

        public Builder addButton(String name, View.OnClickListener listener) {
            mDialog.tvcancle.setVisibility(View.VISIBLE);
            mDialog.tvcancle.setText(name);
            mDialog.line.setVisibility(View.VISIBLE);
            mDialog.tvcancle.setOnClickListener(listener);
            return this;
        }

        public Builder setWheelCyclic(boolean isCyclic) {
            if (mDialog.mDatePicker != null) {
                mDialog.mDatePicker.setWheelCyclic(isCyclic);
            }
            return this;
        }

        public Builder setCurrentDate(long milliseconds) {
            currentDate = milliseconds;
            return this;
        }

        public Builder setTitleForm(@TitleForm int titleForm) {
            mDialog.titleForm = titleForm;
            return this;
        }

        public Builder setDateFormat(DateFormat format) {
            if (format != null) {
                mDialog.format = format;
            }
            return this;
        }

        public Builder setBeginDate(Calendar calendar) {
            this.calendar = calendar;
            return this;
        }

        public Builder setShowHourAndMinute(boolean show) {
            mDialog.setShowHourAndMinute(show);
            return this;
        }

        public Builder setHourData(int start, int end) {
            mDialog.setHourData(start, end);
            return this;
        }

        public DatePickerTimeDialog create() {
            Calendar today = Calendar.getInstance(Locale.getDefault());
            today.add(Calendar.MONTH, -6); //默认当前时间减6个月
            if (this.calendar == null) {
                this.calendar = today;
            }
            this.calendar.add(Calendar.DATE, 1);
            mDialog.setBeginDate(this.calendar, mDialog.format);
            if (currentDate > 0) {
                mDialog.setCurrentDate(currentDate);
            } else {
                mDialog.setCurrentDate(new Date().getTime());
            }
            return mDialog;
        }

        public Builder setGravity(int gravity) {
            mDialog.gravity = gravity;
            return this;
        }
    }

    public static boolean isSameDay(Calendar calDateA, Calendar calDateB) {
        return calDateA.get(Calendar.YEAR) == calDateB.get(Calendar.YEAR)
                && calDateA.get(Calendar.MONTH) == calDateB.get(Calendar.MONTH)
                && calDateA.get(Calendar.DAY_OF_MONTH) == calDateB
                .get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 日期的校验
     */
    private void dateValidate(Calendar calendar) {
        String time = TimeUtil.toFormatDay(calendar.getTimeInMillis());
        int year = Integer.parseInt(time.substring(0, 4));
        int month = Integer.parseInt(time.substring(5, 7));
        int day = Integer.parseInt(time.substring(8, time.length()));
        Calendar calender = Calendar.getInstance();
        if (year < calender.get(Calendar.YEAR)) {
            ToastHelper.show("选择年份过期,请您重新选择年份!");
            tvConfirm.setEnabled(false);
            tvConfirm.setTextColor(Color.GRAY);
            return;
        } else {
            if (month < calender.get(Calendar.MONTH) + 1) {
                ToastHelper.show("选择月份过期,请您重新选择月份!");
                tvConfirm.setEnabled(false);
                tvConfirm.setTextColor(Color.GRAY);
                return;
            } else {
                if (day < calender.get(Calendar.DATE)) {
                    ToastHelper.show("选择日期过期,请您重新选择日期!");
                    tvConfirm.setEnabled(false);
                    tvConfirm.setTextColor(Color.GRAY);
                    return;
                }
            }
        }
        tvConfirm.setEnabled(true);
        tvConfirm.setTextColor(mContext.getResources().getColor(R.color.blue_txt));
    }

}