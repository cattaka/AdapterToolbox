package net.cattaka.android.adaptertoolbox.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

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

    private ListenerRelay<A, VH> mListenerRelay;

    public CustomRecyclerAdapter() {
        super();
    }

    public void setListenerRelay(@Nullable ListenerRelay<A, VH> listenerRelay) {
        mListenerRelay = listenerRelay;
    }

    @NonNull
    @Override
    public ForwardingListener<A, VH> createForwardingListener() {
        ForwardingListener<A, VH> forwardingListener = new ForwardingListener<>();
        if (mListenerRelay != null) {
            forwardingListener.setListenerRelay(mListenerRelay);
        }
        return forwardingListener;
    }
}
