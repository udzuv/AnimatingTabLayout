package com.hoge.hogeapp;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class ItemRowViewHolder extends RecyclerView.ViewHolder {
    public TextView titleView;
    public TextView detailView;

    public ItemRowViewHolder(View view) {
        super(view);
        titleView = (TextView) view.findViewById(R.id.title);
        detailView = (TextView) view.findViewById(R.id.detail);
    }
}
