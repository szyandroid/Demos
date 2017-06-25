package com.szy.demos.view;

import android.app.Dialog;
import android.content.Context;

/**
 * Created by Administrator on 2017/6/23.
 */

public class MyDataTimePickerDialog extends Dialog {
    private WheelPicker dayWheelView;
    private WheelPicker hourWheelView;
    private WheelPicker minuteWheelView;

    public MyDataTimePickerDialog(Context context) {
        super(context);
        init();
    }

    public MyDataTimePickerDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    private void init() {

    }
}
