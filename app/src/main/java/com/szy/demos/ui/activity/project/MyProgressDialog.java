package com.szy.demos.ui.activity.project;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;

/**
 * Created by Administrator on 2017/6/16.
 */

public class MyProgressDialog {
    private Context context;
    private ProgressDialog progressDialog;

    public MyProgressDialog(Context context) {
        this.context = context;
    }

    public void showDialog() {
        String str1 = "数据请求中...";
        progressDialog = new ProgressDialog(context);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage(str1);
        progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            public void onCancel(DialogInterface arg0) {
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
            }
        });
        progressDialog.show();
    }

    public void dismiss() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }
}
