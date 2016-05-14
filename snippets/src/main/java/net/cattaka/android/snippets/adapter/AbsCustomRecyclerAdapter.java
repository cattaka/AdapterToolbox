package net.cattaka.android.snippets.adapter;

import android.support.annotation.IdRes;
import android.support.v7.widget.RecyclerView;

import net.cattaka.android.snippets.R;

import java.util.List;

/**
 * Created by cattaka on 2015/07/15.
 */
public abstract class AbsCustomRecyclerAdapter<
        A extends AbsCustomRecyclerAdapter<A, VH, FL, T>,
        VH extends RecyclerView.ViewHolder,
        FL extends IForwardingListener<A, ? super VH>,
        T
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

    private ForwardingListener<A, VH> mForwardingListener;

    public ForwardingListener<A, VH> getForwardingListener() {
        return mForwardingListener;
    }

    public AbsCustomRecyclerAdapter() {
        mForwardingListener = new ForwardingListener<>();
        mForwardingListener.setProvider(mProvider);
    }

    public abstract A getSelf();

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

    public void setOnItemClickListener(ForwardingListener.OnItemClickListener listener) {
        mForwardingListener.setOnItemClickListener(listener);
    }

    public void setOnItemLongClickListener(ForwardingListener.OnItemLongClickListener longListener) {
        mForwardingListener.setOnItemLongClickListener(longListener);
    }

    public abstract T getItemAt(int position);

    public abstract List<T> getItems();
}
