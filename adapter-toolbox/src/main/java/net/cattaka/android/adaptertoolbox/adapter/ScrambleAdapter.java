package net.cattaka.android.adaptertoolbox.adapter;

import android.content.Context;
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

    public ScrambleAdapter(
            Context context,
            List<T> items,
            ListenerRelay<ScrambleAdapter<?>,
                    RecyclerView.ViewHolder> listenerRelay,
            IViewHolderFactory<ScrambleAdapter<?>,
                    RecyclerView.ViewHolder,
                    ForwardingListener<ScrambleAdapter<?>, RecyclerView.ViewHolder>,
                    ?,
                    ListenerRelay<ScrambleAdapter<?>,
                            RecyclerView.ViewHolder>>... iViewHolderFactories
    ) {
        this(context, items, listenerRelay, Arrays.asList(iViewHolderFactories));
    }

    public ScrambleAdapter(
            Context context,
            List<T> items,
            ListenerRelay<ScrambleAdapter<?>,
                    RecyclerView.ViewHolder> listenerRelay,
            List<? extends IViewHolderFactory<ScrambleAdapter<?>,
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
        public ForwardingListener<ScrambleAdapter<?>, RecyclerView.ViewHolder> createForwardingListener() {
            return new ForwardingListener<>();
        }

        @Override
        public boolean onFailedToRecycleView(RecyclerView.ViewHolder holder) {
            // no-op
            return false;
        }

        @Override
        public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
            // no-op
        }

        @Override
        public void onViewDetachedFromWindow(RecyclerView.ViewHolder holder) {
            // no-op
        }

        @Override
        public void onViewRecycled(RecyclerView.ViewHolder holder) {
            // no-op
        }
    }
}
