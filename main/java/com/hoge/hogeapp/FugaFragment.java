package com.hoge.hogeapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.List;


public class FugaFragment extends Fragment {
    private FugaRecycleViewAdapter adapter;
    private List<ItemRowData> itemRowList;

    public FugaFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fuga, container, false);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        recyclerView.setLayoutManager(layoutManager);

        adapter = new FugaRecycleViewAdapter(FugaFragment.this.createItemRowList());

        recyclerView.setAdapter(adapter);

        // add ItemTouchHelper
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper ( new ItemTouchHelper.SimpleCallback (
                ItemTouchHelper.UP | ItemTouchHelper.DOWN, ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(RecyclerView recyclerView, ViewHolder viewHolder, ViewHolder target) {
                final int fromPosition = viewHolder.getAdapterPosition();
                final int toPosition = target.getAdapterPosition();
                adapter.notifyItemMoved(fromPosition, toPosition);

                // save list data
                ItemRowData temp = itemRowList.get(fromPosition);
                itemRowList.remove(fromPosition);
                itemRowList.add(toPosition, temp);
                return true;
            }

            @Override
            public void onSwiped(ViewHolder viewHolder, int direction) {
                final int fromPosition = viewHolder.getAdapterPosition();
                itemRowList.remove(fromPosition);
                adapter.notifyItemRemoved(fromPosition);
            }
        });

        itemTouchHelper.attachToRecyclerView(recyclerView);
        return view;
    }

    private List<ItemRowData> createItemRowList() {

        itemRowList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            ItemRowData data = new ItemRowData(null, null);
            data.setTitle(getString(R.string.sample) + ( i + 1 ));
            data.setDetail(getString(R.string.detail_part1) + ( i + 1 ) + getString(R.string.detail_part2));

            itemRowList.add(data);
        }
        return itemRowList;
    }

}
