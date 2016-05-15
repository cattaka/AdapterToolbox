package net.cattaka.android.snippets.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import net.cattaka.android.snippets.adapter.listener.ForwardingListener;
import net.cattaka.android.snippets.adapter.listener.ListenerRelay;

import java.util.List;

/**
 * Created by takao on 2016/05/10.
 */
public class ScrambleAdapter extends AbsScrambleAdapter<
        ScrambleAdapter,
        RecyclerView.ViewHolder,
        ForwardingListener<ScrambleAdapter, RecyclerView.ViewHolder>,
        RecyclerView.ViewHolder,
        ForwardingListener<ScrambleAdapter, RecyclerView.ViewHolder>,
        ListenerRelay<ScrambleAdapter, ? super RecyclerView.ViewHolder>> {
    private Context mContext;
    private List<Object> mItems;
    private ListenerRelay<ScrambleAdapter, RecyclerView.ViewHolder> mListenerRelay = new ListenerRelay<>();

    @SafeVarargs
    public ScrambleAdapter(Context context, List<Object> items, ListenerRelay<ScrambleAdapter, RecyclerView.ViewHolder> listenerRelay, IViewHolderFactory<ScrambleAdapter, RecyclerView.ViewHolder, ForwardingListener<ScrambleAdapter, RecyclerView.ViewHolder>, ?, ?, ListenerRelay<ScrambleAdapter, ? super RecyclerView.ViewHolder>>... viewHolderFactories) {
        super(listenerRelay, viewHolderFactories);
        mContext = context;
        mItems = items;
    }

    @Override
    public RecyclerView.ViewHolder createNullViewHolder() {
        View view = new View(mContext);
        view.setLayoutParams(new RecyclerView.LayoutParams(0, 0));
        return new RecyclerView.ViewHolder(view) {
        };
    }

    @Override
    public ForwardingListener<ScrambleAdapter, RecyclerView.ViewHolder> createNullForwardingListener() {
        return new ForwardingListener<>();
    }

    @Override
    public ScrambleAdapter getSelf() {
        return this;
    }


    @Override
    public Object getItemAt(int position) {
        return mItems.get(position);
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public abstract static class AbsViewHolderFactory<VH extends RecyclerView.ViewHolder> implements IViewHolderFactory<ScrambleAdapter, RecyclerView.ViewHolder, ForwardingListener<ScrambleAdapter, RecyclerView.ViewHolder>, VH, ForwardingListener<ScrambleAdapter, RecyclerView.ViewHolder>, ListenerRelay<ScrambleAdapter, ? super RecyclerView.ViewHolder>> {
        @Override
        public ForwardingListener<ScrambleAdapter, RecyclerView.ViewHolder> createForwardingListener() {
            return new ForwardingListener<>();
        }
    }
}
