package net.cattaka.android.adaptertoolbox.classic;

import android.database.DataSetObserver;
import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import net.cattaka.android.adaptertoolbox.R;
import net.cattaka.android.adaptertoolbox.adapter.IHasItemAdapter;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by cattaka on 2016/04/12.
 */
public class AdapterConverter<
        S extends IHasItemAdapter<VH, T>,
        VH extends RecyclerView.ViewHolder,
        T
        > extends BaseAdapter {
    @IdRes
    public static int VIEW_HOLDER = R.id.viewholder;

    private S mOrig;
    private boolean mRecyclingDisabled = false;

    private Map<DataSetObserver, AdapterDataObserver> mAdapterDataObservers = new HashMap<>();

    public AdapterConverter() {
        super();
    }

    public void setOriginal(S orig) {
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
        return mRecyclingDisabled ? 1 : mOrig.getItemViewType(position);
    }

    @Override
    public int getViewTypeCount() {
        return mRecyclingDisabled ? 1 : mOrig.getViewTypeCount();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int itemViewType = mOrig.getItemViewType(position);
        @SuppressWarnings("unchecked")
        ViewHolderWrapper<VH> vhw = convertView != null ? (ViewHolderWrapper<VH>) findViewHolder(convertView) : null;
        if (vhw == null || vhw.getItemViewType() != itemViewType) {
            vhw = new ViewHolderWrapper<>(mOrig.onCreateViewHolder(parent, itemViewType));
            vhw.getOrig().itemView.setTag(VIEW_HOLDER, vhw);
        }
        vhw.setPosition(position);
        vhw.setItemViewType(itemViewType);

        mOrig.onBindViewHolder(vhw.getOrig(), position);
        return vhw.getOrig().itemView;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        int itemViewType = mOrig.getItemViewType(position);
        @SuppressWarnings("unchecked")
        ViewHolderWrapper<VH> vhw = convertView != null ? (ViewHolderWrapper<VH>) findViewHolder(convertView) : null;
        if (vhw == null || vhw.getItemViewType() != itemViewType) {
            vhw = new ViewHolderWrapper<>(mOrig.onCreateViewHolder(parent, itemViewType));
            vhw.getOrig().itemView.setTag(VIEW_HOLDER, vhw);
        }
        vhw.setPosition(position);
        vhw.setItemViewType(itemViewType);

        mOrig.onBindViewHolder(vhw.getOrig(), position);
        return vhw.getOrig().itemView;
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

    public boolean isRecyclingDisabled() {
        return mRecyclingDisabled;
    }

    public void setRecyclingDisabled(boolean recyclingDisabled) {
        mRecyclingDisabled = recyclingDisabled;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {
        super.registerDataSetObserver(observer);
        AdapterDataObserver adapterDataObserver = mAdapterDataObservers.get(observer);
        if (adapterDataObserver == null) {
            adapterDataObserver = new AdapterDataObserver(observer);
            mOrig.registerAdapterDataObserver(adapterDataObserver);
            mAdapterDataObservers.put(observer, adapterDataObserver);
        }
    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {
        super.unregisterDataSetObserver(observer);
        AdapterDataObserver adapterDataObserver = mAdapterDataObservers.remove(observer);
        if (adapterDataObserver != null) {
            mOrig.unregisterAdapterDataObserver(adapterDataObserver);
        }
    }

    public static class ViewHolderWrapper<VH extends RecyclerView.ViewHolder> {
        VH orig;
        private int position;
        private int itemViewType;

        public ViewHolderWrapper(VH orig) {
            this.orig = orig;
        }

        public VH getOrig() {
            return orig;
        }

        public int getPosition() {
            return position;
        }

        void setPosition(int position) {
            this.position = position;
        }

        public int getItemViewType() {
            return itemViewType;
        }

        public void setItemViewType(int itemViewType) {
            this.itemViewType = itemViewType;
        }
    }

    private static class AdapterDataObserver extends RecyclerView.AdapterDataObserver {
        DataSetObserver observer;

        public AdapterDataObserver(DataSetObserver observer) {
            this.observer = observer;
        }

        @Override
        public void onChanged() {
            super.onChanged();
            observer.onChanged();
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount) {
            super.onItemRangeChanged(positionStart, itemCount);
            observer.onChanged();
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount, Object payload) {
            super.onItemRangeChanged(positionStart, itemCount, payload);
            observer.onChanged();
        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            super.onItemRangeInserted(positionStart, itemCount);
            observer.onChanged();
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            super.onItemRangeRemoved(positionStart, itemCount);
            observer.onChanged();
        }

        @Override
        public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
            super.onItemRangeMoved(fromPosition, toPosition, itemCount);
            observer.onChanged();
        }
    }
}
