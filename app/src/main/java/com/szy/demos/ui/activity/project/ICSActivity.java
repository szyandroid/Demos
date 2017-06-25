package com.szy.demos.ui.activity.project;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.szy.demos.R;
import com.szy.demos.constance.Constance;
import com.szy.demos.mode.Group;
import com.szy.demos.mode.User;

public class ICSActivity extends BaseActivity implements View.OnClickListener {
    private Button btn_request_01, btn_request_02;
    private TextView tv_content;
    private MyProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ics);
        progressDialog = new MyProgressDialog(this);
    }

    @Override
    public void fillStaticUI() {
        btn_request_01 = (Button) findViewById(R.id.btn_request_01);
        btn_request_01.setOnClickListener(this);
        btn_request_02 = (Button) findViewById(R.id.btn_request_02);
        btn_request_02.setOnClickListener(this);
        tv_content = (TextView) findViewById(R.id.tv_content);
    }

    @Override
    protected void onRequestError(int requestCode, int returnCode, String message, Object[] objs) throws Exception {
        progressDialog.dismiss();
        tv_content.setText("错误码=" + returnCode + ",\r\n错误信息=" + message);
    }

    @Override
    protected void onRequestSucceed(int requestCode, Object result, Object[] objs) throws Exception {
        progressDialog.dismiss();
        if (result instanceof String) {
            String ret = (String) result;
            tv_content.setText(ret);
        } else if (result instanceof User) {
            User user = (User) result;
            tv_content.setText(user.toString());
        } else if (result instanceof Group) {
            Group group = (Group) result;
            tv_content.setText(group.toString());
        }
    }

    @Override
    public void onClick(View v) {
        progressDialog.showDialog();
        switch (v.getId()) {
            case R.id.btn_request_01:
                doGet(Constance.REQ_STRING, "https://www.baidu.com/");
                break;
            case R.id.btn_request_02:
                doGet(Constance.REQ_USER, "https://www.baidu.com/");
                break;
        }
    }
}
