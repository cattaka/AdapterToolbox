package net.cattaka.android.snippets.adapter.listener;

import android.support.annotation.IdRes;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import net.cattaka.android.snippets.R;
import net.cattaka.android.snippets.adapter.AdapterConverter;

/**
 * Created by takao on 2016/05/12.
 */
public class ForwardingListener<A extends RecyclerView.Adapter<? super VH>, VH extends RecyclerView.ViewHolder>
        implements IForwardingListener<A, VH>, View.OnClickListener, View.OnLongClickListener {
    @IdRes
    public static int VIEW_HOLDER = R.id.viewholder;

    private IProvider<A, VH> mProvider;

    private ListenerRelay mListenerRelay;

    @Override
    public void setProvider(IProvider<A, VH> provider) {
        mProvider = provider;
    }

    @Override
    public void onClick(View view) {
        if (mListenerRelay != null) {
            @SuppressWarnings("unchecked")
            VH vh = (VH) view.getTag(VIEW_HOLDER);
            int position = (vh != null) ? pickCompatPosition(vh) : RecyclerView.NO_POSITION;
            if (position != RecyclerView.NO_POSITION) {
                mListenerRelay.onItemClick(mProvider.getAttachedRecyclerView(), mProvider.getAdapter(), position, view.getId(), vh);
            }
        }
    }

    @Override
    public boolean onLongClick(View view) {
        if (mListenerRelay != null) {
            @SuppressWarnings("unchecked")
            VH vh = (VH) view.getTag(VIEW_HOLDER);
            int position = (vh != null) ? pickCompatPosition(vh) : RecyclerView.NO_POSITION;
            if (position != RecyclerView.NO_POSITION) {
                mListenerRelay.onItemLongClick(mProvider.getAttachedRecyclerView(), mProvider.getAdapter(), position, view.getId(), view, vh);

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

    public void setListenerRelay(ListenerRelay listenerRelay) {
        this.mListenerRelay = listenerRelay;
    }

    public interface OnItemClickListener<A extends RecyclerView.Adapter<? super VH>, VH extends RecyclerView.ViewHolder> {
        void onItemClick(RecyclerView parent, A adapter, int position, int id, VH vh);
    }

    public interface OnItemLongClickListener<A extends RecyclerView.Adapter<? super VH>, VH extends RecyclerView.ViewHolder> {
        boolean onItemLongClick(RecyclerView parent, A adapter, int position, int id, View view, VH vh);
    }

}
