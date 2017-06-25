package com.szy.demos.utils;

import com.szy.demos.constance.Constance;
import com.szy.demos.mode.Error;
import com.szy.demos.mode.Group;
import com.szy.demos.mode.ResponseData;
import com.szy.demos.mode.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.TimeUnit;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/6/9.
 */

public class HttpUtils {
    private static HttpUtils httpUtils;
    private OkHttpClient client;

    private HttpUtils() {
    }

    public static HttpUtils getHttpUtils() {
        if (httpUtils == null) {
            synchronized (HttpUtils.class) {
                if (httpUtils == null) {
                    httpUtils = new HttpUtils();
                }
            }
        }
        return httpUtils;
    }

    public ResponseData get(int requestCode, String url) {
        ResponseData data = new ResponseData();
        data.code = requestCode;
        client = getOkHttpClient();

        if (!StringUtils.isEmpty(url)) {
            try {
                Request request = new Request.Builder().url(url).build();
                Response response = client.newCall(request).execute();
                if (response.isSuccessful()) {
                    String result = response.body().string();
//                    JSONObject json = new JSONObject(result);
//                    int code = json.getInt("code");
//                    data.code = code;
                    if (requestCode == Constance.REQ_USER) {
                        User user = new User();
                        user.getData(user, "1232");
                        data.result = user;
                    } else if (requestCode == Constance.REQ_GROUP) {
                        Group group = new Group();
                        group.getData(group, "1213");
                        data.result = group;
                    } else {
                        Error error = new Error();
                        error.code = -1;
                        error.setErrorMsg("数据请求失败！");
                        data.result = error;
                    }
                } else {
                    Error error = new Error();
                    error.setErrorMsg(response.body().string());
                    error.code = response.code();
                    data.result = error;
                }
            } catch (Exception e) {
                e.printStackTrace();
                Error error = new Error();
                error.setErrorMsg(e.getMessage());
                error.code = -2;
                data.result = error;
            }
        } else {
            Error error = new Error();
            error.setErrorMsg("网络请求路径有误！");
            error.code = -3;
            data.result = error;
        }
        return data;
    }

    public ResponseData post(int requestCode, int type, String url) {
        ResponseData data = new ResponseData();
        data.code = requestCode;
        client = getOkHttpClient();
        try {
            RequestBody body = getRequestBody(type);
            Request request = new Request.Builder().url(url).post(body).build();
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                data.result = response.body().string();
            } else {
                Error error = new Error();
                error.setErrorMsg(response.body().string());
                data.result = error;
            }
        } catch (Exception e) {
            e.printStackTrace();
            Error error = new Error();
            error.setErrorMsg(e.getMessage());
            data.result = error;
        }
        return data;
    }

    //获取OKHttpClient对象
    private OkHttpClient getOkHttpClient() {
        if (client == null) {
            synchronized (HttpUtils.class) {
                if (client == null) {
                    client = new OkHttpClient.Builder()
                            .connectTimeout(20, TimeUnit.SECONDS)//设置连接超时时间
                            .readTimeout(20, TimeUnit.SECONDS)//设置读取超时时间
                            .writeTimeout(20, TimeUnit.SECONDS)//设置写的超时时间
                            .build();
                }
            }
        }
        return client;
    }

    public RequestBody getRequestBody(int type) {
        RequestBody body = null;
        if (type == Constance.REQUEST_JSON) {
            MediaType JSON = MediaType.parse("application/json; charset=utf-8");
            body = RequestBody.create(JSON, "json Strig");
        } else if (type == Constance.REQUEST_STRING) {
            MediaType TEXT = MediaType.parse("text/x-markdown; charset=utf-8");
            String str = "12213";
            body = RequestBody.create(TEXT, str);
        } else if (type == Constance.REQUEST_MAP) {
            body = new FormBody.Builder()
                    .add("platform", "android")
                    .add("name", "szy")
                    .add("password", "123")
                    .build();

        }
        return body;
    }
}
/**
 * OkHttp官网：http://square.github.io/okhttp/
 * GET:请求
 * OkHttpClient client = new OkHttpClient();
 * <p>
 * String run(String url) throws IOException {
 * Request request = new Request.Builder()
 * .url(url)
 * .build();
 * <p>
 * Response response = client.newCall(request).execute();
 * return response.body().string();
 * }
 * <p>
 * POST：请求
 * public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
 * OkHttpClient client = new OkHttpClient();
 * <p>
 * String post(String url, String json) throws IOException {
 * RequestBody body = RequestBody.create(JSON, json);
 * Request request = new Request.Builder()
 * .url(url)
 * .post(body)
 * .build();
 * Response response = client.newCall(request).execute();
 * return response.body().string();
 * }
 */
























