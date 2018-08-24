package net.cattaka.android.adaptertoolbox.example.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.legacy.widget.Space;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;

import net.cattaka.android.adaptertoolbox.adapter.AbsChoosableTreeItemAdapter;
import net.cattaka.android.adaptertoolbox.adapter.listener.IForwardingListener;
import net.cattaka.android.adaptertoolbox.example.R;
import net.cattaka.android.adaptertoolbox.example.data.MyTreeItem;

import java.util.List;

/**
 * Created by cattaka on 16/05/21.
 */
public class ChoosableMyTreeItemAdapter extends AbsChoosableTreeItemAdapter<
        ChoosableMyTreeItemAdapter,
        ChoosableMyTreeItemAdapter.ViewHolder,
        MyTreeItem,
        ChoosableMyTreeItemAdapter.WrappedItem
        > {
    public static ITreeItemAdapterRef<ChoosableMyTreeItemAdapter, ViewHolder, MyTreeItem, WrappedItem> REF = new ITreeItemAdapterRef<ChoosableMyTreeItemAdapter, ViewHolder, MyTreeItem, WrappedItem>() {
        @NonNull
        @Override
        public Class<MyTreeItem> getItemClass() {
            return MyTreeItem.class;
        }

        @NonNull
        @Override
        public ChoosableMyTreeItemAdapter createAdapter(@NonNull Context context, @NonNull List<MyTreeItem> items) {
            return new ChoosableMyTreeItemAdapter(context, items);
        }

        @NonNull
        @Override
        public WrappedItem createWrappedItem(int level, MyTreeItem item, WrappedItem parent) {
            return new WrappedItem(level, item, parent);
        }
    };

    public ChoosableMyTreeItemAdapter(Context context, List<MyTreeItem> items) {
        super(context, items, REF);
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.item_choosable_my_tree_item, parent, false);
        ViewHolder holder = new ViewHolder(view);

        holder.chosenCheck.setOnClickListener(createOnClickListener(parent));
        holder.openedCheck.setOnClickListener(createOnClickListener(parent));

        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        WrappedItem wrappedItem = getItemAt(position);
        MyTreeItem item = wrappedItem.getItem();

        {
            ViewGroup.LayoutParams params = holder.levelSpace.getLayoutParams();
            params.width = wrappedItem.level * getContext().getResources().getDimensionPixelSize(R.dimen.element_spacing_large);
            holder.levelSpace.setLayoutParams(params);
        }
        {
            boolean hasChildren = (wrappedItem.children != null && wrappedItem.children.size() > 0);
            holder.openedCheck.setVisibility(hasChildren ? View.VISIBLE : View.INVISIBLE);
        }

        holder.chosenCheck.setChecked(wrappedItem.isChecked());
        holder.openedCheck.setChecked(wrappedItem.isOpened());
        holder.labelText.setText(item.getText());
    }

    private View.OnClickListener createOnClickListener(View parent) {
        final IForwardingListener.IProvider provider = getProvider(parent);
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RecyclerView recyclerView = provider.getAttachedRecyclerView();
                ViewHolder vh = (ViewHolder) (recyclerView != null ? recyclerView.findContainingViewHolder(view) : null);
                if (vh != null) {
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
            }
        };
    }

    public static class WrappedItem extends AbsChoosableTreeItemAdapter.WrappedItem<WrappedItem, MyTreeItem> {
        public WrappedItem(int level, MyTreeItem item, WrappedItem parent) {
            super(level, item, parent);
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        Space levelSpace;
        CompoundButton chosenCheck;
        CompoundButton openedCheck;
        TextView labelText;

        public ViewHolder(View itemView) {
            super(itemView);
            levelSpace = (Space) itemView.findViewById(R.id.space_level);
            chosenCheck = (CompoundButton) itemView.findViewById(R.id.check_chosen);
            openedCheck = (CompoundButton) itemView.findViewById(R.id.check_opened);
            labelText = (TextView) itemView.findViewById(R.id.text_label);
        }
    }
}
