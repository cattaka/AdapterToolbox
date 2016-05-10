package net.cattaka.android.snippets.adapter;

import android.support.v7.widget.RecyclerView;

/**
 * Created by cattaka on 16/05/11.
 */
public interface OnItemClickListener<A extends RecyclerView.Adapter<? extends VH>, VH extends RecyclerView.ViewHolder> {
    void onItemClick(RecyclerView parent, A adapter, int position, int id, VH vh);
}
