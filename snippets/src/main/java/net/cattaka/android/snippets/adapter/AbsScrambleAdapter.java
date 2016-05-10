package net.cattaka.android.snippets.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by cattaka on 16/05/10.
 */
public abstract class AbsScrambleAdapter<VH extends RecyclerView.ViewHolder, FL extends AbsScrambleAdapter.ForwardingListener> extends RecyclerView.Adapter<VH> {
    RecyclerView mRecyclerView;

    List<Object> mItems;
    List<IViewHolderFactory<VH, FL>> mViewHolderFactory;
    List<FL> mForwardingListeners;

    public AbsScrambleAdapter(Class<VH> viewHolderClazz, List<Object> items, IViewHolderFactory<VH, FL>... viewHolderFactories) {
        mItems = items;
        mViewHolderFactory = new ArrayList<>();
        mViewHolderFactory.addAll(Arrays.asList(viewHolderFactories));
        mViewHolderFactory.add(new NullViewHolderFactory<>(this));
        mForwardingListeners = new ArrayList<>();
        for (IViewHolderFactory<VH, FL> viewHolderFactory : mViewHolderFactory) {
            mForwardingListeners.add(createForwardingListener(viewHolderFactory));
        }
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        IViewHolderFactory<VH, FL> viewHolderFactory = mViewHolderFactory.get(viewType);
        FL forwardingListener = mForwardingListeners.get(viewType);
        return viewHolderFactory.onCreateViewHolder(this, parent, viewType, forwardingListener);
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {
        IViewHolderFactory<VH, FL> viewHolderFactory = mViewHolderFactory.get(position);
        Object object = mItems.get(position);
        viewHolderFactory.onBindViewHolder(this, holder, position, object);
    }

    @Override
    public int getItemViewType(int position) {
        Object object = mItems.get(position);
        int index = 0;
        for (IViewHolderFactory viewHolderFactory : mViewHolderFactory) {
            if (viewHolderFactory.isAssignable(object)) {
                return index;
            }
            index++;
        }
        return index;
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

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

    public abstract FL createForwardingListener(IViewHolderFactory<VH, FL> viewHolderFactory);

    public static class ForwardingListener<VH extends RecyclerView.ViewHolder, FL extends AbsScrambleAdapter.ForwardingListener> {
        IViewHolderFactory<VH, FL> mViewHolderFactory;

        public ForwardingListener(IViewHolderFactory<VH, FL> viewHolderFactory) {
            mViewHolderFactory = viewHolderFactory;
        }

        public IViewHolderFactory<VH, FL> getViewHolderFactory() {
            return mViewHolderFactory;
        }
    }

    public interface IViewHolderFactory<VH extends RecyclerView.ViewHolder, FL extends AbsScrambleAdapter.ForwardingListener> {
        VH onCreateViewHolder(AbsScrambleAdapter adapter, ViewGroup parent, int viewType, FL forwardingListener);

        void onBindViewHolder(AbsScrambleAdapter adapter, VH holder, int position, Object object);

        boolean isAssignable(Object object);
    }

    public static class NullViewHolderFactory<VH extends RecyclerView.ViewHolder, FL extends AbsScrambleAdapter.ForwardingListener> implements IViewHolderFactory<VH, FL> {
        AbsScrambleAdapter<VH, FL> mAdapter;

        public NullViewHolderFactory(AbsScrambleAdapter<VH, FL> adapter) {
            mAdapter = adapter;
        }

        @Override
        public VH onCreateViewHolder(AbsScrambleAdapter adapter, ViewGroup parent, int viewType, FL forwardingListener) {
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
