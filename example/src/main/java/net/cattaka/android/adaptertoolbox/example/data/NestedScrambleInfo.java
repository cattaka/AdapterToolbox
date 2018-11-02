package net.cattaka.android.adaptertoolbox.example.data;

import androidx.recyclerview.widget.RecyclerView;

import net.cattaka.android.adaptertoolbox.adapter.ScrambleAdapter;
import net.cattaka.android.adaptertoolbox.adapter.listener.ListenerRelay;

import java.util.List;

/**
 * Created by cattaka on 16/05/16.
 */
public class NestedScrambleInfo {
    private List<ScrambleAdapter.AbsViewHolderFactory<? extends RecyclerView.ViewHolder>> viewHolderFactories;
    private ListenerRelay<ScrambleAdapter<?>, RecyclerView.ViewHolder> listenerRelay;
    private List<Object> items;

    public NestedScrambleInfo() {
    }

    public NestedScrambleInfo(List<ScrambleAdapter.AbsViewHolderFactory<? extends RecyclerView.ViewHolder>> viewHolderFactories, ListenerRelay<ScrambleAdapter<?>, RecyclerView.ViewHolder> listenerRelay, List<Object> items) {
        this.viewHolderFactories = viewHolderFactories;
        this.listenerRelay = listenerRelay;
        this.items = items;
    }

    public List<ScrambleAdapter.AbsViewHolderFactory<? extends RecyclerView.ViewHolder>> getViewHolderFactories() {
        return viewHolderFactories;
    }

    public void setViewHolderFactories(List<ScrambleAdapter.AbsViewHolderFactory<? extends RecyclerView.ViewHolder>> viewHolderFactories) {
        this.viewHolderFactories = viewHolderFactories;
    }

    public ListenerRelay<ScrambleAdapter<?>, RecyclerView.ViewHolder> getListenerRelay() {
        return listenerRelay;
    }

    public void setListenerRelay(ListenerRelay<ScrambleAdapter<?>, RecyclerView.ViewHolder> listenerRelay) {
        this.listenerRelay = listenerRelay;
    }

    public List<Object> getItems() {
        return items;
    }

    public void setItems(List<Object> items) {
        this.items = items;
    }
}
