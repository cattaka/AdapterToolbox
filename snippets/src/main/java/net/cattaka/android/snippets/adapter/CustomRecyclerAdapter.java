package net.cattaka.android.snippets.adapter;

import android.support.v7.widget.RecyclerView;

import java.util.List;

/**
 * Created by cattaka on 2015/07/15.
 */
public abstract class CustomRecyclerAdapter<VH extends RecyclerView.ViewHolder, T> extends AbsCustomRecyclerAdapter<
        CustomRecyclerAdapter<VH, T>,
        VH, ForwardingListener<CustomRecyclerAdapter<VH, T>, VH>, T
        > {

    @Override
    public CustomRecyclerAdapter<VH, T> getSelf() {
        return this;
    }

    public abstract T getItemAt(int position);

    public abstract List<T> getItems();

    public interface OnItemClickListener<VH extends RecyclerView.ViewHolder, T> extends ForwardingListener.OnItemClickListener<CustomRecyclerAdapter<VH, T>, VH> {

    }

    public interface OnItemLongClickListener<VH extends RecyclerView.ViewHolder, T> extends ForwardingListener.OnItemLongClickListener<CustomRecyclerAdapter<VH, T>, VH> {

    }
}
