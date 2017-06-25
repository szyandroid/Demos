package com.szy.demos.ui.activity.mvp.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.mapapi.search.core.PoiInfo;
import com.szy.demos.R;

import java.util.List;


/**
 * Created by Administrator on 2017/6/1.
 */

public class BaiduMapAdapter extends BaseAdapter {
    private Context context;
    private List<PoiInfo> list;
    private int checkPosition;

    public BaiduMapAdapter(Context context, List<PoiInfo> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        int ret = 0;
        if (list != null) {
            ret = list.size();
        }
        return ret;
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.adapter_baidumap_item, parent, false);
            holder = new ViewHolder();
            holder.tv_title = (TextView) convertView.findViewById(R.id.adapter_baidumap_location_name);
            holder.tv_location = (TextView) convertView.findViewById(R.id.adapter_baidumap_location_address);
            holder.img_check = (ImageView) convertView.findViewById(R.id.adapter_baidumap_location_checked);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        PoiInfo info = list.get(position);
        holder.tv_title.setText(info.name);
        holder.tv_location.setText(info.address);
        if (position == checkPosition) {
            holder.img_check.setVisibility(View.VISIBLE);
        } else {
            holder.img_check.setVisibility(View.INVISIBLE);
        }
        return convertView;
    }

    public void setSelection(int checkPosition) {
        this.checkPosition = checkPosition;
    }

    static class ViewHolder {
        TextView tv_title, tv_location;
        ImageView img_check;
    }
}
