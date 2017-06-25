package com.szy.demos.utils;

/**
 * Created by Administrator on 2017/6/23.
 */


import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

import com.szy.demos.application.MyApplication;

public class ToastHelper {

    private static int duration = 3000;
    private static Toast mToast;

    public static void showOnceToast(String text) {
        if (mToast == null) {
            mToast = Toast.makeText(MyApplication.getInstance(), text, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(text);
            mToast.setDuration(Toast.LENGTH_SHORT);
        }
        mToast.show();
    }

    public static void show(String msg) {
        try {
            Toast toast = Toast.makeText(MyApplication.getInstance(), msg, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * context 非静态时，可传
     *
     * @param context
     * @param msg
     */
    public void show(Context context, String msg) {
        try {
            Toast toast = Toast.makeText(context, msg, Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
