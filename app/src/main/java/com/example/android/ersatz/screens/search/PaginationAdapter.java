package com.example.android.ersatz.screens.search;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

/**
 * Created by Denis on 19.09.2017.
 */

public class PaginationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public boolean isEmpty() {
        return getItemCount() == 0;
    }
}