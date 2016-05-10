package net.cattaka.android.snippets.adapter;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import net.cattaka.android.snippets.R;

import java.util.List;

/**
 * Created by takao on 2016/05/10.
 */
public class ScrambleAdapter extends AbsScrambleAdapter<RecyclerView.ViewHolder, ScrambleAdapter.ForwardingListener> {
    @IdRes
    public static int VIEW_HOLDER = R.id.viewholder;

    private Context mContext;
    private List<Object> mItems;
    private net.cattaka.android.snippets.adapter.OnItemClickListener<ScrambleAdapter, RecyclerView.ViewHolder> mListener;
    private net.cattaka.android.snippets.adapter.OnItemLongClickListener<ScrambleAdapter, RecyclerView.ViewHolder> mLongListener;

    public ScrambleAdapter(Context context, List<Object> items, IViewHolderFactory<? extends RecyclerView.ViewHolder, ? extends ForwardingListener>... viewHolderFactories) {
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
    public ForwardingListener createForwardingListener(IViewHolderFactory<? extends RecyclerView.ViewHolder, ? extends ForwardingListener> viewHolderFactory) {
        return new ForwardingListener(viewHolderFactory);
    }

    public void setOnItemClickListener(net.cattaka.android.snippets.adapter.OnItemClickListener<ScrambleAdapter, RecyclerView.ViewHolder> listener) {
        mListener = listener;
    }

    public void setOnItemLongClickListener(net.cattaka.android.snippets.adapter.OnItemLongClickListener<ScrambleAdapter, RecyclerView.ViewHolder> longListener) {
        mLongListener = longListener;
    }

    @Override
    public Object getItemAt(int position) {
        return mItems.get(position);
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public class ForwardingListener extends AbsScrambleAdapter.ForwardingListener<RecyclerView.ViewHolder, ScrambleAdapter.ForwardingListener> implements View.OnClickListener, View.OnLongClickListener {
        public ForwardingListener(IViewHolderFactory<? extends RecyclerView.ViewHolder, ? extends ForwardingListener> viewHolderFactory) {
            super(viewHolderFactory);
        }

        @Override
        public void onClick(View view) {
            if (mListener != null) {
                RecyclerView.ViewHolder vh = (RecyclerView.ViewHolder) view.getTag(VIEW_HOLDER);
                int position = (vh != null) ? pickCompatPosition(vh) : RecyclerView.NO_POSITION;
                if (position != RecyclerView.NO_POSITION) {
                    mListener.onItemClick(mRecyclerView, ScrambleAdapter.this, position, view.getId(), vh);
                }
            }
        }

        @Override
        public boolean onLongClick(View view) {
            if (mLongListener != null) {
                RecyclerView.ViewHolder vh = (RecyclerView.ViewHolder) view.getTag(VIEW_HOLDER);
                int position = (vh != null) ? pickCompatPosition(vh) : RecyclerView.NO_POSITION;
                if (position != RecyclerView.NO_POSITION) {
                    mLongListener.onItemLongClick(mRecyclerView, ScrambleAdapter.this, position, view.getId(), view, vh);
                }
                return true;
            }
            return false;
        }
    }

    public static int pickCompatPosition(RecyclerView.ViewHolder vh) {
        if (vh instanceof AdapterConverter.ViewHolder) {
            return ((AdapterConverter.ViewHolder) vh).getCompatPosition();
        }
        return vh.getAdapterPosition();
    }

    public interface OnItemClickListener extends net.cattaka.android.snippets.adapter.OnItemClickListener<ScrambleAdapter, RecyclerView.ViewHolder> {
    }

    public interface OnItemLongClickListener extends net.cattaka.android.snippets.adapter.OnItemLongClickListener<ScrambleAdapter, RecyclerView.ViewHolder> {
    }
}
