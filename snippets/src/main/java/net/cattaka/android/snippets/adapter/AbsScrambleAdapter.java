package net.cattaka.android.snippets.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import net.cattaka.android.snippets.adapter.listener.IForwardingListener;
import net.cattaka.android.snippets.adapter.listener.IListenerRelay;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by cattaka on 16/05/10.
 */
public abstract class AbsScrambleAdapter<
        A extends AbsScrambleAdapter<A, VH, FL, ?, ?, LR>,
        VH extends RecyclerView.ViewHolder,
        FL extends IForwardingListener<A, VH, LR>,
        EVH extends VH,
        EFL extends FL,
        LR extends IListenerRelay<? super VH>
        > extends RecyclerView.Adapter<VH> {
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

    RecyclerView mRecyclerView;

    List<IViewHolderFactory<A, VH, FL, ? extends VH, ? extends FL, LR>> mViewHolderFactory;
    List<FL> mForwardingListeners;
    LR mListenerRelay;

    public AbsScrambleAdapter(LR listenerRelay, List<? extends IViewHolderFactory<A, VH, FL, ?, ?, LR>> viewHolderFactories) {
        mListenerRelay = listenerRelay;
        mViewHolderFactory = new ArrayList<>();

        mViewHolderFactory.addAll(viewHolderFactories);

        @SuppressWarnings("unchecked")
        NullViewHolderFactory<A, VH, FL, ?, ?, LR> nvhf = new NullViewHolderFactory(this);
        mViewHolderFactory.add(nvhf);
        mForwardingListeners = new ArrayList<>();
        for (IViewHolderFactory<A, VH, FL, ?, ?, LR> viewHolderFactory : mViewHolderFactory) {
            FL forwardingListener = viewHolderFactory.createForwardingListener();
            forwardingListener.setListenerRelay(listenerRelay);
            forwardingListener.setProvider(mProvider);
            mForwardingListeners.add(forwardingListener);
        }
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        IViewHolderFactory<A, VH, FL, ?, ?, LR> viewHolderFactory = mViewHolderFactory.get(viewType);
        FL forwardingListener = mForwardingListeners.get(viewType);
        return viewHolderFactory.onCreateViewHolder(getSelf(), parent, forwardingListener);
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {
        int viewType = getItemViewType(position);
        IViewHolderFactory<A, VH, FL, ?, ?, LR> viewHolderFactory = mViewHolderFactory.get(viewType);
        onBindViewHolderInner(viewHolderFactory, holder, position);
    }

    @SuppressWarnings("unchecked")
    public <EVH extends VH> void onBindViewHolderInner(IViewHolderFactory<A, VH, FL, EVH, ?, LR> viewHolderFactory, VH holder, int position) {
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

    public void setListenerRelay(LR listenerRelay) {
        this.mListenerRelay = listenerRelay;
    }

    public abstract A getSelf();

    public abstract EVH createNullViewHolder();

    public abstract FL createNullForwardingListener();

    public interface IViewHolderFactory<A extends AbsScrambleAdapter<A, VH, FL, ?, ?, LR>, VH extends RecyclerView.ViewHolder, FL extends IForwardingListener<A, VH, LR>, EVH extends VH, EFL extends FL, LR extends IListenerRelay<? super VH>> {
        EVH onCreateViewHolder(A adapter, ViewGroup parent, FL forwardingListener);

        void onBindViewHolder(A adapter, EVH holder, int position, Object object);

        boolean isAssignable(Object object);

        FL createForwardingListener();
    }

    public static class NullViewHolderFactory<A extends AbsScrambleAdapter<A, VH, FL, EVH, EFL, LR>, VH extends RecyclerView.ViewHolder, FL extends IForwardingListener<A, VH, LR>, EVH extends VH, EFL extends FL, LR extends IListenerRelay<? super VH>> implements IViewHolderFactory<A, VH, FL, EVH, EFL, LR> {
        A mAdapter;

        public NullViewHolderFactory(A mAdapter) {
            this.mAdapter = mAdapter;
        }

        @Override
        public EVH onCreateViewHolder(A adapter, ViewGroup parent, FL forwardingListener) {
            return mAdapter.createNullViewHolder();
        }

        @Override
        public void onBindViewHolder(A adapter, EVH holder, int position, Object object) {
            // no-op
        }

        @Override
        public boolean isAssignable(Object object) {
            return true;
        }

        @Override
        public FL createForwardingListener() {
            return mAdapter.createNullForwardingListener();
        }
    }
}
