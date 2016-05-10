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
    private OnItemClickListener mListener;
    private OnItemLongClickListener mLongListener;

    public ScrambleAdapter(Class<RecyclerView.ViewHolder> viewHolderClazz, List<Object> items, Context context, IViewHolderFactory<RecyclerView.ViewHolder, ScrambleAdapter.ForwardingListener>... viewHolderFactories) {
        super(viewHolderClazz, items, viewHolderFactories);
        mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder createNullViewHolder() {
        View view = new View(mContext);
        view.setLayoutParams(new RecyclerView.LayoutParams(0, 0));
        return new RecyclerView.ViewHolder(view) {
        };
    }

    @Override
    public ScrambleAdapter.ForwardingListener createForwardingListener(IViewHolderFactory<RecyclerView.ViewHolder, ScrambleAdapter.ForwardingListener> viewHolderFactory) {
        return new ForwardingListener(viewHolderFactory);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener longListener) {
        mLongListener = longListener;
    }

    class ForwardingListener extends AbsScrambleAdapter.ForwardingListener<RecyclerView.ViewHolder, ScrambleAdapter.ForwardingListener> implements View.OnClickListener, View.OnLongClickListener {
        public ForwardingListener(IViewHolderFactory<RecyclerView.ViewHolder, ScrambleAdapter.ForwardingListener> viewHolderFactory) {
            super(viewHolderFactory);
        }

        @Override
        public void onClick(View view) {
            if (mListener != null) {
                RecyclerView.ViewHolder vh = (RecyclerView.ViewHolder) view.getTag(VIEW_HOLDER);
                int position = (vh != null) ? vh.getAdapterPosition() : RecyclerView.NO_POSITION;
                if (position != RecyclerView.NO_POSITION) {
                    mListener.onItemClick(mRecyclerView, ScrambleAdapter.this, position, view.getId(), vh, getViewHolderFactory());
                }
            }
        }

        @Override
        public boolean onLongClick(View view) {
            if (mLongListener != null) {
                RecyclerView.ViewHolder vh = (RecyclerView.ViewHolder) view.getTag(VIEW_HOLDER);
                int position = (vh != null) ? vh.getAdapterPosition() : RecyclerView.NO_POSITION;
                if (position != RecyclerView.NO_POSITION) {
                    mLongListener.onItemLongClick(mRecyclerView, ScrambleAdapter.this, position, view.getId(), view, vh, getViewHolderFactory());
                }
                return true;
            }
            return false;
        }
    }

    public interface OnItemClickListener {
        void onItemClick(RecyclerView parent, ScrambleAdapter adapter, int position, int id, RecyclerView.ViewHolder vh, IViewHolderFactory<RecyclerView.ViewHolder, ScrambleAdapter.ForwardingListener> viewHolderFactory);
    }

    public interface OnItemLongClickListener {
        boolean onItemLongClick(RecyclerView parent, ScrambleAdapter adapter, int position, int id, View view, RecyclerView.ViewHolder vh, IViewHolderFactory<RecyclerView.ViewHolder, ScrambleAdapter.ForwardingListener> viewHolderFactory);
    }
}
