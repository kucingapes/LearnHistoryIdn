package com.utsman.kucingapes.learnhistoryidn.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

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
}
