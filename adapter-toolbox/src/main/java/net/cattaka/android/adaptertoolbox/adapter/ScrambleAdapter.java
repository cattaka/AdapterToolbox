package net.cattaka.android.adaptertoolbox.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

import net.cattaka.android.adaptertoolbox.adapter.listener.ForwardingListener;
import net.cattaka.android.adaptertoolbox.adapter.listener.ListenerRelay;
import net.cattaka.android.adaptertoolbox.classic.listener.ClassicForwardingListener;

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
        T
        > {
    private Context mContext;
    private List<T> mItems;

    private ListenerRelay<ScrambleAdapter<?>, RecyclerView.ViewHolder> mListenerRelay;

    @SafeVarargs
    public ScrambleAdapter(
            @NonNull Context context,
            @NonNull List<T> items,
            @Nullable ListenerRelay<ScrambleAdapter<?>,
                    RecyclerView.ViewHolder> listenerRelay,
            @NonNull IViewHolderFactory<ScrambleAdapter<?>,
                    RecyclerView.ViewHolder,
                    ForwardingListener<ScrambleAdapter<?>, RecyclerView.ViewHolder>,
                    ?
                    >... iViewHolderFactories
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
                    ?
                    >> iViewHolderFactories
    ) {
        super(iViewHolderFactories);
        this.mContext = context;
        this.mItems = items;
        this.mListenerRelay = listenerRelay;
    }

    public Context getContext() {
        return mContext;
    }

    @Override
    public ForwardingListener<ScrambleAdapter<?>, RecyclerView.ViewHolder> createForwardingListener(IViewHolderFactory<ScrambleAdapter<?>, RecyclerView.ViewHolder, ForwardingListener<ScrambleAdapter<?>, RecyclerView.ViewHolder>, ?> viewHolderFactory) {
        ForwardingListener<ScrambleAdapter<?>, RecyclerView.ViewHolder> forwardingListener = viewHolderFactory.createForwardingListener();
        if (forwardingListener != null) {
            forwardingListener.setListenerRelay(mListenerRelay);
        }
        return forwardingListener;
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

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public static abstract class AbsViewHolderFactory<EVH extends RecyclerView.ViewHolder>
            implements IViewHolderFactory<
            ScrambleAdapter<?>,
            RecyclerView.ViewHolder,
            ForwardingListener<ScrambleAdapter<?>, RecyclerView.ViewHolder>,
            EVH
            > {

        @Override
        public boolean isAssignable(@NonNull ScrambleAdapter<?> adapter, Object object) {
            return isAssignable(object);
        }

        public abstract boolean isAssignable(Object object);

        @Override
        public void onBindViewHolder(@NonNull ScrambleAdapter<?> adapter, @NonNull EVH holder, int position, @Nullable Object object, List<Object> payloads) {
            this.onBindViewHolder(adapter, holder, position, object);
        }

        @Override
        public void onBindViewHolder(@NonNull ScrambleAdapter<?> adapter, @NonNull EVH holder, int position, @Nullable Object object) {
            // override me
        }

        @Override
        public ForwardingListener<ScrambleAdapter<?>, RecyclerView.ViewHolder> createForwardingListener() {
            return new ForwardingListener<>();
        }

        public ClassicForwardingListener<ScrambleAdapter<?>, RecyclerView.ViewHolder> createClassicForwardingListener() {
            return new ClassicForwardingListener<>();
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
