package net.cattaka.android.snippets.adapter;

import android.support.annotation.IdRes;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import net.cattaka.android.snippets.R;

import java.util.List;

/**
 * Created by cattaka on 2015/07/15.
 */
public abstract class CustomRecyclerAdapter<VH extends RecyclerView.ViewHolder, T> extends RecyclerView.Adapter<VH> implements View.OnClickListener, View.OnLongClickListener {
    @IdRes
    public static int VIEW_HOLDER = R.id.viewholder;

    protected RecyclerView mRecyclerView;

    private net.cattaka.android.snippets.adapter.OnItemClickListener<CustomRecyclerAdapter<? extends RecyclerView.ViewHolder,?>, RecyclerView.ViewHolder> mListener;
    private net.cattaka.android.snippets.adapter.OnItemLongClickListener<CustomRecyclerAdapter<? extends RecyclerView.ViewHolder,?>, RecyclerView.ViewHolder> mLongListener;

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        mRecyclerView = recyclerView;
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        mRecyclerView = null;
    }

    public void setOnItemClickListener(net.cattaka.android.snippets.adapter.OnItemClickListener<CustomRecyclerAdapter<? extends RecyclerView.ViewHolder,?>, RecyclerView.ViewHolder> listener) {
        mListener = listener;
    }

    public void setOnItemLongClickListener(net.cattaka.android.snippets.adapter.OnItemLongClickListener<CustomRecyclerAdapter<? extends RecyclerView.ViewHolder,?>, RecyclerView.ViewHolder> longListener) {
        mLongListener = longListener;
    }

    @Override
    public void onClick(View view) {
        if (mListener != null) {
            @SuppressWarnings("unchecked")
            VH vh = (VH) view.getTag(VIEW_HOLDER);
            int position = (vh != null) ? pickCompatPosition(vh) : RecyclerView.NO_POSITION;
            if (position != RecyclerView.NO_POSITION) {
                mListener.onItemClick(mRecyclerView, this, position, view.getId(), vh);
            }
        }
    }

    @Override
    public boolean onLongClick(View view) {
        if (mLongListener != null) {
            @SuppressWarnings("unchecked")
            VH vh = (VH) view.getTag(VIEW_HOLDER);
            int position = (vh != null) ? pickCompatPosition(vh) : RecyclerView.NO_POSITION;
            if (position != RecyclerView.NO_POSITION) {
                mLongListener.onItemLongClick(mRecyclerView, this, position, view.getId(), view, vh);
            }
            return true;
        }
        return false;
    }

    public static int pickCompatPosition(RecyclerView.ViewHolder vh) {
        if (vh instanceof AdapterConverter.ViewHolder) {
            return ((AdapterConverter.ViewHolder) vh).getCompatPosition();
        }
        return vh.getAdapterPosition();
    }

    public interface OnItemClickListener extends net.cattaka.android.snippets.adapter.OnItemClickListener<CustomRecyclerAdapter<? extends RecyclerView.ViewHolder,?>, RecyclerView.ViewHolder> {
    }

    public interface OnItemLongClickListener extends net.cattaka.android.snippets.adapter.OnItemLongClickListener<CustomRecyclerAdapter<? extends RecyclerView.ViewHolder,?>, RecyclerView.ViewHolder> {
    }

    public abstract T getItemAt(int position);

    public abstract List<T> getItems();
}
