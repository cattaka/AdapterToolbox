package net.cattaka.android.adaptertoolbox.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

import net.cattaka.android.adaptertoolbox.adapter.listener.IForwardingListener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by cattaka on 2015/07/15.
 */
public abstract class AbsCustomRecyclerAdapter<
        A extends AbsCustomRecyclerAdapter<A, VH, T, FL>,
        VH extends RecyclerView.ViewHolder,
        T,
        FL extends IForwardingListener<A, VH>
        > extends RecyclerView.Adapter<VH> implements IHasItemAdapter<VH, T> {

    Map<View, IForwardingListener.IProvider<A, VH>> mProviderMap = new HashMap<>();
    IForwardingListener.IProvider<A, VH> mNullProvider = new IForwardingListener.IProvider<A, VH>() {
        @NonNull
        @Override
        public A getAdapter() {
            return getSelf();
        }

        @Nullable
        @Override
        public RecyclerView getAttachedRecyclerView() {
            return null;
        }
    };

    Map<View, FL> mForwardingListenerMap = new HashMap<>();

    private FL mForwardingListener;

    public AbsCustomRecyclerAdapter() {
    }

    @SuppressWarnings("unchecked")
    @NonNull
    public A getSelf() {
        return (A) this;
    }

    @NonNull
    public FL getForwardingListener(View parent) {
        FL forwardingListener = mForwardingListenerMap.get(parent);
        if (forwardingListener == null) {
            forwardingListener = createForwardingListener();
            forwardingListener.setProvider(getProvider(parent));
            mForwardingListenerMap.put(parent, forwardingListener);
        }
        return forwardingListener;
    }

    @Override
    public void onAttachedToRecyclerView(final RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        mProviderMap.put(recyclerView, new IForwardingListener.IProvider<A, VH>() {
            @NonNull
            @Override
            public A getAdapter() {
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
        mForwardingListenerMap.remove(recyclerView);
    }

    protected IForwardingListener.IProvider<A, VH> getProvider(View parent) {
        IForwardingListener.IProvider<A, VH> provider = mProviderMap.get(parent);
        return (provider != null) ? provider : mNullProvider;
    }

    @NonNull
    public abstract FL createForwardingListener();

    public int getViewTypeCount() {
        return 1;
    }

    public abstract T getItemAt(int position);

    public abstract List<T> getItems();
}
