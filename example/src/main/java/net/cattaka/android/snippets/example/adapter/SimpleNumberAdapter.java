package net.cattaka.android.snippets.example.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import net.cattaka.android.snippets.adapter.ScrambleAdapter;
import net.cattaka.android.snippets.adapter.listener.ListenerRelay;
import net.cattaka.android.snippets.example.adapter.factory.SimpleNumberViewHolderFactory;

import java.util.List;

/**
 * Created by cattaka on 16/05/02.
 */
public class SimpleNumberAdapter extends ScrambleAdapter<Number> {
    public SimpleNumberAdapter(Context context, List<Number> items, ListenerRelay<ScrambleAdapter<?>, RecyclerView.ViewHolder> listenerRelay) {
        super(context, items, listenerRelay, new SimpleNumberViewHolderFactory());
    }
}
