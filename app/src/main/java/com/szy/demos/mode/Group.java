package com.szy.demos.mode;

import com.szy.demos.ui.http.Parserable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/5/27.
 */

public class Group implements Serializable, Parserable {
    private String id;
    private String name;
    private List<User> list;

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

    public List<User> getList() {
        return list;
    }

    public void setList(List<User> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "Group{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", list=" + list +
                '}';
    }

    @Override
    public Object getData(Object object, String json) {
        Group group = null;
        if (object instanceof Group) {
            group = (Group) object;
            group.setId("11");
            group.setName("一组");
            User user = new User("1231", "szy_1");
            User user2 = new User("1232", "szy_2");
            List<User> list = new ArrayList<>();
            list.add(user);
            list.add(user2);
            group.setList(list);
        }
        return group;
    }
}
