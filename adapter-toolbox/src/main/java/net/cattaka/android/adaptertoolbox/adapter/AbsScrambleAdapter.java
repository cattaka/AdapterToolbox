package net.cattaka.android.adaptertoolbox.adapter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.ViewGroup;

import net.cattaka.android.adaptertoolbox.adapter.listener.IForwardingListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cattaka on 16/05/10.
 */
public abstract class AbsScrambleAdapter<
        A extends AbsScrambleAdapter<A, SA, VH, FL, T>,
        SA extends AbsScrambleAdapter<?, SA, VH, ?, ?>,
        VH extends RecyclerView.ViewHolder,
        FL extends IForwardingListener<SA, VH>,
        T
        > extends RecyclerView.Adapter<VH> implements IHasItemAdapter<VH, T> {
    IForwardingListener.IProvider<SA, VH> mProvider = new IForwardingListener.IProvider<SA, VH>() {
        @NonNull
        @Override
        public SA getAdapter() {
            return getSelf();
        }

        @Nullable
        @Override
        public RecyclerView getAttachedRecyclerView() {
            return mRecyclerView;
        }
    };

    RecyclerView mRecyclerView;

    List<IViewHolderFactory<SA, VH, FL, ? extends VH>> mViewHolderFactory;
    SparseArray<FL> mForwardingListeners;

    public AbsScrambleAdapter(
            @NonNull List<? extends IViewHolderFactory<SA, VH, FL, ?>> viewHolderFactories
    ) {
        mViewHolderFactory = new ArrayList<>();

        mViewHolderFactory.addAll(viewHolderFactories);

        @SuppressWarnings("unchecked")
        NullViewHolderFactory<SA, VH, FL> nvhf = new NullViewHolderFactory(this);
        mViewHolderFactory.add(nvhf);
        mForwardingListeners = new SparseArray<>();
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        IViewHolderFactory<SA, VH, FL, ?> viewHolderFactory = mViewHolderFactory.get(viewType);
        FL forwardingListener;
        if (mForwardingListeners.indexOfKey(viewType) < 0) {
            forwardingListener = createForwardingListener(viewHolderFactory);
            if (forwardingListener != null) {
                forwardingListener.setProvider(mProvider);
            }
            mForwardingListeners.put(viewType, forwardingListener);
        } else {
            forwardingListener = mForwardingListeners.get(viewType);
        }
        return viewHolderFactory.onCreateViewHolder(getSelf(), parent, forwardingListener);
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {
        int viewType = getItemViewType(position);
        IViewHolderFactory<SA, VH, FL, ?> viewHolderFactory = mViewHolderFactory.get(viewType);
        onBindViewHolderInner(viewHolderFactory, holder, position);
    }

    @Override
    public void onViewRecycled(VH holder) {
        int viewType = getItemViewType(holder.getAdapterPosition());
        IViewHolderFactory<SA, VH, FL, ?> viewHolderFactory = mViewHolderFactory.get(viewType);
        viewHolderFactory.onViewRecycled(getSelf(), holder);
    }

    @Override
    public void onViewDetachedFromWindow(VH holder) {
        int viewType = getItemViewType(holder.getAdapterPosition());
        IViewHolderFactory<SA, VH, FL, ?> viewHolderFactory = mViewHolderFactory.get(viewType);
        viewHolderFactory.onViewDetachedFromWindow(getSelf(), holder);
    }

    @Override
    public void onViewAttachedToWindow(VH holder) {
        int viewType = getItemViewType(holder.getAdapterPosition());
        IViewHolderFactory<SA, VH, FL, ?> viewHolderFactory = mViewHolderFactory.get(viewType);
        viewHolderFactory.onViewAttachedToWindow(getSelf(), holder);
    }

    @Override
    public boolean onFailedToRecycleView(VH holder) {
        int viewType = getItemViewType(holder.getAdapterPosition());
        IViewHolderFactory<SA, VH, FL, ?> viewHolderFactory = mViewHolderFactory.get(viewType);
        return viewHolderFactory.onFailedToRecycleView(getSelf(), holder);
    }

    @SuppressWarnings("unchecked")
    private <EVH extends VH> void onBindViewHolderInner(
            IViewHolderFactory<SA, VH, FL, EVH> viewHolderFactory,
            VH holder,
            int position
    ) {
        Object object = getItemAt(position);
        viewHolderFactory.onBindViewHolder(getSelf(), (EVH) holder, position, object);
    }

    @Override
    public int getItemViewType(int position) {
        Object object = getItemAt(position);
        int index = 0;
        for (IViewHolderFactory<SA, VH, FL, ? extends VH> viewHolderFactory : mViewHolderFactory) {
            if (viewHolderFactory.isAssignable(getSelf(), object)) {
                return index;
            }
            index++;
        }
        return mViewHolderFactory.size() - 1;
    }

    public abstract T getItemAt(int position);

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

    @SuppressWarnings("unchecked")
    @NonNull
    public SA getSelf() {
        return (SA) this;
    }

    @Nullable
    public abstract FL createForwardingListener(@NonNull IViewHolderFactory<SA, VH, FL, ?> viewHolderFactory);

    @NonNull
    public abstract VH createNullViewHolder();

    public interface IViewHolderFactory<
            SA extends AbsScrambleAdapter<?, SA, VH, ?, ?>,
            VH extends RecyclerView.ViewHolder,
            FL extends IForwardingListener<SA, VH>,
            EVH extends VH
            > {
        @NonNull
        EVH onCreateViewHolder(@NonNull SA adapter, @NonNull ViewGroup parent, @NonNull FL forwardingListener);

        void onBindViewHolder(@NonNull SA adapter, @NonNull EVH holder, int position, @Nullable Object object);

        boolean isAssignable(@NonNull SA adapter, @Nullable Object object);

        @Nullable
        FL createForwardingListener();

        boolean onFailedToRecycleView(@NonNull SA adapter, @NonNull VH holder);

        void onViewAttachedToWindow(@NonNull SA adapter, @NonNull VH holder);

        void onViewDetachedFromWindow(@NonNull SA adapter, @NonNull VH holder);

        void onViewRecycled(@NonNull SA adapter, @NonNull VH holder);
    }

    public static class NullViewHolderFactory<
            SA extends AbsScrambleAdapter<?, SA, VH, ?, ?>,
            VH extends RecyclerView.ViewHolder,
            FL extends IForwardingListener<SA, VH>
            > implements IViewHolderFactory<SA, VH, FL, VH> {
        SA mAdapter;

        public NullViewHolderFactory(@NonNull SA adapter) {
            this.mAdapter = adapter;
        }

        @NonNull
        @Override
        public VH onCreateViewHolder(@NonNull SA adapter, @NonNull ViewGroup parent, @NonNull FL forwardingListener) {
            return mAdapter.createNullViewHolder();
        }

        @Override
        public void onBindViewHolder(@NonNull SA adapter, @NonNull VH holder, int position, @Nullable Object object) {
            // no-op
        }

        @Override
        public boolean isAssignable(@NonNull SA adapter, @Nullable Object object) {
            return true;
        }

        @Nullable
        @Override
        public FL createForwardingListener() {
            return null;
        }

        @Override
        public void onViewRecycled(@NonNull SA adapter, @NonNull VH holder) {
            // no-op
        }

        @Override
        public void onViewDetachedFromWindow(@NonNull SA adapter, @NonNull VH holder) {
            // no-op
        }

        @Override
        public void onViewAttachedToWindow(@NonNull SA adapter, @NonNull VH holder) {
            // no-op
        }

        @Override
        public boolean onFailedToRecycleView(@NonNull SA adapter, @NonNull VH holder) {
            // no-op
            return false;
        }
    }
}
