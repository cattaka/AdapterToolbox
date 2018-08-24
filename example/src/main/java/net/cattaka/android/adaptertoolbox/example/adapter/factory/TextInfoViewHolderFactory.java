package net.cattaka.android.adaptertoolbox.example.adapter.factory;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import net.cattaka.android.adaptertoolbox.adapter.ScrambleAdapter;
import net.cattaka.android.adaptertoolbox.adapter.listener.ForwardingListener;
import net.cattaka.android.adaptertoolbox.example.R;
import net.cattaka.android.adaptertoolbox.example.data.TextInfo;

/**
 * Created by cattaka on 16/05/15.
 */
public class TextInfoViewHolderFactory extends ScrambleAdapter.AbsViewHolderFactory<TextInfoViewHolderFactory.ViewHolder> {
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ScrambleAdapter<?> adapter, @NonNull ViewGroup parent, @NonNull ForwardingListener<ScrambleAdapter<?>, RecyclerView.ViewHolder> forwardingListener) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_text_info, parent, false);
        ViewHolder vh = new ViewHolder(view);

        vh.editText.setOnEditorActionListener(forwardingListener);
        forwardingListener.addTextChangedListener(vh.editText);
        vh.goButton.setOnClickListener(forwardingListener);

        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ScrambleAdapter adapter, @NonNull TextInfoViewHolderFactory.ViewHolder holder, int position, Object object) {
        TextInfo item = (TextInfo) object;

        holder.editText.setText(item.getText());
    }

    @Override
    public boolean isAssignable(Object object) {
        return object instanceof TextInfo;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final EditText editText;
        public final View goButton;

        public ViewHolder(View itemView) {
            super(itemView);
            editText = (EditText) itemView.findViewById(R.id.edit_text);
            goButton = itemView.findViewById(R.id.button_go);
        }
    }
}
