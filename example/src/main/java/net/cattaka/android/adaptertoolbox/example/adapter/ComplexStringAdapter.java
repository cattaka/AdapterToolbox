package net.cattaka.android.adaptertoolbox.example.adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;

import net.cattaka.android.adaptertoolbox.adapter.ScrambleAdapter;
import net.cattaka.android.adaptertoolbox.adapter.listener.ListenerRelay;
import net.cattaka.android.adaptertoolbox.example.adapter.factory.ComplexStringViewHolderFactory;

import java.util.List;

/**
 * Created by cattaka on 16/05/02.
 */
public class ComplexStringAdapter extends ScrambleAdapter<String> {
    public ComplexStringAdapter(Context context, List<String> items, ListenerRelay<ScrambleAdapter<?>, RecyclerView.ViewHolder> listenerRelay) {
        super(context, items, listenerRelay, new ComplexStringViewHolderFactory());
    }
}
