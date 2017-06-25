package com.szy.demos.adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.szy.demos.R;
import com.szy.demos.mode.Item;
import com.szy.demos.utils.DpUtils;

import java.util.List;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

/**
 * Created by Administrator on 2017/6/9.
 */

public class MultiAdapter extends BaseAdapter {
    private Context context;
    private List<Item> list;

    public MultiAdapter(Context context, List<Item> list) {
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
    public int getItemViewType(int position) {
        Item item = list.get(position);
        return item.type;
    }

    @Override
    public int getViewTypeCount() {
        return 3;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int type = getItemViewType(position);
        if (type == 0) {
            //文本
            convertView = getTextView(position, convertView, parent);
        } else if (type == 1) {
            //图片
            convertView = getImageView(position, convertView, parent);
        } else if (type == 2) {
            //图文
            convertView = getTextImageView(position, convertView, parent);
        }
        return convertView;
    }

    //文本
    private View getTextView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            TextView tv = new TextView(context);
            tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
            int px = DpUtils.dip2px(context, 10);
            tv.setPadding(px, px, px, px);
            holder = new ViewHolder();
            holder.tv_content = tv;
            convertView = tv;
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Item item = list.get(position);
        holder.tv_content.setText(item.content);
        return convertView;
    }

    //图片
    private View getImageView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
//            ImageView imageView = new ImageView(context);
//            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
//            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT);
//            int px = DpUtils.dip2px(context, 150);
//            params.width = px;
//            params.height = px;
//            int dis = DpUtils.dip2px(context, 10);
//            params.leftMargin = dis;
//            params.topMargin = dis;
//            params.rightMargin = dis;
//            params.bottomMargin = dis;
//            imageView.setPadding(dis, dis, dis, dis);
//            imageView.setLayoutParams(params);
//            holder = new ViewHolder();
//            holder.img = imageView;
//            convertView = imageView;
//            convertView.setTag(R.id.img_tag, holder);
            convertView = LayoutInflater.from(context).inflate(R.layout.item_img, parent, false);
            holder = new ViewHolder();
            holder.img = (ImageView) convertView.findViewById(R.id.imageView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Item item = list.get(position);
        Glide.with(context).load(item.path).into(holder.img);
        return convertView;
    }

    //图文
    private View getTextImageView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_layout, parent, false);
            holder = new ViewHolder();
            holder.tv_title = (TextView) convertView.findViewById(R.id.tv_title);
            holder.tv_time = (TextView) convertView.findViewById(R.id.tv_time);
            holder.tv_content = (TextView) convertView.findViewById(R.id.tv_item_content);
            holder.tv_path = (TextView) convertView.findViewById(R.id.tv_item_path);
            holder.img = (ImageView) convertView.findViewById(R.id.imageView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Item item = list.get(position);
        holder.tv_content.setText(item.path);
        if (position % 3 != 0) {
            holder.img.setVisibility(View.VISIBLE);
            Glide.with(context).load(item.path).into(holder.img);
        } else {
            holder.img.setVisibility(View.GONE);
        }
        return convertView;
    }

    static class ViewHolder {
        TextView tv_content, tv_path, tv_title, tv_time;
        ImageView img;
    }
}
