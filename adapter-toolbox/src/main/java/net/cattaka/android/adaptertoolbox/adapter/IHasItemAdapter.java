package net.cattaka.android.adaptertoolbox.adapter;

import androidx.recyclerview.widget.RecyclerView;
import android.view.ViewGroup;

/**
 * Created by cattaka on 16/05/26.
 */
public interface IHasItemAdapter<VH extends RecyclerView.ViewHolder, T> {
    VH onCreateViewHolder(ViewGroup parent, int viewType);

    void onBindViewHolder(VH holder, int position);

    int getItemViewType(int position);

    int getViewTypeCount();

    int getItemCount();

    T getItemAt(int position);

    void registerAdapterDataObserver(RecyclerView.AdapterDataObserver observer);

    void unregisterAdapterDataObserver(RecyclerView.AdapterDataObserver observer);
}
