package com.szy.demos.ui.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.szy.demos.R;

public class VolleyActivity extends Activity implements View.OnClickListener {
    private Button btn;
    private TextView tv_ret;
    private RequestQueue mQueue;
    private Context mContext;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volley);
        init();
    }

    private void init() {
        mContext = this;
        mQueue = Volley.newRequestQueue(mContext);
        btn = (Button) findViewById(R.id.btn_get);
        btn.setOnClickListener(this);
        tv_ret = (TextView) findViewById(R.id.tv_ret);

        progressDialog = new ProgressDialog(mContext);
        progressDialog.setMessage("数据加载中...");
        progressDialog.show();
        get();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_get:
                progressDialog.show();
                get();
                break;
        }
    }

    private void get() {
        StringRequest request = new StringRequest("http://www.baidu.com", new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                tv_ret.setText("数据请求成功：" + s.toString().trim());
                progressDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                tv_ret.setText("数据请求失败：" + volleyError.toString());
                progressDialog.dismiss();
            }
        });
        mQueue.add(request);
    }
}
