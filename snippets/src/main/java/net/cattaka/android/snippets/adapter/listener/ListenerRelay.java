package net.cattaka.android.snippets.adapter.listener;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by cattaka on 16/05/14.
 */
public class ListenerRelay<A extends RecyclerView.Adapter<? super VH>, VH extends RecyclerView.ViewHolder> implements
        ForwardingListener.OnItemClickListener<A, VH>,
        ForwardingListener.OnItemLongClickListener<A, VH> {
    @Override
    public void onItemClick(RecyclerView parent, A adapter, int position, int id, VH vh) {

    }

    @Override
    public boolean onItemLongClick(RecyclerView parent, A adapter, int position, int id, View view, VH vh) {
        return false;
    }
}
