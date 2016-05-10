package net.cattaka.android.snippets.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by cattaka on 16/05/11.
 */
public interface OnItemLongClickListener<A extends RecyclerView.Adapter<? extends VH>, VH extends RecyclerView.ViewHolder> {
    boolean onItemLongClick(RecyclerView parent, A adapter, int position, int id, View view, VH vh);
}
