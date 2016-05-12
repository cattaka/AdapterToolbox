package net.cattaka.android.snippets.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.List;

/**
 * Created by takao on 2016/05/10.
 */
public class ScrambleAdapter extends AbsScrambleAdapter<ScrambleAdapter, RecyclerView.ViewHolder, ForwardingListener<ScrambleAdapter, RecyclerView.ViewHolder>, RecyclerView.ViewHolder, ForwardingListener<ScrambleAdapter, RecyclerView.ViewHolder>> {
    private ForwardingListener.OnItemClickListener<ScrambleAdapter, RecyclerView.ViewHolder> mInnerOnItemClickListener = new ForwardingListener.OnItemClickListener<ScrambleAdapter, RecyclerView.ViewHolder>() {
        @Override
        public void onItemClick(RecyclerView parent, ScrambleAdapter adapter, int position, int id, RecyclerView.ViewHolder viewHolder) {
            if (mOnItemClickListener != null) {
                mOnItemClickListener.onItemClick(parent, adapter, position, id, viewHolder);
            }
        }
    };
    private ForwardingListener.OnItemLongClickListener<ScrambleAdapter, RecyclerView.ViewHolder> mInnerOnItemLongClickListener = new ForwardingListener.OnItemLongClickListener<ScrambleAdapter, RecyclerView.ViewHolder>() {
        @Override
        public boolean onItemLongClick(RecyclerView parent, ScrambleAdapter adapter, int position, int id, View view, RecyclerView.ViewHolder viewHolder) {
            if (mOnItemLongClickListener != null) {
                return mOnItemLongClickListener.onItemLongClick(parent, adapter, position, id, view, viewHolder);
            }
            return false;
        }
    };

    private Context mContext;
    private List<Object> mItems;

    private ForwardingListener.OnItemClickListener<ScrambleAdapter, RecyclerView.ViewHolder> mOnItemClickListener;
    private ForwardingListener.OnItemLongClickListener<ScrambleAdapter, RecyclerView.ViewHolder> mOnItemLongClickListener;

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
        fl.setOnItemClickListener(mInnerOnItemClickListener);
        fl.setOnItemLongClickListener(mInnerOnItemLongClickListener);
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

    public void setOnItemClickListener(ForwardingListener.OnItemClickListener<ScrambleAdapter, RecyclerView.ViewHolder> onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public void setOnItemLongClickListener(ForwardingListener.OnItemLongClickListener<ScrambleAdapter, RecyclerView.ViewHolder> onItemLongClickListener) {
        mOnItemLongClickListener = onItemLongClickListener;
    }
}
