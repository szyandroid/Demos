package com.szy.demos.ui.http;

import com.szy.demos.mode.User;
import com.szy.demos.utils.StringUtils;

/**
 * Created by Administrator on 2017/6/8.
 */

public class HttpRequest {
    private static HttpRequest httpRequest;
    private MyAsyncTask task;
    private RequestResult requestResult;

    private HttpRequest() {
    }

    public static HttpRequest getInstance() {
        if (httpRequest == null) {
            synchronized (HttpRequest.class) {
                if (httpRequest == null) {
                    httpRequest = new HttpRequest();
                }
            }
        }
        return httpRequest;
    }

    public void setRequestResult(RequestResult requestResult) {
        this.requestResult = requestResult;
    }

    public void doGet(final int requestCode, final String url) {
        if (requestResult != null) {
            task = new MyAsyncTask();
            task.setRequestCode(requestCode);
            task.setRequestResult(requestResult);
            task.execute(url);
        }
    }

    public void doPost(final int requestCode, final String url) {
        if (requestResult != null) {
            task = new MyAsyncTask();
            task.setRequestCode(requestCode);
            task.setRequestResult(requestResult);
            task.execute(url);
        }
    }
}
