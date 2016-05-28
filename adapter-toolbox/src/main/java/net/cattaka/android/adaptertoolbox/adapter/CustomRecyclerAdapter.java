package net.cattaka.android.adaptertoolbox.adapter;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;

import net.cattaka.android.adaptertoolbox.adapter.listener.ForwardingListener;
import net.cattaka.android.adaptertoolbox.adapter.listener.ListenerRelay;

/**
 * Created by cattaka on 2015/07/15.
 */
public abstract class CustomRecyclerAdapter<
        A extends CustomRecyclerAdapter<A, VH, T>,
        VH extends RecyclerView.ViewHolder,
        T
        > extends AbsCustomRecyclerAdapter<
        A,
        VH,
        T,
        ForwardingListener<A, VH>
        > {

    @Override
    public ForwardingListener<A, VH> createForwardingListener() {
        return new ForwardingListener<>();
    }

    public void setListenerRelay(@Nullable ListenerRelay<A, VH> listenerRelay) {
        getForwardingListener().setListenerRelay(listenerRelay);
    }
}
