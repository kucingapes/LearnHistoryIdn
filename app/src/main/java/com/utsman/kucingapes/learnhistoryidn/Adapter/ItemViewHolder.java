package com.utsman.kucingapes.learnhistoryidn.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.daimajia.numberprogressbar.NumberProgressBar;
import com.utsman.kucingapes.learnhistoryidn.R;

import antonkozyriatskyi.circularprogressindicator.CircularProgressIndicator;

public class ItemViewHolder extends RecyclerView.ViewHolder {
    public View view;
    public ItemViewHolder(View itemView) {
        super(itemView);
        view = itemView;
    }

    public void setTitle(String title) {
        TextView tvTitle = view.findViewById(R.id.judul);
        tvTitle.setText(title);
    }

    public void setDesc(String desc) {
        TextView tvDesc = view.findViewById(R.id.desc);
        tvDesc.setText(desc);
    }

    public void setImg(Context context, String img) {
        ImageView imgHead = view.findViewById(R.id.imgHead);
        Glide.with(context).load(img).into(imgHead);
    }

    public void setProgress(int prog){
        CircularProgressIndicator indicator = view.findViewById(R.id.circular_progress);
        indicator.setCurrentProgress(prog);
    }

    public void setNumbProg(int prog) {
        NumberProgressBar progressBar = view.findViewById(R.id.numberprog);
        progressBar.setProgress(prog);
    }
}