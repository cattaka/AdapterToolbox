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
import net.cattaka.android.snippets.example.data.MyTreeItem;

import java.util.List;

/**
 * Created by cattaka on 16/05/21.
 */
public class ChoosableMyTreeItemAdapter extends AbsChoosableTreeItemAdapter<
        ChoosableMyTreeItemAdapter.ViewHolder,
        MyTreeItem,
        ChoosableMyTreeItemAdapter.WrappedItem
        > {
    public static INestedItemAdapterRef<ViewHolder, MyTreeItem, ChoosableMyTreeItemAdapter.WrappedItem> REF = new INestedItemAdapterRef<ViewHolder, MyTreeItem, WrappedItem>() {
        @NonNull
        @Override
        public Class<MyTreeItem> getItemClass() {
            return MyTreeItem.class;
        }

        @NonNull
        @Override
        public AbsTreeItemAdapter<ViewHolder, MyTreeItem, WrappedItem> createAdapter(@NonNull Context context, @NonNull List<MyTreeItem> items) {
            return new ChoosableMyTreeItemAdapter(context, items);
        }

        @Override
        public WrappedItem createWrappedItem(int level, MyTreeItem item, WrappedItem parent) {
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

    public ChoosableMyTreeItemAdapter(Context context, List<MyTreeItem> items) {
        super(context, items, REF);
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.item_choosable_my_tree_item, parent, false);
        ViewHolder holder = new ViewHolder(view);

        holder.chosenCheck.setTag(ForwardingListener.VIEW_HOLDER, holder);
        holder.chosenCheck.setOnClickListener(mOnClickListener);
        holder.openedCheck.setTag(ForwardingListener.VIEW_HOLDER, holder);
        holder.openedCheck.setOnClickListener(mOnClickListener);

        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        WrappedItem wrappedItem = getItemAt(position);
        MyTreeItem item = wrappedItem.getItem();

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

        holder.chosenCheck.setChecked(wrappedItem.chosen);
        holder.openedCheck.setChecked(wrappedItem.opened);
        holder.labelText.setText(item.getText());
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
