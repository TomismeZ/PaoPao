package com.zdk.paopao.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.zdk.paopao.R;

import java.util.List;

/**
 * Created by Administrator on 2017/12/27 0027.
 */

public class GridViewAdapter extends BaseAdapter {
    private Context context;
    private List<String> imageUrls;

    public GridViewAdapter(Context context, List<String> imageUrls) {
        this.context = context;
        this.imageUrls = imageUrls;
    }

    @Override
    public int getCount() {
        return imageUrls.size();
    }

    @Override
    public Object getItem(int i) {
        return imageUrls.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder=null;
        if(view == null){

            view=View.inflate(context, R.layout.item_girdview_image,null);
            holder=new ViewHolder();
            holder.imageView=view.findViewById(R.id.image_url);

            view.setTag(holder);
            // 给ImageView设置资源
//            imageView = new ImageView(context);
//            // 设置布局 图片120×120显示
//            imageView.setLayoutParams(new GridView.LayoutParams(85, 85));
//            // 设置显示比例类型
//            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        }else{
//            imageView = (ImageView) view;
            holder=(ViewHolder) view.getTag();
        }

        Glide.with(context).load(imageUrls.get(i)).into(holder.imageView);
        return view;
    }

    private class ViewHolder{
        ImageView imageView;
    }
}
