package com.szy.demos.mode;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/5/27.
 */

public class EventData implements Serializable{
    private String data;

    public EventData(String data) {
        this.data = data;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
