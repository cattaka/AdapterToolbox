package net.cattaka.android.snippets.example.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.cattaka.android.snippets.adapter.AdapterConverter;
import net.cattaka.android.snippets.adapter.CustomRecyclerAdapter;
import net.cattaka.android.snippets.example.R;

import java.util.List;

/**
 * Created by cattaka on 16/05/02.
 */
public class ComplexStringAdapter extends CustomRecyclerAdapter<ComplexStringAdapter.ViewHolder, String> {
    Context mContext;
    List<String> mItems;

    public ComplexStringAdapter(Context mContext, List<String> mItems) {
        this.mContext = mContext;
        this.mItems = mItems;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_complex_string, parent, false);
        ViewHolder vh = new ViewHolder(view);
        vh.text.setTag(VIEW_HOLDER, vh);
        vh.text.setOnClickListener(getForwardingListener());
        vh.text.setOnLongClickListener(getForwardingListener());
        vh.aButton.setTag(VIEW_HOLDER, vh);
        vh.aButton.setOnClickListener(getForwardingListener());
        vh.aButton.setOnLongClickListener(getForwardingListener());
        vh.bButton.setTag(VIEW_HOLDER, vh);
        vh.bButton.setOnClickListener(getForwardingListener());
        vh.bButton.setOnLongClickListener(getForwardingListener());
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String item = mItems.get(position);

        holder.text.setText(item);
    }

    @Override
    public String getItemAt(int position) {
        return mItems.get(position);
    }

    @Override
    public List<String> getItems() {
        return mItems;
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public static class ViewHolder extends AdapterConverter.ViewHolder {
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
