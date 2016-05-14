package net.cattaka.android.snippets.adapter;

import android.support.annotation.IdRes;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import net.cattaka.android.snippets.R;

/**
 * Created by takao on 2016/05/12.
 */
public class ForwardingListener<A extends RecyclerView.Adapter<? super VH>, VH extends RecyclerView.ViewHolder>
        implements IForwardingListener<A, VH>, View.OnClickListener, View.OnLongClickListener {
    @IdRes
    public static int VIEW_HOLDER = R.id.viewholder;

    private IProvider<A, VH> mProvider;

    private OnItemClickListener<? super A, ? super VH> mListener;
    private OnItemLongClickListener<? super A, ? super VH> mLongListener;

    @Override
    public void setProvider(IProvider<A, VH> provider) {
        mProvider = provider;
    }

    @Override
    public void onClick(View view) {
        if (mListener != null) {
            @SuppressWarnings("unchecked")
            VH vh = (VH) view.getTag(VIEW_HOLDER);
            int position = (vh != null) ? pickCompatPosition(vh) : RecyclerView.NO_POSITION;
            if (position != RecyclerView.NO_POSITION) {
                mListener.onItemClick(mProvider.getAttachedRecyclerView(), mProvider.getAdapter(), position, view.getId(), vh);
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
                mLongListener.onItemLongClick(mProvider.getAttachedRecyclerView(), mProvider.getAdapter(), position, view.getId(), view, vh);

                return true;
            }
        }
        return false;
    }

    public static int pickCompatPosition(RecyclerView.ViewHolder vh) {
        if (vh instanceof AdapterConverter.ViewHolder) {
            return ((AdapterConverter.ViewHolder) vh).getCompatPosition();
        }
        return vh.getAdapterPosition();
    }

    public void setOnItemClickListener(OnItemClickListener<? super A, ? super VH> listener) {
        mListener = listener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener<? super A, ? super VH> longListener) {
        mLongListener = longListener;
    }

    public interface OnItemClickListener<A extends RecyclerView.Adapter<? super VH>, VH extends RecyclerView.ViewHolder> {
        void onItemClick(RecyclerView parent, A adapter, int position, int id, VH vh);
    }

    public interface OnItemLongClickListener<A extends RecyclerView.Adapter<VH>, VH extends RecyclerView.ViewHolder> {
        boolean onItemLongClick(RecyclerView parent, A adapter, int position, int id, View view, VH vh);
    }

}
