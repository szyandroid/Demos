package com.szy.demos.ui.activity.project;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;

import com.szy.demos.R;
import com.szy.demos.mode.Error;
import com.szy.demos.mode.ResponseData;
import com.szy.demos.ui.http.HttpRequest;
import com.szy.demos.ui.http.RequestResult;

import okhttp3.Response;

public abstract class BaseActivity extends Activity implements RequestResult {
    private HttpRequest httpRequest;

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        fillStaticUI();
        httpRequest = HttpRequest.getInstance();
        httpRequest.setRequestResult(this);
    }

    //UI初始化
    protected void fillStaticUI() {

    }


    /**
     * 网络请求失败回调
     */
    protected abstract void onRequestError(int requestCode, int returnCode,
                                           String message, Object[] objs) throws Exception;

    /**
     * 网络请求成功
     *
     * @param requestCode 请求时的 requestCode
     */
    protected abstract void onRequestSucceed(int requestCode,
                                             Object response, Object[] objs) throws Exception;

    protected void doGet(int requestCode, String url) {
        httpRequest.doGet(requestCode, url);
    }

    protected void doPost(int requestCode, String url) {
        httpRequest.doPost(requestCode, url);
    }

    @Override
    public void requestResponse(int requestCode, Object object) {
        if (object != null && object instanceof ResponseData) {
            ResponseData data = (ResponseData) object;
            try {
                if (data.result instanceof Error) {
                    Error error = (Error) data.result;
                    onRequestError(requestCode, error.code, error.errorMsg, new Object[]{error});
                } else {
                    onRequestSucceed(requestCode, data.result, new Object[]{data.result});
                }
            } catch (Exception e) {
            }
        }
    }
}
