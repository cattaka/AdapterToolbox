package net.cattaka.android.snippets.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by cattaka on 16/05/10.
 */
public abstract class AbsScrambleAdapter<VH extends RecyclerView.ViewHolder, FL extends AbsScrambleAdapter.IForwardingListener> extends RecyclerView.Adapter<VH> {
    RecyclerView mRecyclerView;

    List<IViewHolderFactory<? extends VH, ? extends FL>> mViewHolderFactory;
    List<FL> mForwardingListeners;

    public AbsScrambleAdapter(IViewHolderFactory<? extends VH, ? extends FL>... viewHolderFactories) {
        mViewHolderFactory = new ArrayList<>();

        mViewHolderFactory.addAll(Arrays.asList(viewHolderFactories));
        mViewHolderFactory.add(new NullViewHolderFactory<>(this));
        mForwardingListeners = new ArrayList<>();
        for (IViewHolderFactory<? extends VH, ? extends FL> viewHolderFactory : mViewHolderFactory) {
            mForwardingListeners.add(createForwardingListener(viewHolderFactory));
        }
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        IViewHolderFactory<? extends VH, ? extends FL> viewHolderFactory = mViewHolderFactory.get(viewType);
        FL forwardingListener = mForwardingListeners.get(viewType);
        return onCreateViewHolderInner(parent, viewHolderFactory, forwardingListener);
    }

    @SuppressWarnings("unchecked")
    private <EFL extends FL> VH onCreateViewHolderInner(ViewGroup parent, IViewHolderFactory<? extends VH, EFL> viewHolderFactory, FL forwardingListener) {
        return viewHolderFactory.onCreateViewHolder(this, parent, (EFL) forwardingListener);
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {
        int viewType = getItemViewType(position);
        IViewHolderFactory<? extends VH, ? extends FL> viewHolderFactory = mViewHolderFactory.get(viewType);
        onBindViewHolderInner(viewHolderFactory, holder, position);
    }

    @SuppressWarnings("unchecked")
    public <EVH extends VH> void onBindViewHolderInner(IViewHolderFactory<EVH, ? extends FL> viewHolderFactory, VH holder, int position) {
        Object object = getItemAt(position);
        viewHolderFactory.onBindViewHolder(this, (EVH) holder, position, object);
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

    public abstract VH createNullViewHolder();

    public abstract FL createForwardingListener(IViewHolderFactory<? extends VH, ? extends FL> viewHolderFactory);

    public interface IForwardingListener {

    }

    public interface IViewHolderFactory<VH extends RecyclerView.ViewHolder, FL extends IForwardingListener> {
        VH onCreateViewHolder(AbsScrambleAdapter adapter, ViewGroup parent, FL forwardingListener);

        void onBindViewHolder(AbsScrambleAdapter adapter, VH holder, int position, Object object);

        boolean isAssignable(Object object);
    }

    public static class NullViewHolderFactory<VH extends RecyclerView.ViewHolder, FL extends IForwardingListener> implements IViewHolderFactory<VH, FL> {
        AbsScrambleAdapter<VH, FL> mAdapter;

        public NullViewHolderFactory(AbsScrambleAdapter<VH, FL> adapter) {
            mAdapter = adapter;
        }

        @Override
        public VH onCreateViewHolder(AbsScrambleAdapter adapter, ViewGroup parent, FL forwardingListener) {
            return mAdapter.createNullViewHolder();
        }

        @Override
        public void onBindViewHolder(AbsScrambleAdapter adapter, VH holder, int position, Object object) {
            // no-op
        }

        public boolean isAssignable(Object object) {
            return true;
        }
    }
}
