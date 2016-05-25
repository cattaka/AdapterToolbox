package net.cattaka.android.adaptertoolbox.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import net.cattaka.android.adaptertoolbox.adapter.listener.IForwardingListener;
import net.cattaka.android.adaptertoolbox.adapter.listener.IListenerRelay;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cattaka on 16/05/10.
 */
public abstract class AbsScrambleAdapter<
        A extends AbsScrambleAdapter<A, SA, VH, FL, LR, T>,
        SA extends AbsScrambleAdapter<?, SA, VH, ?, ?, ?>,
        VH extends RecyclerView.ViewHolder,
        FL extends IForwardingListener<SA, VH, LR>,
        LR extends IListenerRelay<? super VH>,
        T
        > extends RecyclerView.Adapter<VH> {
    IForwardingListener.IProvider<SA, VH> mProvider = new IForwardingListener.IProvider<SA, VH>() {
        @Override
        public SA getAdapter() {
            return getSelf();
        }

        @Override
        public RecyclerView getAttachedRecyclerView() {
            return mRecyclerView;
        }
    };

    RecyclerView mRecyclerView;

    List<IViewHolderFactory<SA, VH, FL, ? extends VH, LR>> mViewHolderFactory;
    List<FL> mForwardingListeners;
    LR mListenerRelay;

    public AbsScrambleAdapter(
            LR listenerRelay,
            List<? extends IViewHolderFactory<SA, VH, FL, ?, LR>> viewHolderFactories
    ) {
        mListenerRelay = listenerRelay;
        mViewHolderFactory = new ArrayList<>();

        mViewHolderFactory.addAll(viewHolderFactories);

        @SuppressWarnings("unchecked")
        NullViewHolderFactory<SA, VH, FL, ?, LR> nvhf = new NullViewHolderFactory(this);
        mViewHolderFactory.add(nvhf);
        mForwardingListeners = new ArrayList<>();
        for (IViewHolderFactory<SA, VH, FL, ?, LR> viewHolderFactory : mViewHolderFactory) {
            FL forwardingListener = viewHolderFactory.createForwardingListener();
            if (forwardingListener != null) {
                forwardingListener.setListenerRelay(listenerRelay);
                forwardingListener.setProvider(mProvider);
            }
            mForwardingListeners.add(forwardingListener);
        }
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        IViewHolderFactory<SA, VH, FL, ?, LR> viewHolderFactory = mViewHolderFactory.get(viewType);
        FL forwardingListener = mForwardingListeners.get(viewType);
        return viewHolderFactory.onCreateViewHolder(getSelf(), parent, forwardingListener);
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {
        int viewType = getItemViewType(position);
        IViewHolderFactory<SA, VH, FL, ?, LR> viewHolderFactory = mViewHolderFactory.get(viewType);
        onBindViewHolderInner(viewHolderFactory, holder, position);
    }

    @Override
    public void onViewRecycled(VH holder) {
        int viewType = getItemViewType(holder.getAdapterPosition());
        IViewHolderFactory<SA, VH, FL, ?, LR> viewHolderFactory = mViewHolderFactory.get(viewType);
        viewHolderFactory.onViewRecycled(holder);
    }

    @Override
    public void onViewDetachedFromWindow(VH holder) {
        int viewType = getItemViewType(holder.getAdapterPosition());
        IViewHolderFactory<SA, VH, FL, ?, LR> viewHolderFactory = mViewHolderFactory.get(viewType);
        viewHolderFactory.onViewDetachedFromWindow(holder);
    }

    @Override
    public void onViewAttachedToWindow(VH holder) {
        int viewType = getItemViewType(holder.getAdapterPosition());
        IViewHolderFactory<SA, VH, FL, ?, LR> viewHolderFactory = mViewHolderFactory.get(viewType);
        viewHolderFactory.onViewAttachedToWindow(holder);
    }

    @Override
    public boolean onFailedToRecycleView(VH holder) {
        int viewType = getItemViewType(holder.getAdapterPosition());
        IViewHolderFactory<SA, VH, FL, ?, LR> viewHolderFactory = mViewHolderFactory.get(viewType);
        return viewHolderFactory.onFailedToRecycleView(holder);
    }

    @SuppressWarnings("unchecked")
    private <EVH extends VH> void onBindViewHolderInner(
            IViewHolderFactory<SA, VH, FL, EVH, LR> viewHolderFactory,
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
        for (IViewHolderFactory viewHolderFactory : mViewHolderFactory) {
            if (viewHolderFactory.isAssignable(object)) {
                return index;
            }
            index++;
        }
        return mViewHolderFactory.size() - 1;
    }

    public abstract Object getItemAt(int position);

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

    public abstract SA getSelf();

    public abstract VH createNullViewHolder();

    public interface IViewHolderFactory<
            SA extends AbsScrambleAdapter<?, SA, VH, ?, ?, ?>,
            VH extends RecyclerView.ViewHolder,
            FL extends IForwardingListener<SA, VH, LR>,
            EVH extends VH,
            LR extends IListenerRelay<? super VH>
            > {
        EVH onCreateViewHolder(SA adapter, ViewGroup parent, FL forwardingListener);

        void onBindViewHolder(SA adapter, EVH holder, int position, Object object);

        boolean isAssignable(Object object);

        FL createForwardingListener();

        boolean onFailedToRecycleView(VH holder);

        void onViewAttachedToWindow(VH holder);

        void onViewDetachedFromWindow(VH holder);

        void onViewRecycled(VH holder);
    }

    public static class NullViewHolderFactory<
            SA extends AbsScrambleAdapter<?, SA, VH, ?, ?, ?>,
            VH extends RecyclerView.ViewHolder,
            FL extends IForwardingListener<SA, VH, LR>,
            EVH extends VH,
            LR extends IListenerRelay<? super VH>
            > implements IViewHolderFactory<SA, VH, FL, VH, LR> {
        SA mAdapter;

        public NullViewHolderFactory(SA adapter) {
            this.mAdapter = adapter;
        }

        @Override
        public VH onCreateViewHolder(SA adapter, ViewGroup parent, FL forwardingListener) {
            return mAdapter.createNullViewHolder();
        }

        @Override
        public void onBindViewHolder(SA adapter, VH holder, int position, Object object) {
            // no-op
        }

        @Override
        public boolean isAssignable(Object object) {
            return true;
        }

        @Override
        public FL createForwardingListener() {
            return null;
        }

        @Override
        public void onViewRecycled(VH holder) {
            // no-op
        }

        @Override
        public void onViewDetachedFromWindow(VH holder) {
            // no-op
        }

        @Override
        public void onViewAttachedToWindow(VH holder) {
            // no-op
        }

        @Override
        public boolean onFailedToRecycleView(VH holder) {
            // no-op
            return false;
        }
    }
}
