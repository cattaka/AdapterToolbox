package net.cattaka.android.adaptertoolbox.example.adapter.factory;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.cattaka.android.adaptertoolbox.adapter.ScrambleAdapter;
import net.cattaka.android.adaptertoolbox.adapter.listener.ForwardingListener;
import net.cattaka.android.adaptertoolbox.example.R;
import net.cattaka.android.adaptertoolbox.view.IFastScrollViewHolder;

/**
 * Created by cattaka on 16/05/02.
 */
public class FastScrollStringViewHolderFactory extends ScrambleAdapter.AbsViewHolderFactory<FastScrollStringViewHolderFactory.ViewHolder> {

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ScrambleAdapter<?> adapter, @NonNull ViewGroup parent, @NonNull ForwardingListener<ScrambleAdapter<?>, RecyclerView.ViewHolder> forwardingListener) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fast_scroll_string, parent, false);
        ViewHolder vh = new ViewHolder(view);
        view.setOnClickListener(forwardingListener);
        view.setOnLongClickListener(forwardingListener);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ScrambleAdapter adapter, @NonNull ViewHolder holder, int position, Object object) {
        String item = (String) object;

        String str = "String = " + item;
        holder.text.setText(str);

        holder.indexLabel = (item != null && item.length() > 0) ? item.substring(0, 1) : "";
    }

    @Override
    public boolean isAssignable(Object object) {
        return object instanceof String;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements IFastScrollViewHolder {
        TextView text;
        String indexLabel;

        public ViewHolder(View itemView) {
            super(itemView);
            text = (TextView) itemView.findViewById(R.id.text);
        }

        @Override
        public void updateIndexLabelView(View view) {
            View textView = view.findViewById(android.R.id.text1);
            if (textView instanceof TextView) {
                ((TextView) textView).setText(indexLabel);
            }
        }
    }
}
