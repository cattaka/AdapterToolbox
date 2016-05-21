package net.cattaka.android.snippets.example.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.cattaka.android.snippets.adapter.AdapterConverter;
import net.cattaka.android.snippets.adapter.CustomRecyclerAdapter;
import net.cattaka.android.snippets.adapter.ScrambleAdapter;
import net.cattaka.android.snippets.adapter.listener.ForwardingListener;
import net.cattaka.android.snippets.adapter.listener.ListenerRelay;
import net.cattaka.android.snippets.example.R;
import net.cattaka.android.snippets.example.adapter.factory.ComplexStringViewHolderFactory;

import java.util.List;

/**
 * Created by cattaka on 16/05/02.
 */
public class ComplexStringAdapter extends ScrambleAdapter<String> {
    public ComplexStringAdapter(Context context, List<String> items, ListenerRelay<ScrambleAdapter<?>, RecyclerView.ViewHolder> listenerRelay) {
        super(context, items, listenerRelay, new ComplexStringViewHolderFactory());
    }
}
