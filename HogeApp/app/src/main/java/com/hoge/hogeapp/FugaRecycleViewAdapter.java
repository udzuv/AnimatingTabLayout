package com.hoge.hogeapp;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;


public class FugaRecycleViewAdapter extends RecyclerView.Adapter<ItemRowViewHolder> {
    private List<ItemRowData> list;

    public FugaRecycleViewAdapter(List<ItemRowData> list) {
        this.list = list;
    }

    @Override
    public ItemRowViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row, parent,false);
        ItemRowViewHolder viewHolder = new ItemRowViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ItemRowViewHolder holder, int position) {
        holder.titleView.setText(list.get(position).getTitle());
        holder.detailView.setText(list.get(position).getDetail());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}