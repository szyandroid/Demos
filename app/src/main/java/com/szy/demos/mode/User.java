package com.szy.demos.mode;

import com.szy.demos.ui.http.Parserable;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/5/27.
 */

public class User implements Serializable, Parserable {
    private String id;
    private String name;

    public User() {
    }

    public User(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", id='" + id + '\'' +
                '}';
    }

    @Override
    public Object getData(Object object, String json) {
        User user = null;
        if (object instanceof User) {
            user = (User) object;
            user.setName("szy");
            user.setId("123");
        }
        return user;
    }
}
