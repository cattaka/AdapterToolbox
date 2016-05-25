package net.cattaka.android.adaptertoolbox.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import net.cattaka.android.adaptertoolbox.adapter.listener.ForwardingListener;
import net.cattaka.android.adaptertoolbox.adapter.listener.ListenerRelay;

import java.util.Arrays;
import java.util.List;

/**
 * Created by cattaka on 2016/05/10.
 */
public class ScrambleAdapter<T> extends AbsScrambleAdapter<
        ScrambleAdapter<T>,
        ScrambleAdapter<?>,
        RecyclerView.ViewHolder,
        ForwardingListener<ScrambleAdapter<?>, RecyclerView.ViewHolder>,
        ListenerRelay<ScrambleAdapter<?>, RecyclerView.ViewHolder>,
        T
        > {
    private Context mContext;
    private List<T> mItems;

    @SafeVarargs
    public ScrambleAdapter(
            @NonNull Context context,
            @NonNull List<T> items,
            @Nullable ListenerRelay<ScrambleAdapter<?>,
                    RecyclerView.ViewHolder> listenerRelay,
            @NonNull IViewHolderFactory<ScrambleAdapter<?>,
                    RecyclerView.ViewHolder,
                    ForwardingListener<ScrambleAdapter<?>, RecyclerView.ViewHolder>,
                    ?,
                    ListenerRelay<ScrambleAdapter<?>,
                            RecyclerView.ViewHolder>>... iViewHolderFactories
    ) {
        this(context, items, listenerRelay, Arrays.asList(iViewHolderFactories));
    }

    public ScrambleAdapter(
            @NonNull Context context,
            @NonNull List<T> items,
            @Nullable ListenerRelay<ScrambleAdapter<?>,
                    RecyclerView.ViewHolder> listenerRelay,
            @NonNull List<? extends IViewHolderFactory<ScrambleAdapter<?>,
                    RecyclerView.ViewHolder,
                    ForwardingListener<ScrambleAdapter<?>, RecyclerView.ViewHolder>,
                    ?,
                    ListenerRelay<ScrambleAdapter<?>,
                            RecyclerView.ViewHolder>>> iViewHolderFactories
    ) {
        super(listenerRelay, iViewHolderFactories);
        this.mContext = context;
        this.mItems = items;
    }

    public Context getContext() {
        return mContext;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder createNullViewHolder() {
        View view = new View(mContext);
        return new RecyclerView.ViewHolder(view) {
        };
    }

    @Override
    public T getItemAt(int position) {
        return mItems.get(position);
    }

    public List<T> getItems() {
        return mItems;
    }

    @NonNull
    @Override
    public ScrambleAdapter<T> getSelf() {
        return this;
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public static abstract class AbsViewHolderFactory<EVH extends RecyclerView.ViewHolder>
            implements IViewHolderFactory<
            ScrambleAdapter<?>,
            RecyclerView.ViewHolder,
            ForwardingListener<ScrambleAdapter<?>, RecyclerView.ViewHolder>,
            EVH,
            ListenerRelay<ScrambleAdapter<?>, RecyclerView.ViewHolder>
            > {

        @Override
        public boolean isAssignable(@NonNull ScrambleAdapter<?> adapter, Object object) {
            return isAssignable(object);
        }

        public abstract boolean isAssignable(Object object);

        @Override
        public ForwardingListener<ScrambleAdapter<?>, RecyclerView.ViewHolder> createForwardingListener() {
            return new ForwardingListener<>();
        }

        @Override
        public boolean onFailedToRecycleView(@NonNull ScrambleAdapter<?> adapter, @NonNull RecyclerView.ViewHolder holder) {
            // no-op
            return false;
        }

        @Override
        public void onViewAttachedToWindow(@NonNull ScrambleAdapter<?> adapter, @NonNull RecyclerView.ViewHolder holder) {
            // no-op
        }

        @Override
        public void onViewDetachedFromWindow(@NonNull ScrambleAdapter<?> adapter, @NonNull RecyclerView.ViewHolder holder) {
            // no-op
        }

        @Override
        public void onViewRecycled(@NonNull ScrambleAdapter<?> adapter, @NonNull RecyclerView.ViewHolder holder) {
            // no-op
        }
    }
}
