package com.szy.demos.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.szy.demos.R;
import com.szy.demos.mode.Time;
import com.szy.demos.utils.StringUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

public class DateTimePicker extends FrameLayout {
    private long oneDay = 24 * 60 * 60 * 1000;

//    static class KV {
//        public Object value;
//        private DateFormat formater = new SimpleDateFormat("yyyy.MM.dd E", Locale.getDefault());
//
//        private Calendar today = Calendar.getInstance(Locale.getDefault());
//
//        public KV(Object value, DateFormat formater) {
//            this.value = value;
//            this.formater = formater;
//        }
//
//        public KV(Calendar cal, DateFormat formater) {
//            this.value = DateTimePicker.formatCalendar(cal).getTimeInMillis();
//            this.formater = formater;
//        }
//
//        @Override
//        public int hashCode() {
//            final int prime = 31;
//            int result = 1;
//            result = prime * result + (int) (value ^ (value >>> 32));
//            return result;
//        }
//
//        @Override
//        public boolean equals(Object obj) {
//            if (this == obj)
//                return true;
//            if (obj == null || getClass() != obj.getClass())
//                return false;
//            KV other = (KV) obj;
//            if (value != other.value)
//                return false;
//            return true;
//        }
//
//        @Override
//        public String toString() {
//            String name;
//            Calendar cal = Calendar.getInstance();
//            cal.setTimeInMillis(value);
//            name = formater.format(cal.getTime());
//            return name;
//        }
//    }

    private List<String> DAYS;
    private List<String> HOURS;
    private static List<String> MINUTES;

    static {
//		if (HOURS == null) {
//			HOURS = new ArrayList<String>();
//			for (int i = 0; i <= 23; i++) {
//				HOURS.add(i + "");
//			}
//		}
        if (MINUTES == null) {
            MINUTES = new ArrayList<String>();
            for (int i = 0; i <= 59; i += 5) {
                MINUTES.add(i + "");
            }
        }
    }

    private WheelPicker dayWheelView;
    private WheelPicker hourWheelView;
    private WheelPicker minuteWheelView;
    private View minutePadding, hourPadding;
    private OnDateChangedListener mListener;
    private DateFormat formater;

    public void setOnDateChangedListener(OnDateChangedListener listener) {
        mListener = listener;
    }

    public DateTimePicker(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DateTimePicker(Context context) {
        super(context);
        init();
    }

    private void init() {
        View content = LayoutInflater.from(getContext()).inflate(R.layout.widget_datetimepicker, this, false);

        dayWheelView = (WheelPicker) content.findViewById(R.id.daywheel);
        hourWheelView = (WheelPicker) content.findViewById(R.id.hourwheel);
        minuteWheelView = (WheelPicker) content.findViewById(R.id.minutewheel);

        initHourData();
        minuteWheelView.setData(MINUTES);
        WheelPicker.OnWheelChangeListener wheelChange = new WheelPicker.OnWheelChangeListener() {
            @Override
            public void onWheelSelected(int position) {
                if (mListener != null) {
                    mListener.onChanged(getDate());
                }
            }

            @Override
            public void onWheelScrolled(int offset) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onWheelScrollStateChanged(int state) {
                // TODO Auto-generated method stub
            }
        };
        dayWheelView.setOnWheelChangeListener(wheelChange);
        hourWheelView.setOnWheelChangeListener(wheelChange);
        minuteWheelView.setOnWheelChangeListener(wheelChange);

        FrameLayout.LayoutParams p = new LayoutParams(
                android.view.ViewGroup.LayoutParams.FILL_PARENT,
                android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
        addView(content, p);

        hourPadding = this.findViewById(R.id.paddingHour);
        minutePadding = this.findViewById(R.id.paddingMinute);
    }

    private void initHourData() {
        if (HOURS == null) {
            HOURS = new ArrayList<String>();
            for (int i = 0; i <= 23; i++) {
                HOURS.add(i + "");
            }
        }
        hourWheelView.setData(HOURS);
    }

    public void setHourData(int start, int end) {
        HOURS.clear();
        for (int i = start; i <= end; i++) {
            HOURS.add(i + "");
        }
        hourWheelView.setData(HOURS);
    }

    public void setDate(Calendar calendar) {
        calendar.add(Calendar.MINUTE, 4);
        int indexHour = calendar.get(Calendar.HOUR_OF_DAY);
        int indexMinute = calendar.get(Calendar.MINUTE);
        String time = StringUtils.getTimeContent(calendar, formater,StringUtils.TYPE_TIME_CHAR);
        int index = DAYS.indexOf(time);
        if (index == -1) {
            time = StringUtils.getTimeContent(Calendar.getInstance(Locale.getDefault()), formater,StringUtils.TYPE_TIME_CHAR);
            index = DAYS.indexOf(time);
        }
        if (index != -1) {
            dayWheelView.setSelectedItemPosition(index);
            hourWheelView.setSelectedItemPosition(indexHour / (24 / HOURS.size() + (24 % HOURS.size() > 0 ? 1 : 0)));
            minuteWheelView.setSelectedItemPosition(indexMinute / 5);
        }
        if (mListener != null) {
            mListener.onChanged(getDate());
        }
    }

    public Calendar getDate() {
        Calendar calendar = Calendar.getInstance();
        Object value = DAYS.get(dayWheelView.getCurrentItemPosition());
        long time = System.currentTimeMillis();
        if (value instanceof String) {
            String ret = (String) value;
            if (ret.equals("今天")) {
                time = System.currentTimeMillis();
            } else if (ret.equals("明天")) {
                time = System.currentTimeMillis() + oneDay;
            } else if (ret.equals("后天")) {
                time = System.currentTimeMillis() + 2 * oneDay;
            }
        } else if (value instanceof Long) {
            time = (long) value;
        }

        calendar.setTimeInMillis(time);
        calendar.set(Calendar.HOUR_OF_DAY, Integer.valueOf(HOURS
                .get(hourWheelView.getCurrentItemPosition())));
        calendar.set(Calendar.MINUTE,
                minuteWheelView.getCurrentItemPosition() * 5);
        calendar.set(Calendar.MILLISECOND, 0);

        return calendar;
    }

    // private SimpleDateFormat formater = new
    // SimpleDateFormat("M月d日   E",Locale.getDefault());

    public void setBeginDay(Calendar calendar, DateFormat formater) {
        this.formater = formater;
//        long time = formatCalendar(calendar).getTimeInMillis();
        long time = System.currentTimeMillis();
//        int count = 365 * 2;  //两年的时间选择
        int count = 30;
        DAYS = new LinkedList<String>();
        for (int i = 0; i <= count; i++) {
            String kv = null;
            long notime = System.currentTimeMillis();
            String strtime = formater.format(time).toString();
            if (strtime.equals(formater.format(notime).toString())) {
                kv = "今天";
            } else if (strtime.equals(formater.format(notime + oneDay).toString())) {
                kv = "明天";
            } else if (strtime.equals(formater.format(notime + 2 * oneDay).toString())) {
                kv = "后天";
            } else {
                kv = formater.format(time);
            }
            DAYS.add(kv);
            time += oneDay;
        }
        dayWheelView.setData(DAYS);
    }

    public void setWheelCyclic(boolean isCyclic) {
        dayWheelView.setCyclic(isCyclic);
        hourWheelView.setCyclic(isCyclic);
        minuteWheelView.setCyclic(isCyclic);
    }

    public void setShowModule(boolean showHour, boolean showMinute) {
        if (showHour) {
            hourWheelView.setVisibility(View.VISIBLE);
            hourPadding.setVisibility(View.VISIBLE);
        } else {
            hourWheelView.setVisibility(View.GONE);
            hourPadding.setVisibility(View.GONE);
        }

        if (showMinute) {
            minuteWheelView.setVisibility(View.VISIBLE);
            minutePadding.setVisibility(View.VISIBLE);
        } else {
            minuteWheelView.setVisibility(View.GONE);
            minutePadding.setVisibility(View.GONE);
        }
    }

    public static Calendar formatCalendar(Calendar calendar) {
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DATE);
        calendar.clear();
        calendar.set(Calendar.DATE, day);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.YEAR, year);
        return calendar;
    }

    public interface OnDateChangedListener {
        public void onChanged(Calendar calendar);
    }

}
