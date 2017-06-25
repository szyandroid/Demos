package com.szy.demos.ui.activity.project;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.ObbInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.szy.demos.R;
import com.szy.demos.constance.Constance;
import com.szy.demos.mode.Error;
import com.szy.demos.mode.Group;
import com.szy.demos.mode.ResponseData;
import com.szy.demos.mode.User;
import com.szy.demos.ui.http.BaseTestActivity;
import com.szy.demos.ui.http.RequestResult;

public class TestActivity extends BaseTestActivity implements View.OnClickListener, RequestResult {
    private Button btn_request_1, btn_request_2, btn_request_3, btn_request_4;
    private TextView tv_content;
    private Context mContext;
    private MyProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        init();
        setRequestResult(this);
        progressDialog = new MyProgressDialog(mContext);
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
        progressDialog.showDialog();
        switch (v.getId()) {
            case R.id.btn_request_1:
                doGet(Constance.REQ_STRING, "https://www.baidu.com/");
                break;
            case R.id.btn_request_2:
                doGet(Constance.REQ_USER, "https://www.baidu.com/");
                break;
            case R.id.btn_request_3:
                doGet(Constance.REQ_GROUP, "https://www.baidu.com/");
                break;
            case R.id.btn_request_4:
                doGet(Constance.REQ_OBJECT, "http://123");
                break;
        }
    }

    @Override
    public void requestResponse(int requestCode, final Object object) {
        progressDialog.dismiss();
        if (object instanceof ResponseData) {
            ResponseData data = (ResponseData) object;
            int responseCode = data.code;
            Object result = data.result;
            if (result instanceof String) {
                String ret = (String) result;
                tv_content.setText(ret);
            } else if (result instanceof User) {
                User user = (User) result;
                tv_content.setText(user.toString());
            } else if (result instanceof Group) {
                Group group = (Group) result;
                tv_content.setText(group.toString());
            } else if (result instanceof Error) {
                Error error = (Error) result;
                tv_content.setText("错误码=" + error.code + ",\r\n错误信息=" + error.getErrorMsg());
            }
        }
    }

}
