package net.cattaka.android.adaptertoolbox.example.spinner;

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
import net.cattaka.android.adaptertoolbox.adapter.AbsTreeItemAdapter;
import net.cattaka.android.adaptertoolbox.classic.AdapterConverter;
import net.cattaka.android.adaptertoolbox.example.R;
import net.cattaka.android.adaptertoolbox.example.data.MyTreeItem;

import java.util.List;

/**
 * Created by cattaka on 16/05/21.
 */
public class SpinnerMyTreeItemAdapter extends AbsTreeItemAdapter<
        SpinnerMyTreeItemAdapter,
        SpinnerMyTreeItemAdapter.ViewHolder,
        MyTreeItem,
        SpinnerMyTreeItemAdapter.WrappedItem
        > {
    public static ITreeItemAdapterRef<SpinnerMyTreeItemAdapter, ViewHolder, MyTreeItem, WrappedItem> REF = new ITreeItemAdapterRef<SpinnerMyTreeItemAdapter, ViewHolder, MyTreeItem, WrappedItem>() {
        @NonNull
        @Override
        public Class<MyTreeItem> getItemClass() {
            return MyTreeItem.class;
        }

        @NonNull
        @Override
        public SpinnerMyTreeItemAdapter createAdapter(@NonNull Context context, @NonNull List<MyTreeItem> items) {
            return new SpinnerMyTreeItemAdapter(context, items);
        }

        @NonNull
        @Override
        public WrappedItem createWrappedItem(int level, MyTreeItem item, WrappedItem parent) {
            return new WrappedItem(level, item, parent);
        }
    };

    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            ViewHolder vh = (ViewHolder) view.getTag(AdapterConverter.VIEW_HOLDER);
            if (vh != null) {
                WrappedItem item = getItemAt(vh.position);
                switch (view.getId()) {
                    case R.id.check_opened: {
                        doOpen(item, !item.isOpened());
                        break;
                    }
                    default: {
                        break;
                    }
                }
            }
        }
    };

    public SpinnerMyTreeItemAdapter(Context context, List<MyTreeItem> items) {
        super(context, items, REF);
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.item_spinner_my_tree_item, parent, false);
        ViewHolder holder = new ViewHolder(view);

        holder.openedCheck.setTag(AdapterConverter.VIEW_HOLDER, holder);
        holder.openedCheck.setOnClickListener(mOnClickListener);

        holder.itemView.setOnClickListener(getForwardingListener());

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

        holder.openedCheck.setChecked(wrappedItem.isOpened());
        holder.labelText.setText(item.getText());

        // Because of holder.getAdapterPosition() is only for RecyclerView. So it keep position by itself.
        holder.position = position;
    }

    public static class WrappedItem extends AbsChoosableTreeItemAdapter.WrappedItem<WrappedItem, MyTreeItem> {
        public WrappedItem(int level, MyTreeItem item, WrappedItem parent) {
            super(level, item, parent);
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        Space levelSpace;
        CompoundButton openedCheck;
        TextView labelText;
        int position;

        public ViewHolder(View itemView) {
            super(itemView);
            levelSpace = (Space) itemView.findViewById(R.id.space_level);
            openedCheck = (CompoundButton) itemView.findViewById(R.id.check_opened);
            labelText = (TextView) itemView.findViewById(R.id.text_label);
        }
    }
}
