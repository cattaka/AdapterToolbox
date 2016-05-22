package net.cattaka.android.snippets.example.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.widget.Space;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;

import net.cattaka.android.snippets.adapter.AbsChoosableTreeItemAdapter;
import net.cattaka.android.snippets.adapter.AbsTreeItemAdapter;
import net.cattaka.android.snippets.adapter.listener.ForwardingListener;
import net.cattaka.android.snippets.example.R;
import net.cattaka.android.snippets.example.data.ActivityEntry;

import java.util.List;

/**
 * Created by cattaka on 16/05/21.
 */
public class ActivityEntryAdapter extends AbsChoosableTreeItemAdapter<
        ActivityEntryAdapter.ViewHolder,
        ActivityEntry,
        ActivityEntryAdapter.WrappedItem
        > {
    public static ITreeItemAdapterRef<ViewHolder, ActivityEntry, WrappedItem> REF = new ITreeItemAdapterRef<ViewHolder, ActivityEntry, WrappedItem>() {
        @NonNull
        @Override
        public Class<ActivityEntry> getItemClass() {
            return ActivityEntry.class;
        }

        @NonNull
        @Override
        public AbsTreeItemAdapter<ViewHolder, ActivityEntry, WrappedItem> createAdapter(@NonNull Context context, @NonNull List<ActivityEntry> items) {
            return new ActivityEntryAdapter(context, items);
        }

        @Override
        public WrappedItem createWrappedItem(int level, ActivityEntry item, WrappedItem parent) {
            return new WrappedItem(level, item, parent);
        }
    };

    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            ViewHolder vh = (ViewHolder) view.getTag(ForwardingListener.VIEW_HOLDER);
            int position = vh.getAdapterPosition();
            WrappedItem item = getItemAt(position);
            switch (view.getId()) {
                case R.id.check_opened: {
                    item.opened = !item.opened;
                    doFold(item, item.opened);
                    break;
                }
                default: {
                    toggleCheck(item);
                    break;
                }
            }
        }
    };

    public ActivityEntryAdapter(Context context, List<ActivityEntry> items) {
        super(context, items, REF);
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.item_activity_entry, parent, false);
        ViewHolder holder = new ViewHolder(view);

        holder.openedCheck.setTag(ForwardingListener.VIEW_HOLDER, holder);
        holder.openedCheck.setOnClickListener(mOnClickListener);

        holder.itemView.setTag(ForwardingListener.VIEW_HOLDER, holder);
        holder.itemView.setOnClickListener(getForwardingListener());

        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        WrappedItem wrappedItem = getItemAt(position);
        ActivityEntry item = wrappedItem.getItem();

        {
            ViewGroup.LayoutParams params = holder.itemView.getLayoutParams();
            params.height = wrappedItem.fold ? 0 : ViewGroup.LayoutParams.WRAP_CONTENT;
            holder.itemView.setLayoutParams(params);
        }
        {
            ViewGroup.LayoutParams params = holder.levelSpace.getLayoutParams();
            params.width = wrappedItem.level * getContext().getResources().getDimensionPixelSize(R.dimen.element_spacing_large);
            holder.levelSpace.setLayoutParams(params);
        }
        {
            boolean hasChildren = (wrappedItem.children != null && wrappedItem.children.size() > 0);
            holder.openedCheck.setVisibility(hasChildren ? View.VISIBLE : View.INVISIBLE);
        }

        holder.openedCheck.setChecked(wrappedItem.opened);
        holder.labelText.setText(item.getLabel());
    }

    public static class WrappedItem extends AbsChoosableTreeItemAdapter.WrappedItem<WrappedItem, ActivityEntry> {
        public WrappedItem(int level, ActivityEntry item, WrappedItem parent) {
            super(level, item, parent);
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        Space levelSpace;
        CompoundButton openedCheck;
        TextView labelText;

        public ViewHolder(View itemView) {
            super(itemView);
            levelSpace = (Space) itemView.findViewById(R.id.space_level);
            openedCheck = (CompoundButton) itemView.findViewById(R.id.check_opened);
            labelText = (TextView) itemView.findViewById(R.id.text_label);
        }
    }
}
