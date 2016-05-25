package net.cattaka.android.adaptertoolbox.adapter;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import net.cattaka.android.adaptertoolbox.R;

/**
 * Created by cattaka on 2016/04/12.
 */
public class AdapterConverter<
        S extends IHasItemAdapter<VH,T>,
        VH extends RecyclerView.ViewHolder,
        T
        > extends BaseAdapter {
    @IdRes
    public static int VIEW_HOLDER = R.id.viewholder;

    private S mOrig;

    public AdapterConverter(@NonNull Context context, @NonNull S orig) {
        super();
        mOrig = orig;
    }

    @NonNull
    public S getOrig() {
        return mOrig;
    }

    @Override
    public int getCount() {
        return mOrig.getItemCount();
    }

    @Override
    public T getItem(int i) {
        return (T) mOrig.getItemAt(i);
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
    public View getView(int position, View convertView, ViewGroup parent) {
        @SuppressWarnings("unchecked")
        VH vh = convertView != null ? (VH) findViewHolder(convertView) : null;
        if (vh == null) {
            vh = mOrig.onCreateViewHolder(parent, getItemViewType(position));
            vh.itemView.setTag(VIEW_HOLDER, vh);
        }
        if(vh instanceof AdapterConverter.ViewHolder) {
            ((AdapterConverter.ViewHolder)vh).setCompatPosition(position);
        }

        mOrig.onBindViewHolder(vh, position);
        return vh.itemView;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        @SuppressWarnings("unchecked")
        VH vh = convertView != null ? (VH) findViewHolder(convertView) : null;
        if (vh == null) {
            vh = mOrig.onCreateViewHolder(parent, getItemViewType(position));
            vh.itemView.setTag(VIEW_HOLDER, vh);
        }
        if(vh instanceof AdapterConverter.ViewHolder) {
            ((AdapterConverter.ViewHolder)vh).setCompatPosition(position);
        }

        mOrig.onBindViewHolder(vh, position);
        return vh.itemView;
    }

    @Nullable
    public static Object findViewHolder(@Nullable View view) {
        View p = view;
        while (p != null) {
            Object viewHolder = p.getTag(VIEW_HOLDER);
            if (viewHolder != null) {
                return viewHolder;
            }
            p = (p.getParent() instanceof View) ? (View) p.getParent() : null;
        }
        return null;
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

}
