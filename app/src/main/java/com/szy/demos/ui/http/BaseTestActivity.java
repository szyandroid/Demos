package com.szy.demos.ui.http;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.szy.demos.R;

public class BaseTestActivity extends Activity {
    private RequestResult requestResult;
    private HttpRequest request;

    public void setRequestResult(RequestResult requestResult) {
        this.requestResult = requestResult;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_test);
        request = HttpRequest.getInstance();
    }

    public void doGet(int requestCode, String url) {
        if (requestResult != null) {
            request.setRequestResult(requestResult);
        }
        request.doGet(requestCode, url);
    }

    public void doPost(int requestCode, String url) {
        if (requestResult != null) {
            request.setRequestResult(requestResult);
        }
        request.doPost(requestCode, url);
    }
}
