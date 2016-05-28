package net.cattaka.android.adaptertoolbox.adapter.listener;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;

/**
 * Created by cattaka on 2016/05/12.
 */
public interface IForwardingListener<
        A extends RecyclerView.Adapter<? extends VH>,
        VH extends RecyclerView.ViewHolder> {
    void setProvider(@NonNull IProvider<A, VH> provider);

    interface IProvider<A extends RecyclerView.Adapter<? extends VH>, VH extends RecyclerView.ViewHolder> {
        @NonNull
        A getAdapter();

        @Nullable
        RecyclerView getAttachedRecyclerView();
    }
}
