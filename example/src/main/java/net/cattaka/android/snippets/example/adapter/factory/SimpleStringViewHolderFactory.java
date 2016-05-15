package net.cattaka.android.snippets.example.adapter.factory;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.cattaka.android.snippets.adapter.AdapterConverter;
import net.cattaka.android.snippets.adapter.ScrambleAdapter;
import net.cattaka.android.snippets.adapter.listener.ForwardingListener;
import net.cattaka.android.snippets.example.R;

/**
 * Created by cattaka on 16/05/02.
 */
public class SimpleStringViewHolderFactory extends ScrambleAdapter.AbsViewHolderFactory<SimpleStringViewHolderFactory.ViewHolder> {
    @Override
    public ViewHolder onCreateViewHolder(ScrambleAdapter adapter, ViewGroup parent, ForwardingListener forwardingListener) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_simple_string, parent, false);
        ViewHolder vh = new ViewHolder(view);
        view.setTag(ForwardingListener.VIEW_HOLDER, vh);
        view.setOnClickListener(forwardingListener);
        view.setOnLongClickListener(forwardingListener);
        return vh;
    }

    @Override
    public void onBindViewHolder(ScrambleAdapter adapter, ViewHolder holder, int position, Object object) {
        String item = (String) object;

        String str = "String = " + item;
        holder.text.setText(str);
    }

    @Override
    public boolean isAssignable(Object object) {
        return object instanceof String;
    }

    public static class ViewHolder extends AdapterConverter.ViewHolder {
        TextView text;

        public ViewHolder(View itemView) {
            super(itemView);
            text = (TextView) itemView.findViewById(R.id.text);
        }
    }
}
