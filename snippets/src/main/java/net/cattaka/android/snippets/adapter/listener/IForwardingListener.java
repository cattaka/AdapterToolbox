package net.cattaka.android.snippets.adapter.listener;

import android.support.v7.widget.RecyclerView;

/**
 * Created by takao on 2016/05/12.
 */
public interface IForwardingListener<A extends RecyclerView.Adapter<? extends VH>, VH extends RecyclerView.ViewHolder, LR extends IListenerRelay<? super VH>> {
    void setProvider(IProvider<A, VH> provider);

    void setListenerRelay(LR listenerRelay);

    interface IProvider<A extends RecyclerView.Adapter<? extends VH>, VH extends RecyclerView.ViewHolder> {
        A getAdapter();

        RecyclerView getAttachedRecyclerView();
    }
}
