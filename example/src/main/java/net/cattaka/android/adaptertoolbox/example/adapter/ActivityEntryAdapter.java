package net.cattaka.android.adaptertoolbox.example.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.widget.Space;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;

import net.cattaka.android.adaptertoolbox.adapter.AbsChoosableTreeItemAdapter;
import net.cattaka.android.adaptertoolbox.adapter.listener.IForwardingListener;
import net.cattaka.android.adaptertoolbox.example.R;
import net.cattaka.android.adaptertoolbox.example.data.ActivityEntry;

import java.util.List;

/**
 * Created by cattaka on 16/05/21.
 */
public class ActivityEntryAdapter extends AbsChoosableTreeItemAdapter<
        ActivityEntryAdapter,
        ActivityEntryAdapter.ViewHolder,
        ActivityEntry,
        ActivityEntryAdapter.WrappedItem
        > {
    public static ITreeItemAdapterRef<ActivityEntryAdapter, ViewHolder, ActivityEntry, WrappedItem> REF = new ITreeItemAdapterRef<ActivityEntryAdapter, ViewHolder, ActivityEntry, WrappedItem>() {
        @NonNull
        @Override
        public Class<ActivityEntry> getItemClass() {
            return ActivityEntry.class;
        }

        @NonNull
        @Override
        public ActivityEntryAdapter createAdapter(@NonNull Context context, @NonNull List<ActivityEntry> items) {
            return new ActivityEntryAdapter(context, items);
        }

        @NonNull
        @Override
        public WrappedItem createWrappedItem(int level, ActivityEntry item, WrappedItem parent) {
            return new WrappedItem(level, item, parent);
        }
    };

    public ActivityEntryAdapter(Context context, List<ActivityEntry> items) {
        super(context, items, REF);
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.item_activity_entry, parent, false);
        ViewHolder holder = new ViewHolder(view);

        holder.openedCheck.setOnClickListener(createOnClickListener(parent));

        holder.itemView.setOnClickListener(getForwardingListener(parent));

        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        WrappedItem wrappedItem = getItemAt(position);
        ActivityEntry item = wrappedItem.getItem();

        {
            ViewGroup.LayoutParams params = holder.levelSpace.getLayoutParams();
            params.width = wrappedItem.level * getContext().getResources().getDimensionPixelSize(R.dimen.element_spacing_large);
            holder.levelSpace.setLayoutParams(params);
        }
        {
            boolean hasChildren = (wrappedItem.children != null && wrappedItem.children.size() > 0);
            holder.openedCheck.setVisibility(hasChildren ? View.VISIBLE : View.INVISIBLE);
        }

        holder.openedCheck.setChecked(wrappedItem.isOpened());
        holder.labelText.setText(item.getLabel(holder.itemView.getResources()));
    }

    private View.OnClickListener createOnClickListener(View parent) {
        final IForwardingListener.IProvider<ActivityEntryAdapter, ViewHolder> listenerRelay = getProvider(parent);
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RecyclerView recyclerView = listenerRelay.getAttachedRecyclerView();
                ViewHolder vh = (ViewHolder) recyclerView.findContainingViewHolder(view);
                int position = vh.getAdapterPosition();
                WrappedItem item = getItemAt(position);
                switch (view.getId()) {
                    case R.id.check_opened: {
                        doOpen(item, !item.isOpened());
                        break;
                    }
                    default: {
                        toggleCheck(item);
                        break;
                    }
                }
            }
        };
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
