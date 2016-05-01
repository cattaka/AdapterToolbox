package net.cattaka.android.snippets.adapter;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import net.cattaka.android.snippets.R;

/**
 * Created by cattaka on 2016/04/12.
 */
public class AdapterConverter<S extends AdapterConverter.Adapter<VH, T>, VH extends AdapterConverter.ViewHolder, T> extends BaseAdapter {
    @IdRes
    public static int VIEW_HOLDER = R.id.viewholder;

    S mOrig;

    public AdapterConverter(@NonNull Context context, @NonNull S orig) {
        super();
        mOrig = orig;
    }

    @Override
    public int getCount() {
        return mOrig.getItemCount();
    }

    @Override
    public T getItem(int i) {
        return mOrig.getItemAt(i);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return mOrig.getItemViewType(position);
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        @SuppressWarnings("unchecked")
        VH vh = view != null ? (VH) view.getTag(VIEW_HOLDER) : null;
        if (vh == null) {
            vh = mOrig.createViewHolder(viewGroup, getItemViewType(position));
            vh.itemView.setTag(VIEW_HOLDER, vh);
        }
        vh.setCompatPosition(position);

        mOrig.bindViewHolder(vh, position);
        return vh.itemView;
    }

    public static abstract class ViewHolder extends RecyclerView.ViewHolder {
        int compatPosition = RecyclerView.NO_POSITION;

        public ViewHolder(View itemView) {
            super(itemView);
        }

        public int getCompatPosition() {
            int p = getAdapterPosition();
            return (p != RecyclerView.NO_POSITION) ? p : compatPosition;
        }

        public void setCompatPosition(int compatPosition) {
            this.compatPosition = compatPosition;
        }
    }

    public static abstract class Adapter<VH extends AdapterConverter.ViewHolder, T> extends RecyclerView.Adapter<VH> {
        public abstract T getItemAt(int position);
    }
}
