package com.szy.demos.ui.http;

import com.szy.demos.utils.StringUtils;

/**
 * Created by Administrator on 2017/6/8.
 */

public class HttpManger {
    private static HttpManger manger;
    private RequestResult requestResult;
    private MyAsyncTask task;

    public void setRequestResult(RequestResult requestResult) {
        this.requestResult = requestResult;
    }

    private HttpManger() {
    }

    public static HttpManger getManger() {
        if (manger == null) {
            synchronized (HttpManger.class) {
                if (manger == null) {
                    manger = new HttpManger();
                }
            }
        }
        return manger;
    }

    public void doGet(int requestCode, String url) {
        task = new MyAsyncTask();
        task.setRequestResult(requestResult);
        task.setRequestCode(requestCode);
        task.execute(url);
    }

    public void doPost(int requestCode, String url) {
        task = new MyAsyncTask();
        task.setRequestResult(requestResult);
        task.setRequestCode(requestCode);
        task.execute(url);
    }
}
