package net.cattaka.android.adaptertoolbox.adapter;

import android.support.annotation.IdRes;
import android.support.v7.widget.RecyclerView;

import net.cattaka.android.adaptertoolbox.R;
import net.cattaka.android.adaptertoolbox.adapter.listener.IForwardingListener;
import net.cattaka.android.adaptertoolbox.adapter.listener.IListenerRelay;

import java.util.List;

/**
 * Created by cattaka on 2015/07/15.
 */
public abstract class AbsCustomRecyclerAdapter<
        A extends AbsCustomRecyclerAdapter<A, VH, T, FL, LR>,
        VH extends RecyclerView.ViewHolder,
        T,
        FL extends IForwardingListener<A, VH, LR>,
        LR extends IListenerRelay<VH>
        > extends RecyclerView.Adapter<VH> {
    @IdRes
    public static int VIEW_HOLDER = R.id.viewholder;

    IForwardingListener.IProvider<A, VH> mProvider = new IForwardingListener.IProvider<A, VH>() {
        @Override
        public A getAdapter() {
            return getSelf();
        }

        @Override
        public RecyclerView getAttachedRecyclerView() {
            return mRecyclerView;
        }
    };

    protected RecyclerView mRecyclerView;

    private FL mForwardingListener;

    public FL getForwardingListener() {
        return mForwardingListener;
    }

    public AbsCustomRecyclerAdapter() {
        mForwardingListener = createForwardingListener();
        mForwardingListener.setProvider(mProvider);
    }

    public abstract A getSelf();

    public abstract FL createForwardingListener();

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        mRecyclerView = recyclerView;
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        mRecyclerView = null;
    }

    //  public void setListenerRelay(LR listenerRelay) {
    public <XLR extends IListenerRelay<? super VH>> void setListenerRelay(XLR listenerRelay) {
        mForwardingListener.setListenerRelay((LR) listenerRelay);
    }

    public abstract T getItemAt(int position);

    public abstract List<T> getItems();
}
