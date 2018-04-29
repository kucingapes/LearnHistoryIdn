package com.utsman.kucingapes.learnhistoryidn.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.utsman.kucingapes.learnhistoryidn.R;

public class ItemCategoryHolder extends RecyclerView.ViewHolder {
    public View view;

    public ItemCategoryHolder(View itemView) {
        super(itemView);
        view = itemView;
    }

    public void setTitleCat(String titleCat) {
        TextView tvName = view.findViewById(R.id.name_cat);
        tvName.setText(titleCat);
    }

    public void setImgCat(Context context, String imgCat) {
        ImageView imageView = view.findViewById(R.id.img_cat);
        Glide.with(context).load(imgCat).into(imageView);
    }
}
