package net.cattaka.android.snippets.adapter;

import android.support.v7.widget.RecyclerView;

import net.cattaka.android.snippets.adapter.listener.ForwardingListener;
import net.cattaka.android.snippets.adapter.listener.ListenerRelay;

/**
 * Created by cattaka on 2015/07/15.
 */
public abstract class CustomRecyclerAdapter<
        VH extends RecyclerView.ViewHolder,
        T
        > extends AbsCustomRecyclerAdapter<CustomRecyclerAdapter<VH, T>, VH, T, ForwardingListener<CustomRecyclerAdapter<VH, T>, VH>, ListenerRelay<CustomRecyclerAdapter<VH, T>, VH>> {

    @Override
    public CustomRecyclerAdapter<VH, T> getSelf() {
        return this;
    }

    @Override
    public ForwardingListener<CustomRecyclerAdapter<VH, T>, VH> createForwardingListener() {
        return new ForwardingListener<>();
    }
}

