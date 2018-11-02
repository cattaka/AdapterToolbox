package net.cattaka.android.adaptertoolbox.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import net.cattaka.android.adaptertoolbox.adapter.listener.ForwardingListener;
import net.cattaka.android.adaptertoolbox.adapter.listener.ListenerRelay;
import net.cattaka.android.adaptertoolbox.data.ITreeItem;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by cattaka on 16/05/21.
 */
public abstract class AbsTreeItemAdapter<
        A extends AbsTreeItemAdapter<A, VH, T, W>,
        VH extends RecyclerView.ViewHolder,
        T extends ITreeItem<T>,
        W extends AbsTreeItemAdapter.WrappedItem<W, T>
        > extends AbsCustomRecyclerAdapter<
        A,
        VH,
        W,
        ForwardingListener<A, VH>
        > {
    private Context mContext;
    private List<W> mItems;

    private ListenerRelay<A, VH> mListenerRelay;

    @NonNull
    protected static <T extends ITreeItem<T>, W extends AbsTreeItemAdapter.WrappedItem<W, T>, REF extends ITreeItemAdapterRef<?, ?, T, W>>
    List<W> inflateWrappedList(@NonNull List<W> dest, @NonNull List<T> items, int level, @Nullable W parent, @NonNull REF ref) {
        for (T item : items) {
            W child = ref.createWrappedItem(level, item, parent);
            child.setOpened(true);
            dest.add(child);
            if (parent != null) {
                parent.children.add(child);
            }
            if (item.getChildren() != null) {
                inflateWrappedList(dest, item.getChildren(), level + 1, child, ref);
            }
        }
        return dest;
    }

    public <REF extends ITreeItemAdapterRef<A, ?, T, W>> AbsTreeItemAdapter(@NonNull Context context, @NonNull List<T> items, @NonNull REF ref) {
        super();
        mContext = context;
        mItems = inflateWrappedList(new ArrayList<W>(), items, 0, null, ref);
    }

    @NonNull
    public Context getContext() {
        return mContext;
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    @NonNull
    @Override
    public W getItemAt(int position) {
        return mItems.get(position);
    }

    @NonNull
    @Override
    public List<W> getItems() {
        return mItems;
    }

    public void doOpen(W item, boolean opened) {
        if (item.isOpened() != opened) {
            item.setOpened(opened);
            List<W> children = new ArrayList<>();
            flattenChildren(children, item);
            if (opened) {
                int p = mItems.indexOf(item);
                mItems.addAll(p + 1, children);
                notifyItemRangeInserted(p + 1, children.size());
            } else {
                for (W child : children) {
                    int p = mItems.indexOf(child);
                    if (p != -1) {
                        mItems.remove(p);
                        notifyItemRemoved(p);
                    }
                }
            }
        }
    }

    private void flattenChildren(List<W> dest, W item) {
        if (item.children != null) {
            for (W child : item.children) {
                dest.add(child);
                if (child.isOpened()) {
                    flattenChildren(dest, child);
                }
            }
        }
    }

    public static class WrappedItem<W extends WrappedItem<W, T>, T extends ITreeItem<T>> {
        public final int level;
        public final T item;
        public final List<W> children = new ArrayList<>();
        public final W parent;
        private boolean opened;

        public WrappedItem(int level, T item, @NonNull W parent) {
            this.level = level;
            this.item = item;
            this.parent = parent;
        }

        public T getItem() {
            return item;
        }

        public boolean isOpened() {
            return opened;
        }

        void setOpened(boolean opened) {
            this.opened = opened;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            @SuppressWarnings("unchecked")
            W that = (W) o;

            return item != null ? item.equals(that.item) : that.item == null;
        }

        @Override
        public int hashCode() {
            return item != null ? item.hashCode() : 0;
        }
    }

    public void setListenerRelay(@Nullable ListenerRelay<A, VH> listenerRelay) {
        mListenerRelay = listenerRelay;
    }

    @NonNull
    @Override
    public ForwardingListener<A, VH> createForwardingListener() {
        ForwardingListener<A, VH> forwardingListener = new ForwardingListener<>();
        if (mListenerRelay != null) {
            forwardingListener.setListenerRelay(mListenerRelay);
        }
        return forwardingListener;
    }

    public interface ITreeItemAdapterRef<
            A extends AbsTreeItemAdapter<A, VH, T, W>,
            VH extends RecyclerView.ViewHolder,
            T extends ITreeItem<T>,
            W extends WrappedItem<W, T>
            > extends Serializable {
        @NonNull
        Class<T> getItemClass();

        @NonNull
        A createAdapter(@NonNull Context context, @NonNull List<T> items);

        @NonNull
        W createWrappedItem(int level, T item, W parent);
    }
}
