package net.cattaka.android.adaptertoolbox.example.spinner.factory;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.cattaka.android.adaptertoolbox.adapter.ScrambleAdapter;
import net.cattaka.android.adaptertoolbox.adapter.listener.ForwardingListener;
import net.cattaka.android.adaptertoolbox.example.R;
import net.cattaka.android.adaptertoolbox.example.data.HeaderInfo;

/**
 * Created by cattaka on 16/05/02.
 */
public class SpinnerHeaderInfoViewHolderFactory extends ScrambleAdapter.AbsViewHolderFactory<SpinnerHeaderInfoViewHolderFactory.ViewHolder> {

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ScrambleAdapter<?> adapter, @NonNull ViewGroup parent, @NonNull ForwardingListener<ScrambleAdapter<?>, RecyclerView.ViewHolder> forwardingListener) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_spinner_header, parent, false);
        ViewHolder vh = new ViewHolder(view);
        view.setOnClickListener(forwardingListener);
        view.setOnLongClickListener(forwardingListener);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ScrambleAdapter adapter, @NonNull ViewHolder holder, int position, Object object) {
        HeaderInfo item = (HeaderInfo) object;

        String str = item.getText();
        holder.text.setText(str);
    }

    @Override
    public boolean isAssignable(Object object) {
        return object instanceof HeaderInfo;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView text;

        public ViewHolder(View itemView) {
            super(itemView);
            text = (TextView) itemView.findViewById(R.id.text);
        }
    }
}
