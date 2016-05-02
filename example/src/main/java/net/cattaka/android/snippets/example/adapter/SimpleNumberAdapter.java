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
public class SimpleNumberAdapter extends CustomRecyclerAdapter<SimpleNumberAdapter.ViewHolder, Number> {
    Context mContext;
    List<Number> mItems;

    public SimpleNumberAdapter(Context mContext, List<Number> mItems) {
        this.mContext = mContext;
        this.mItems = mItems;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_simple_number, parent, false);
        ViewHolder vh = new ViewHolder(view);
        view.setTag(VIEW_HOLDER, vh);
        view.setOnClickListener(this);
        view.setOnLongClickListener(this);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Number item = mItems.get(position);

        String str = "Number = " + item;
        holder.text.setText(str);
    }

    @Override
    public Number getItemAt(int position) {
        return mItems.get(position);
    }

    @Override
    public List<Number> getItems() {
        return mItems;
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public static class ViewHolder extends AdapterConverter.ViewHolder {
        TextView text;

        public ViewHolder(View itemView) {
            super(itemView);
            text = (TextView) itemView.findViewById(R.id.text);
        }
    }
}
