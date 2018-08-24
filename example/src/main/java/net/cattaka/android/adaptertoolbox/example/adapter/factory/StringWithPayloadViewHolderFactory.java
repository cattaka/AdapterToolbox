package net.cattaka.android.adaptertoolbox.example.adapter.factory;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.cattaka.android.adaptertoolbox.adapter.ScrambleAdapter;
import net.cattaka.android.adaptertoolbox.adapter.listener.ForwardingListener;
import net.cattaka.android.adaptertoolbox.example.R;

import java.util.List;

/**
 * Created by cattaka on 16/05/02.
 */
public class StringWithPayloadViewHolderFactory extends ScrambleAdapter.AbsViewHolderFactory<StringWithPayloadViewHolderFactory.ViewHolder> {

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ScrambleAdapter<?> adapter, @NonNull ViewGroup parent, @NonNull ForwardingListener<ScrambleAdapter<?>, RecyclerView.ViewHolder> forwardingListener) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_string_with_payload, parent, false);
        ViewHolder vh = new ViewHolder(view);
        view.findViewById(R.id.button_none).setOnClickListener(forwardingListener);
        view.findViewById(R.id.button_red).setOnClickListener(forwardingListener);
        view.findViewById(R.id.button_blue).setOnClickListener(forwardingListener);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ScrambleAdapter<?> adapter, @NonNull ViewHolder holder, int position, @Nullable Object object, List<Object> payloads) {
        String item = (String) object;

        String str = "String = " + item;
        holder.text.setText(str);

        StringBuilder sb = new StringBuilder();
        if (payloads != null) {
            for (Object payload : payloads) {
                if (sb.length() > 0) {
                    sb.append(',');
                }
                sb.append(String.valueOf(payload));
            }
        }
        holder.payloadText.setText(sb.toString());
    }

    @Override
    public boolean isAssignable(Object object) {
        return object instanceof String;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView text;
        TextView payloadText;

        public ViewHolder(View itemView) {
            super(itemView);
            text = (TextView) itemView.findViewById(R.id.text);
            payloadText = (TextView) itemView.findViewById(R.id.text_payload);
        }
    }
}
