package com.szy.demos.ui.http;

import android.os.AsyncTask;

import com.szy.demos.mode.Error;
import com.szy.demos.mode.Group;
import com.szy.demos.mode.ResponseData;
import com.szy.demos.mode.User;
import com.szy.demos.utils.HttpUtils;
import com.szy.demos.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/6/8.
 */

public class MyAsyncTask extends AsyncTask<String, Void, Object> {
    private int requestCode;
    private RequestResult requestResult;
    private HttpUtils httpUtils;

    @Override
    protected ResponseData doInBackground(String... params) {
        ResponseData response = null;
        if (params != null) {
            String url = params[0];
            try {
                Thread.sleep(1000);
                httpUtils = HttpUtils.getHttpUtils();
                response = httpUtils.get(requestCode, url);
            } catch (InterruptedException e) {
                e.printStackTrace();
                Error error = new Error();
                error.setErrorMsg(e.getMessage());
            }
        }
        return response;
    }

    @Override
    protected void onPostExecute(Object object) {
        super.onPostExecute(object);
        if (requestResult != null) {
            requestResult.requestResponse(requestCode, object);
        }
    }

    public void setRequestCode(int requestCode) {
        this.requestCode = requestCode;
    }

    public void setRequestResult(RequestResult requestResult) {
        this.requestResult = requestResult;
    }

}
