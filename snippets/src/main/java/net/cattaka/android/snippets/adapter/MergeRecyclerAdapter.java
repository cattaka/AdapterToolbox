package net.cattaka.android.snippets.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * forked from https://gist.github.com/athornz/008edacd1d3b2f1e1836
 * <p/>
 * The MergeRecyclerAdapter is inspired by the cwac-merge adapter, but for
 * the RecyclerView.
 * <p/>
 * This adapter essentially merges a collection of adapters/views into one adapter.
 * <p/>
 * Methods are provided for adding single views or a list of views, but under the hood
 * these views are then put into an adapter themselves, even if the adapter only holds the
 * one view.
 * <p/>
 * RecyclerView Adapters must implement both the {@link android.support.v7.widget.RecyclerView.Adapter#onCreateViewHolder(android.view.ViewGroup, int)}
 * and {@link android.support.v7.widget.RecyclerView.Adapter#onBindViewHolder(android.support.v7.widget.RecyclerView.ViewHolder,
 * int)} methods, as opposed to just the {@link android.widget.BaseAdapter#getView(int, android.view.View,
 * android.view.ViewGroup)} method in a ListView Adapter.
 * <p/>
 * Because the {@link android.support.v7.widget.RecyclerView.Adapter#onCreateViewHolder(android.view.ViewGroup, int)} method only provides us the ViewGroup and a View Type, we must keep a mapping of
 * (unique) view types in this merge adapter to each sub adapter, so we know which adapters' onCreateViewHolder method to call.
 * <p/>
 * The {@link MergeRecyclerAdapter.LocalAdapter} class
 * is used to maintain this mapping, and the {@link MergeRecyclerAdapter#mViewTypeIndex} field is used to provide a unique
 * view type.
 * <p/>
 * <p/>
 * -------------
 * BUGS
 * -------------
 * <p/>
 * There currently exists a bug when merging Views and Adapters. The RecyclerView throws the following error:
 * <p/>
 * java.lang.IllegalArgumentException: Scrapped or attached views may not be recycled.
 * at android.support.v7.widget.RecyclerView$Recycler.recycleViewHolder(RecyclerView.java:2569)
 * at android.support.v7.widget.RecyclerView$Recycler.quickRecycleScrapView(RecyclerView.java:2610)
 * at android.support.v7.widget.RecyclerView$LayoutManager.removeAndRecycleScrapInt(RecyclerView.java:3992)
 * at android.support.v7.widget.RecyclerView.dispatchLayout(RecyclerView.java:1533)
 * at android.support.v7.widget.RecyclerView.onLayout(RecyclerView.java:1600)
 * at android.view.View.layout(View.java:15273)
 * <p/>
 * <p/>
 * <p/>
 * I currently don't know what the fix is for this issue.
 */
public class MergeRecyclerAdapter<T extends RecyclerView.Adapter> extends RecyclerView
        .Adapter {

    private Context mContext;
    protected ArrayList<LocalAdapter> mAdapters = new ArrayList<>();
    private int mViewTypeIndex = 0;

    public MergeRecyclerAdapter() {
    }

    public MergeRecyclerAdapter(Context context) {
        this.mContext = context;
    }

    /**
     * A Mergeable adapter must implement both ListAdapter and SpinnerAdapter to be useful in lists and spinners.
     */

    public class LocalAdapter extends RecyclerView.AdapterDataObserver {
        public final T mAdapter;
        public int mLocalPosition = 0;
        public Map<Integer, Integer> mViewTypesMap = new HashMap<>();

        public LocalAdapter(T adapter) {
            mAdapter = adapter;
        }

        @Override
        public void onChanged() {
            notifyDataSetChanged();
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount) {
            super.onItemRangeChanged(positionStart, itemCount);
            int offset = getAdapterOffsetForAdapter(this);
            notifyItemRangeChanged(offset + positionStart, itemCount);
        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            super.onItemRangeInserted(positionStart, itemCount);
            int offset = getAdapterOffsetForAdapter(this);
            notifyItemRangeInserted(offset + positionStart, itemCount);
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            super.onItemRangeRemoved(positionStart, itemCount);
            int offset = getAdapterOffsetForAdapter(this);
            notifyItemRangeRemoved(offset + positionStart, itemCount);
        }
    }

    /**
     * Append the given adapter to the list of merged adapters.
     */
    public void addAdapter(T adapter) {
        addAdapter(mAdapters.size(), adapter);
    }

    /**
     * Add the given adapter to the list of merged adapters at the given index.
     */
    public void addAdapter(int index, T adapter) {
        LocalAdapter la = new LocalAdapter(adapter);
        mAdapters.add(index, la);
        adapter.registerAdapterDataObserver(la);
        notifyDataSetChanged();
    }

	/* - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
     * remove adapter
	 * - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - */

    /**
     * Remove the given adapter from the list of merged adapters.
     */
    public void removeAdapter(T adapter) {
        if (!mAdapters.contains(adapter)) return;
        removeAdapter(mAdapters.indexOf(adapter));
    }

    /**
     * Remove the adapter at the given index from the list of merged adapters.
     */
    public void removeAdapter(int index) {
        if (index < 0 || index >= mAdapters.size()) return;
        LocalAdapter adapter = mAdapters.remove(index);
        adapter.mAdapter.unregisterAdapterDataObserver(adapter);
        notifyDataSetChanged();
    }

    public int getSubAdapterCount() {
        return mAdapters.size();
    }

    public T getSubAdapter(int index) {
        return mAdapters.get(index).mAdapter;
    }

    /**
     * Adds a new View to the roster of things to appear in
     * the aggregate list.
     *
     * @param view Single view to add
     */
    public void addView(View view) {
        ArrayList<View> list = new ArrayList<View>(1);

        list.add(view);

        addViews(list);
    }

    /**
     * Adds a list of views to the roster of things to appear
     * in the aggregate list.
     *
     * @param views List of views to add
     */
    public void addViews(List<View> views) {
        addAdapter((T) new ViewsAdapter(mContext, views));
    }

    @Override
    public int getItemCount() {
        int count = 0;
        for (LocalAdapter adapter : mAdapters) {
            count += adapter.mAdapter.getItemCount();
        }
        return count;
        // TODO: cache counts until next onChanged
    }

    /**
     * For a given merged position, find the corresponding Adapter and local position within that Adapter by iterating through Adapters and
     * summing their counts until the merged position is found.
     *
     * @param position a merged (global) position
     * @return the matching Adapter and local position, or null if not found
     */
    public LocalAdapter getAdapterOffsetForItem(final int position) {
        final int adapterCount = mAdapters.size();
        int i = 0;
        int count = 0;

        while (i < adapterCount) {
            LocalAdapter a = mAdapters.get(i);
            int newCount = count + a.mAdapter.getItemCount();
            if (position < newCount) {
                a.mLocalPosition = position - count;
                return a;
            }
            count = newCount;
            i++;
        }
        return null;
    }

    private int getAdapterOffsetForAdapter(LocalAdapter t) {
        final int adapterCount = mAdapters.size();
        int i = 0;
        int count = 0;

        while (i < adapterCount) {
            LocalAdapter a = mAdapters.get(i);
            int newCount = count + a.mAdapter.getItemCount();
            if (a == t) {
                return count;
            }
            count = newCount;
            i++;
        }
        return -1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public int getItemViewType(int position) {
        LocalAdapter result = getAdapterOffsetForItem(position);
        int localViewType = result.mAdapter.getItemViewType(result.mLocalPosition);
        if (result.mViewTypesMap.containsValue(localViewType)) {
            for (Map.Entry<Integer, Integer> entry : result.mViewTypesMap.entrySet()) {
                if (entry.getValue() == localViewType) {
                    return entry.getKey();
                }
            }
        }
        mViewTypeIndex += 1;
        result.mViewTypesMap.put(mViewTypeIndex, localViewType);
        return mViewTypeIndex;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        for (LocalAdapter adapter : mAdapters) {
            if (adapter.mViewTypesMap.containsKey(viewType))
                return adapter.mAdapter.onCreateViewHolder(viewGroup, adapter.mViewTypesMap.get(viewType));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        LocalAdapter result = getAdapterOffsetForItem(position);
        result.mAdapter.onBindViewHolder(viewHolder, result.mLocalPosition);
    }

    /**
     * ViewsAdapter, ported from CommonsWare SackOfViews adapter.
     */
    public static class ViewsAdapter extends RecyclerView.Adapter {
        private List<View> views = null;
        private Context context;

        /**
         * Constructor creating an empty list of views, but with
         * a specified count. Subclasses must override newView().
         */
        public ViewsAdapter(Context context, int count) {
            super();
            this.context = context;

            views = new ArrayList<>(count);

            for (int i = 0; i < count; i++) {
                views.add(null);
            }
        }

        /**
         * Constructor wrapping a supplied list of views.
         * Subclasses must override newView() if any of the elements
         * in the list are null.
         */
        public ViewsAdapter(Context context, List<View> views) {
            super();
            this.context = context;

            this.views = views;
        }

        /**
         * How many items are in the data set represented by this
         * Adapter.
         */
        @Override
        public int getItemCount() {
            return (views.size());
        }

        /**
         * Get the type of View that will be created by getView()
         * for the specified item.
         *
         * @param position Position of the item whose data we want
         */
        @Override
        public int getItemViewType(int position) {
            return (position);
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            //view type is equal to the position in this adapter.
            ViewsViewHolder holder = new ViewsViewHolder(views.get(viewType));
            return holder;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        }

        /**
         * Get the row id associated with the specified position
         * in the list.
         *
         * @param position Position of the item whose data we want
         */
        @Override
        public long getItemId(int position) {
            return (position);
        }

        public boolean hasView(View v) {
            return (views.contains(v));
        }

        /**
         * Create a new View to go into the list at the specified
         * position.
         *
         * @param position Position of the item whose data we want
         * @param parent   ViewGroup containing the returned View
         */
        protected View newView(int position, ViewGroup parent) {
            throw new RuntimeException("You must override newView()!");
        }
    }

    public static class ViewsViewHolder extends RecyclerView.ViewHolder {
        public ViewsViewHolder(View itemView) {
            super(itemView);
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        for (LocalAdapter adapter : mAdapters) {
            adapter.mAdapter.onAttachedToRecyclerView(recyclerView);
        }
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        for (LocalAdapter adapter : mAdapters) {
            adapter.mAdapter.onDetachedFromRecyclerView(recyclerView);
        }
    }
}
