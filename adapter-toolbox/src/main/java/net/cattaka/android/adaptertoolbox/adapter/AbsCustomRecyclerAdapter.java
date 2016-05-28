package net.cattaka.android.adaptertoolbox.adapter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;

import net.cattaka.android.adaptertoolbox.adapter.listener.IForwardingListener;

import java.util.List;

/**
 * Created by cattaka on 2015/07/15.
 */
public abstract class AbsCustomRecyclerAdapter<
        A extends AbsCustomRecyclerAdapter<A, VH, T, FL>,
        VH extends RecyclerView.ViewHolder,
        T,
        FL extends IForwardingListener<A, VH>
        > extends RecyclerView.Adapter<VH> implements IHasItemAdapter<VH, T> {

    IForwardingListener.IProvider<A, VH> mProvider = new IForwardingListener.IProvider<A, VH>() {
        @NonNull
        @Override
        public A getAdapter() {
            return getSelf();
        }

        @Nullable
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

    @SuppressWarnings("unchecked")
    @NonNull
    public A getSelf() {
        return (A) this;
    }

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

    public int getViewTypeCount() {
        return 1;
    }

    public abstract T getItemAt(int position);

    public abstract List<T> getItems();
}
