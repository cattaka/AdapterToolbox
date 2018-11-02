package net.cattaka.android.adaptertoolbox.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

import net.cattaka.android.adaptertoolbox.adapter.listener.IForwardingListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    Map<View, IForwardingListener.IProvider<SA, VH>> mProviderMap = new HashMap<>();
    IForwardingListener.IProvider<SA, VH> mNullProvider = new IForwardingListener.IProvider<SA, VH>() {
        @NonNull
        @Override
        public SA getAdapter() {
            return getSelf();
        }

        @Nullable
        @Override
        public RecyclerView getAttachedRecyclerView() {
            return null;
        }
    };

    List<IViewHolderFactory<SA, VH, FL, ? extends VH>> mViewHolderFactory;
    Map<Class<? extends RecyclerView.ViewHolder>, IViewHolderFactory<SA, VH, FL, ? extends VH>> mViewHolder2FactoryMap;
    Map<View, SparseArray<FL>> mForwardingListenersMap = new HashMap<>();

    public AbsScrambleAdapter(
            @NonNull List<? extends IViewHolderFactory<SA, VH, FL, ?>> viewHolderFactories
    ) {
        mViewHolderFactory = new ArrayList<>();
        mViewHolder2FactoryMap = new HashMap<>();

        mViewHolderFactory.addAll(viewHolderFactories);

        @SuppressWarnings("unchecked")
        NullViewHolderFactory<SA, VH, FL> nvhf = new NullViewHolderFactory(this);
        mViewHolderFactory.add(nvhf);
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        IViewHolderFactory<SA, VH, FL, ?> viewHolderFactory = mViewHolderFactory.get(viewType);
        FL forwardingListener;
        SparseArray<FL> forwardingListeners = mForwardingListenersMap.get(parent);
        if (forwardingListeners == null) {
            // Error case
            forwardingListeners = new SparseArray<>();
            mForwardingListenersMap.put(parent, forwardingListeners);
        }
        if (forwardingListeners.indexOfKey(viewType) < 0) {
            forwardingListener = createForwardingListener(viewHolderFactory);
            if (forwardingListener != null) {
                forwardingListener.setProvider(getProvider(parent));
            }
            forwardingListeners.put(viewType, forwardingListener);
        } else {
            forwardingListener = forwardingListeners.get(viewType);
        }
        VH holder = viewHolderFactory.onCreateViewHolder(getSelf(), parent, forwardingListener);
        mViewHolder2FactoryMap.put(holder.getClass(), viewHolderFactory);
        return holder;
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {
        int viewType = getItemViewType(position);
        IViewHolderFactory<SA, VH, FL, ?> viewHolderFactory = mViewHolderFactory.get(viewType);
        onBindViewHolderInner(viewHolderFactory, holder, position);
    }

    @Override
    public void onBindViewHolder(VH holder, int position, List<Object> payloads) {
        int viewType = getItemViewType(position);
        IViewHolderFactory<SA, VH, FL, ?> viewHolderFactory = mViewHolderFactory.get(viewType);
        onBindViewHolderInner(viewHolderFactory, holder, position, payloads);
    }

    @Override
    public void onViewRecycled(VH holder) {
        IViewHolderFactory<SA, VH, FL, ?> viewHolderFactory = mViewHolder2FactoryMap.get(holder.getClass());
        if (viewHolderFactory != null) {
            viewHolderFactory.onViewRecycled(getSelf(), holder);
        }
    }

    @Override
    public void onViewDetachedFromWindow(VH holder) {
        IViewHolderFactory<SA, VH, FL, ?> viewHolderFactory = mViewHolder2FactoryMap.get(holder.getClass());
        if (viewHolderFactory != null) {
            viewHolderFactory.onViewDetachedFromWindow(getSelf(), holder);
        }
    }

    @Override
    public void onViewAttachedToWindow(VH holder) {
        IViewHolderFactory<SA, VH, FL, ?> viewHolderFactory = mViewHolder2FactoryMap.get(holder.getClass());
        if (viewHolderFactory != null) {
            viewHolderFactory.onViewAttachedToWindow(getSelf(), holder);
        }
    }

    @Override
    public boolean onFailedToRecycleView(VH holder) {
        IViewHolderFactory<SA, VH, FL, ?> viewHolderFactory = mViewHolder2FactoryMap.get(holder.getClass());
        if (viewHolderFactory != null) {
            return viewHolderFactory.onFailedToRecycleView(getSelf(), holder);
        } else {
            return super.onFailedToRecycleView(holder);
        }
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

    @SuppressWarnings("unchecked")
    private <EVH extends VH> void onBindViewHolderInner(
            IViewHolderFactory<SA, VH, FL, EVH> viewHolderFactory,
            VH holder,
            int position,
            List<Object> payloads
    ) {
        Object object = getItemAt(position);
        viewHolderFactory.onBindViewHolder(getSelf(), (EVH) holder, position, object, payloads);
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

    @Nullable
    public IViewHolderFactory<SA, VH, FL, ?> getViewHolderFactory(int itemViewType) {
        return mViewHolderFactory.get(itemViewType);
    }

    @Nullable
    public IViewHolderFactory<SA, VH, FL, ?> getViewHolderFactoryByPosition(int position) {
        int viewType = getItemViewType(position);
        return mViewHolderFactory.get(viewType);
    }

    public abstract T getItemAt(int position);

    @Override
    public void onAttachedToRecyclerView(final RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        mProviderMap.put(recyclerView, new IForwardingListener.IProvider<SA, VH>() {
            @NonNull
            @Override
            public SA getAdapter() {
                return getSelf();
            }

            @Nullable
            @Override
            public RecyclerView getAttachedRecyclerView() {
                return recyclerView;
            }
        });
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        mProviderMap.remove(recyclerView);
        mForwardingListenersMap.remove(recyclerView);
    }

    protected IForwardingListener.IProvider<SA, VH> getProvider(View parent) {
        IForwardingListener.IProvider<SA, VH> provider = mProviderMap.get(parent);
        return (provider != null) ? provider : mNullProvider;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    public SA getSelf() {
        return (SA) this;
    }

    public int getViewTypeCount() {
        return mViewHolderFactory.size();
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

        void onBindViewHolder(@NonNull SA adapter, @NonNull EVH holder, int position, @Nullable Object object, List<Object> payloads);

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
        public void onBindViewHolder(@NonNull SA adapter, @NonNull VH holder, int position, @Nullable Object object, List<Object> payloads) {
            this.onBindViewHolder(adapter, holder, position, object);
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
