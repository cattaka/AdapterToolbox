package net.cattaka.android.adaptertoolbox.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;

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
        VH extends RecyclerView.ViewHolder,
        T extends ITreeItem<T>,
        W extends AbsTreeItemAdapter.WrappedItem<W, T>
        > extends AbsCustomRecyclerAdapter<
        AbsTreeItemAdapter<VH, T, W>,
        VH,
        W,
        ForwardingListener<AbsTreeItemAdapter<VH, T, W>, VH>,
        ListenerRelay<AbsTreeItemAdapter<VH, T, W>, VH>
        > {
    private Context mContext;
    private List<W> mItems;

    private RecyclerView mRecyclerView;

    @NonNull
    protected static <T extends ITreeItem<T>, W extends AbsTreeItemAdapter.WrappedItem<W, T>, REF extends ITreeItemAdapterRef<?, T, W>>
    List<W> inflateWrappedList(@NonNull List<W> dest, @NonNull List<T> items, int level, @Nullable W parent, @NonNull REF ref) {
        for (T item : items) {
            W child = ref.createWrappedItem(level, item, parent);
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

    public <REF extends ITreeItemAdapterRef<?, T, W>> AbsTreeItemAdapter(@NonNull Context context, @NonNull List<T> items, @NonNull REF ref) {
        mContext = context;
        mItems = inflateWrappedList(new ArrayList<W>(), items, 0, null, ref);
    }

    @NonNull
    public Context getContext() {
        return mContext;
    }

    @NonNull
    @Override
    public AbsTreeItemAdapter<VH, T, W> getSelf() {
        return this;
    }

    @NonNull
    @Override
    public ForwardingListener<AbsTreeItemAdapter<VH, T, W>, VH> createForwardingListener() {
        return new ForwardingListener<>();
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

    protected void doFold(W item, boolean fold) {
        for (W child : item.children) {
            child.fold = fold;
            doFold(child, child.opened || fold);
        }
        notifyItemChanged(getItems().indexOf(item));
    }

    public static class WrappedItem<W extends WrappedItem<W, T>, T extends ITreeItem<T>> {
        public final int level;
        public boolean opened;
        public boolean fold;
        public final T item;
        public W parent;
        public final List<W> children = new ArrayList<>();

        public WrappedItem(int level, T item, @NonNull W parent) {
            this.level = level;
            this.item = item;
            this.parent = parent;
        }

        public T getItem() {
            return item;
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

    @Nullable
    public RecyclerView getAttachedRecyclerView() {
        return mRecyclerView;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        mRecyclerView = recyclerView;
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        mRecyclerView = null;
    }

    public interface ITreeItemAdapterRef<VH extends RecyclerView.ViewHolder, T extends ITreeItem<T>, W extends WrappedItem<W, T>> extends Serializable {
        @NonNull
        Class<T> getItemClass();

        @NonNull
        AbsTreeItemAdapter<VH, T, W> createAdapter(@NonNull Context context, @NonNull List<T> items);

        @NonNull
        W createWrappedItem(int level, T item, W parent);
    }
}
