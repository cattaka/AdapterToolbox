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
public class ScrambleAdapter extends AbsScrambleAdapter<ScrambleAdapter, RecyclerView.ViewHolder, ForwardingListener<ScrambleAdapter, RecyclerView.ViewHolder>, RecyclerView.ViewHolder, ForwardingListener<ScrambleAdapter, RecyclerView.ViewHolder>> {
    private Context mContext;
    private List<Object> mItems;
    private ListenerRelay<ScrambleAdapter, RecyclerView.ViewHolder> mListenerRelay = new ListenerRelay<>();

    @SafeVarargs
    public ScrambleAdapter(Context context, List<Object> items, IViewHolderFactory<ScrambleAdapter, RecyclerView.ViewHolder, ForwardingListener<ScrambleAdapter, RecyclerView.ViewHolder>, ?, ?>... viewHolderFactories) {
        super(viewHolderFactories);
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
    public ForwardingListener<ScrambleAdapter, RecyclerView.ViewHolder> createForwardingListener(IViewHolderFactory viewHolderFactory) {
        ForwardingListener<ScrambleAdapter, RecyclerView.ViewHolder> fl = new ForwardingListener<>();
        fl.setListenerRelay(mListenerRelay);
        return fl;
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

    public void setListenerRelay(ListenerRelay<ScrambleAdapter, RecyclerView.ViewHolder> listenerRelay) {
        this.mListenerRelay = listenerRelay;
    }
}
