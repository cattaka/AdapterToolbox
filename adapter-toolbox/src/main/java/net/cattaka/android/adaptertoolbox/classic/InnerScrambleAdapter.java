package net.cattaka.android.adaptertoolbox.classic;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import net.cattaka.android.adaptertoolbox.adapter.AbsScrambleAdapter;
import net.cattaka.android.adaptertoolbox.adapter.AdapterConverter;
import net.cattaka.android.adaptertoolbox.classic.listener.ClassicForwardingListener;
import net.cattaka.android.adaptertoolbox.classic.listener.ClassicListenerRelay;

import java.util.Arrays;
import java.util.List;

/**
 * Created by cattaka on 2016/05/10.
 */
public class InnerScrambleAdapter<T> extends AbsScrambleAdapter<
        InnerScrambleAdapter<T>,
        InnerScrambleAdapter<?>,
        AdapterConverter.ViewHolder,
        ClassicForwardingListener<InnerScrambleAdapter<?>, AdapterConverter.ViewHolder>,
        ClassicListenerRelay<InnerScrambleAdapter<?>, AdapterConverter.ViewHolder>,
        T
        > {
    private Context mContext;
    private List<T> mItems;
    private ClassicScrambleAdapter<T> mParent;

    @SafeVarargs
    public InnerScrambleAdapter(
            @NonNull Context context,
            @NonNull List<T> items,
            @Nullable ClassicListenerRelay<InnerScrambleAdapter<?>,
                    AdapterConverter.ViewHolder> listenerRelay,
            @NonNull IViewHolderFactory<InnerScrambleAdapter<?>,
                    AdapterConverter.ViewHolder,
                    ClassicForwardingListener<InnerScrambleAdapter<?>, AdapterConverter.ViewHolder>,
                    ?,
                    ClassicListenerRelay<InnerScrambleAdapter<?>,
                            AdapterConverter.ViewHolder>>... iViewHolderFactories
    ) {
        this(context, items, listenerRelay, Arrays.asList(iViewHolderFactories));
    }

    public InnerScrambleAdapter(
            @NonNull Context context,
            @NonNull List<T> items,
            @Nullable ClassicListenerRelay<InnerScrambleAdapter<?>,
                    AdapterConverter.ViewHolder> listenerRelay,
            @NonNull List<? extends IViewHolderFactory<InnerScrambleAdapter<?>,
                    AdapterConverter.ViewHolder,
                    ClassicForwardingListener<InnerScrambleAdapter<?>, AdapterConverter.ViewHolder>,
                    ?,
                    ClassicListenerRelay<InnerScrambleAdapter<?>,
                            AdapterConverter.ViewHolder>>> iViewHolderFactories
    ) {
        super(listenerRelay, iViewHolderFactories);
        this.mContext = context;
        this.mItems = items;
    }

    public ClassicScrambleAdapter<T> getParent() {
        return mParent;
    }

    public void setParent(ClassicScrambleAdapter<T> parent) {
        mParent = parent;
    }

    public Context getContext() {
        return mContext;
    }

    @NonNull
    @Override
    public AdapterConverter.ViewHolder createNullViewHolder() {
        View view = new View(mContext);
        return new AdapterConverter.ViewHolder(view) {
        };
    }

    @Override
    public T getItemAt(int position) {
        return mItems.get(position);
    }

    public List<T> getItems() {
        return mItems;
    }

    @NonNull
    @Override
    public InnerScrambleAdapter<T> getSelf() {
        return this;
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public static abstract class AbsViewHolderFactory<EVH extends AdapterConverter.ViewHolder>
            implements IViewHolderFactory<
            InnerScrambleAdapter<?>,
            AdapterConverter.ViewHolder,
            ClassicForwardingListener<InnerScrambleAdapter<?>, AdapterConverter.ViewHolder>,
            EVH,
            ClassicListenerRelay<InnerScrambleAdapter<?>, AdapterConverter.ViewHolder>
            > {

        @Override
        public boolean isAssignable(@NonNull InnerScrambleAdapter<?> adapter, Object object) {
            return isAssignable(object);
        }

        public abstract boolean isAssignable(Object object);

        @Override
        public ClassicForwardingListener<InnerScrambleAdapter<?>, AdapterConverter.ViewHolder> createForwardingListener() {
            return new ClassicForwardingListener<>();
        }

        @Override
        public boolean onFailedToRecycleView(@NonNull InnerScrambleAdapter<?> adapter, @NonNull AdapterConverter.ViewHolder holder) {
            // no-op
            return false;
        }

        @Override
        public void onViewAttachedToWindow(@NonNull InnerScrambleAdapter<?> adapter, @NonNull AdapterConverter.ViewHolder holder) {
            // no-op
        }

        @Override
        public void onViewDetachedFromWindow(@NonNull InnerScrambleAdapter<?> adapter, @NonNull AdapterConverter.ViewHolder holder) {
            // no-op
        }

        @Override
        public void onViewRecycled(@NonNull InnerScrambleAdapter<?> adapter, @NonNull AdapterConverter.ViewHolder holder) {
            // no-op
        }
    }
}
