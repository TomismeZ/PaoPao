package com.zdk.paopao.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.bumptech.glide.Glide;
import com.zdk.paopao.R;
import com.zdk.paopao.bean.ImageInfo;


import java.util.List;

/**
 * Created by Administrator on 2017/12/26.
 */

public class ImageAdapter extends BaseAdapter {
    List<ImageInfo> imageDataList;
    Context context;

    public ImageAdapter(List<ImageInfo> imageDataList, Context context) {
        this.imageDataList = imageDataList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return imageDataList.size();
    }

    @Override
    public Object getItem(int i) {
        return imageDataList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (view == null) {
            view = View.inflate(context, R.layout.item_image, null);
            holder = new ViewHolder();
            holder.userAvatar = view.findViewById(R.id.user_avatar);
            holder.username = view.findViewById(R.id.username);
            holder.publishTime = view.findViewById(R.id.user_publishtime);
            holder.content = view.findViewById(R.id.content);
            holder.image_content = view.findViewById(R.id.image_content);
            holder.gridView = view.findViewById(R.id.gview);

            holder.comment = view.findViewById(R.id.comment);
            holder.good = view.findViewById(R.id.good);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        ImageInfo imageInfo = imageDataList.get(i);
        //加载图片
        Glide.with(context).load(imageInfo.getAvatar()).into(holder.userAvatar);
        holder.username.setText(imageInfo.getUsername());
        holder.content.setText(imageInfo.getContent());
        holder.publishTime.setText(imageInfo.getPublishTime());
        //添加元素给gridview
        holder.gridView.setAdapter(new GridViewAdapter(context, imageInfo.getImageUrls()));

       Glide.with(context).load(imageInfo.getImageUrl()).into(holder.image_content);
//        if (imageInfo.getAgree() > 0) {
//            holder.good.setText("赞(" + imageInfo.getAgree() + ")");
//        }


        if (imageInfo.getComment() > 0) {
            holder.comment.setText("评论(" + imageInfo.getComment() + ")");
        }

        return view;
    }

    private class ViewHolder {
        ImageView userAvatar;
        TextView username;
        TextView publishTime;
        TextView content;
        ImageView image_content;
        GridView gridView;
        TextView comment;
        TextView good;

    }
}
