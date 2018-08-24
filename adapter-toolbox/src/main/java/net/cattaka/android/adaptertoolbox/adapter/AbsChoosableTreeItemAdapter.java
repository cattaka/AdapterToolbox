package net.cattaka.android.adaptertoolbox.adapter;

import android.content.Context;
import androidx.annotation.IntDef;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import net.cattaka.android.adaptertoolbox.data.ITreeItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cattaka on 16/05/21.
 */
public abstract class AbsChoosableTreeItemAdapter<
        A extends AbsChoosableTreeItemAdapter<A, VH, T, W>,
        VH extends RecyclerView.ViewHolder,
        T extends ITreeItem<T>,
        W extends AbsChoosableTreeItemAdapter.WrappedItem<W, T>
        > extends AbsTreeItemAdapter<
        A,
        VH,
        T,
        W
        > {
    public static final int CHOICE_MODE_MULTIPLE = 2;
    //public static final int CHOICE_MODE_MULTIPLE_MODAL = 3;
    //public static final int CHOICE_MODE_NONE = 0;
    public static final int CHOICE_MODE_SINGLE = 1;

    @IntDef({CHOICE_MODE_MULTIPLE, CHOICE_MODE_SINGLE})
    public @interface ChoiceMode {
    }

    @ChoiceMode
    private int mChoiceMode = CHOICE_MODE_MULTIPLE;

    public <REF extends ITreeItemAdapterRef<A, ?, T, W>> AbsChoosableTreeItemAdapter(Context context, List<T> items, REF ref) {
        super(context, items, ref);
    }

    public void toggleCheck(@NonNull W item) {
        doCheck(item, !item.isChecked());
    }

    public void doCheck(@NonNull W item, boolean checked) {
        if (mChoiceMode == CHOICE_MODE_MULTIPLE) {
            if (!checked) {
                W p = item.parent;
                while (p != null) {
                    p.setChecked(checked);
                    notifyItemChanged(getItems().indexOf(p));
                    p = p.parent;
                }
            }
            doCheckInner(item, checked);
        } else {
            for (int i = 0; i < getItemCount(); i++) {
                W w = getItemAt(i);
                if (w == item) {
                    if (!w.isChecked()) {
                        notifyItemChanged(getItems().indexOf(w));
                    }
                } else {
                    if (w.isChecked()) {
                        notifyItemChanged(getItems().indexOf(w));
                    }
                }
            }
            for (int i = 0; i < getItemCount(); i++) {
                W w = getItemAt(i);
                if (w.level == 0) {
                    uncheckAll(w);
                }
            }
            item.setChecked(checked);
        }
    }

    private void doCheckInner(@NonNull W item, boolean checked) {
        item.setChecked(checked);
        for (W child : item.children) {
            child.setChecked(checked);
            doCheckInner(child, checked);
        }
        notifyItemChanged(getItems().indexOf(item));
    }

    private void uncheckAll(@NonNull W item) {
        item.setChecked(false);
        if (item.children != null) {
            for (W child : item.children) {
                uncheckAll(child);
            }
        }
    }

    @ChoiceMode
    public int getChoiceMode() {
        return mChoiceMode;
    }

    public void setChoiceMode(@ChoiceMode int choiceMode) {
        mChoiceMode = choiceMode;
    }

    public List<T> getCheckedItems() {
        List<T> checkedItems = new ArrayList<>();
        for (W witem : getItems()) {
            if (witem.level == 0) {
                collectCheckedItems(checkedItems, witem);
            }
        }
        return checkedItems;
    }

    private void collectCheckedItems(List<T> dest, W witem) {
        if (witem == null) {
            return;
        }
        if (witem.isChecked()) {
            dest.add(witem.getItem());
        }
        if (witem.children != null) {
            for (W child : witem.children) {
                collectCheckedItems(dest, child);
            }
        }
    }

    public static class WrappedItem<W extends WrappedItem<W, T>, T extends ITreeItem<T>> extends AbsTreeItemAdapter.WrappedItem<W, T> {
        private boolean checked;

        public WrappedItem(int level, T item, W parent) {
            super(level, item, parent);
        }

        public boolean isChecked() {
            return checked;
        }

        void setChecked(boolean checked) {
            this.checked = checked;
        }
    }
}
