package net.cattaka.android.adaptertoolbox.example.adapter.factory;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.cattaka.android.adaptertoolbox.adapter.ScrambleAdapter;
import net.cattaka.android.adaptertoolbox.adapter.listener.ForwardingListener;
import net.cattaka.android.adaptertoolbox.example.R;

/**
 * Created by cattaka on 16/05/02.
 */
public class ComplexStringViewHolderFactory extends ScrambleAdapter.AbsViewHolderFactory<ComplexStringViewHolderFactory.ViewHolder> {
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ScrambleAdapter<?> adapter, @NonNull ViewGroup parent, @NonNull ForwardingListener<ScrambleAdapter<?>, RecyclerView.ViewHolder> forwardingListener) {
        View view = LayoutInflater.from(adapter.getContext()).inflate(R.layout.item_complex_string, parent, false);
        ViewHolder vh = new ViewHolder(view);
        vh.text.setOnClickListener(forwardingListener);
        vh.text.setOnLongClickListener(forwardingListener);
        vh.aButton.setOnClickListener(forwardingListener);
        vh.aButton.setOnLongClickListener(forwardingListener);
        vh.bButton.setOnClickListener(forwardingListener);
        vh.bButton.setOnLongClickListener(forwardingListener);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ScrambleAdapter adapter, @NonNull ViewHolder holder, int position, Object object) {
        String item = (String) object;

        holder.text.setText(item);
    }

    @Override
    public boolean isAssignable(Object object) {
        return object instanceof String;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView text;
        View aButton;
        View bButton;

        public ViewHolder(View itemView) {
            super(itemView);
            text = (TextView) itemView.findViewById(R.id.text);
            aButton = itemView.findViewById(R.id.button_a);
            bButton = itemView.findViewById(R.id.button_b);
        }
    }
}
