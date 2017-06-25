package com.szy.demos.ui.http;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.szy.demos.R;
import com.szy.demos.mode.Error;
import com.szy.demos.mode.Group;
import com.szy.demos.mode.User;

public class TestActivity extends BaseTestActivity implements View.OnClickListener, RequestResult {
    private Button btn_request_1, btn_request_2, btn_request_3, btn_request_4;
    private TextView tv_content;
    private Context mContext;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        init();
        setRequestResult(this);
    }

    private void init() {
        mContext = this;
        btn_request_1 = (Button) findViewById(R.id.btn_request_1);
        btn_request_1.setOnClickListener(this);
        btn_request_2 = (Button) findViewById(R.id.btn_request_2);
        btn_request_2.setOnClickListener(this);
        btn_request_3 = (Button) findViewById(R.id.btn_request_3);
        btn_request_3.setOnClickListener(this);
        btn_request_4 = (Button) findViewById(R.id.btn_request_4);
        btn_request_4.setOnClickListener(this);

        tv_content = (TextView) findViewById(R.id.tv_request_conetnt);
    }

    @Override
    public void onClick(View v) {
        showDialog();
        switch (v.getId()) {
            case R.id.btn_request_1:
                doGet(1, "http://www.jcodecraeer.com/a/anzhuokaifa/androidkaifa/2015/0106/2275.html");
                break;
            case R.id.btn_request_2:
                doGet(2, "http://www.jcodecraeer.com/a/anzhuokaifa/androidkaifa/2015/0106/2275.html");
                break;
            case R.id.btn_request_3:
                doGet(3, "http://www.jcodecraeer.com/a/anzhuokaifa/androidkaifa/2015/0106/2275.html");
                break;
            case R.id.btn_request_4:
                doGet(4, "http://123");
                break;
        }
    }

    private void showDialog() {
        String str1 = "数据请求中...";
        progressDialog = new ProgressDialog(mContext);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage(str1);
        progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {

            public void onCancel(DialogInterface arg0) {
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                finish();
            }
        });
        progressDialog.show();
    }

    @Override
    public void requestResponse(int requestCode, final Object object) {
        progressDialog.dismiss();
        if (object instanceof String) {
            String ret = (String) object;
            tv_content.setText(ret);
        } else if (object instanceof User) {
            User user = (User) object;
            tv_content.setText(user.toString());
        } else if (object instanceof Group) {
            Group group = (Group) object;
            tv_content.setText(group.toString());
        } else if (object instanceof Error) {
            Error error = (Error) object;
            tv_content.setText("错误码=" + error.code + ",\r\n错误信息=" + error.getErrorMsg());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (progressDialog != null) {
            progressDialog = null;
        }
    }
}
