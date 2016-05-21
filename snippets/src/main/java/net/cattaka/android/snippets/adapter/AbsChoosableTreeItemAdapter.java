package net.cattaka.android.snippets.adapter;

import android.content.Context;
import android.support.annotation.IntDef;
import android.support.v7.widget.RecyclerView;

import net.cattaka.android.snippets.data.ITreeItem;

import java.util.List;

/**
 * Created by cattaka on 16/05/21.
 */
public abstract class AbsChoosableTreeItemAdapter<VH extends RecyclerView.ViewHolder, T extends ITreeItem<T>, W extends AbsChoosableTreeItemAdapter.WrappedItem<W, T>> extends AbsTreeItemAdapter<VH, T, W> {
    public static final int CHOICE_MODE_MULTIPLE = 2;
    //public static final int CHOICE_MODE_MULTIPLE_MODAL = 3;
    //public static final int CHOICE_MODE_NONE = 0;
    public static final int CHOICE_MODE_SINGLE = 1;

    @IntDef({CHOICE_MODE_MULTIPLE, CHOICE_MODE_SINGLE})
    public @interface ChoiceMode {
    }

    @ChoiceMode
    private int mChoiceMode = CHOICE_MODE_MULTIPLE;

    public <REF extends INestedItemAdapterRef<?, T, W>> AbsChoosableTreeItemAdapter(Context context, List<T> items, REF ref) {
        super(context, items, ref);
    }

    public void toggleCheck(W item) {
        doCheck(item, !item.chosen);
    }

    public void doCheck(W item, boolean checked) {
        if (mChoiceMode == CHOICE_MODE_MULTIPLE) {
            if (!checked) {
                W p = item.parent;
                while (p != null) {
                    p.chosen = checked;
                    notifyItemChanged(getItems().indexOf(p));
                    p = p.parent;
                }
            }
            doCheckInner(item, checked);
        } else {
            for (int i = 0; i < getItemCount(); i++) {
                W w = getItemAt(i);
                if (w == item) {
                    if (!w.chosen) {
                        w.chosen = true;
                        notifyItemChanged(getItems().indexOf(w));
                    }
                } else {
                    if (w.chosen) {
                        w.chosen = false;
                        notifyItemChanged(getItems().indexOf(w));
                    }
                }
            }
        }
    }

    private void doCheckInner(W item, boolean checked) {
        item.chosen = checked;
        for (W child : item.children) {
            child.chosen = checked;
            doCheckInner(child, checked);
        }
        notifyItemChanged(getItems().indexOf(item));
    }

    @ChoiceMode
    public int getChoiceMode() {
        return mChoiceMode;
    }

    public void setChoiceMode(@ChoiceMode int choiceMode) {
        mChoiceMode = choiceMode;
    }

    public static class WrappedItem<W extends WrappedItem<W, T>, T extends ITreeItem<T>> extends AbsTreeItemAdapter.WrappedItem<W, T> {
        public boolean chosen;

        public WrappedItem(int level, T item, W parent) {
            super(level, item, parent);
        }
    }
}
