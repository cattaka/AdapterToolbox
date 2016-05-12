package net.cattaka.android.snippets.adapter;

import android.support.v7.widget.RecyclerView;

/**
 * Created by takao on 2016/05/12.
 */
public interface IForwardingListener<A extends RecyclerView.Adapter<? super VH>, VH extends RecyclerView.ViewHolder> {
    void setProvider(IProvider<A, VH> provider);

    interface IProvider<A extends RecyclerView.Adapter<? super VH>, VH extends RecyclerView.ViewHolder> {
        A getAdapter();

        RecyclerView getAttachedRecyclerView();
    }
}
