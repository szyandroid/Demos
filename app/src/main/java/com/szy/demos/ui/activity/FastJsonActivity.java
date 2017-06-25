package com.szy.demos.ui.activity;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.szy.demos.R;
import com.szy.demos.mode.Group;
import com.szy.demos.mode.User;

import java.util.ArrayList;
import java.util.List;

public class FastJsonActivity extends Activity implements View.OnClickListener {
    private Button btn_1, btn_2;
    private TextView tv_content;
    private Group group;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fast_json);

        init();
        initData();
    }

    private void init() {
        btn_1 = (Button) findViewById(R.id.btn_1);
        btn_1.setOnClickListener(this);
        btn_2 = (Button) findViewById(R.id.btn_2);
        btn_2.setOnClickListener(this);

        tv_content = (TextView) findViewById(R.id.tv_content);
    }

    private void initData() {
        group = new Group();
        group.setId("1");
        group.setName("第1组");

        User user1 = new User("1_1", "张三");
        User user2 = new User("1_2", "李四");
        List<User> list = new ArrayList<>();
        list.add(user1);
        list.add(user2);
        group.setList(list);
    }

    @Override
    public void onClick(View v) {
        String ret = JSON.toJSONString(group);
        switch (v.getId()) {
            case R.id.btn_1:
                tv_content.setText(ret);
                break;
            case R.id.btn_2:
                Group group = JSON.parseObject(ret, Group.class);
                tv_content.setText(group.toString());
                break;
        }
    }
}
