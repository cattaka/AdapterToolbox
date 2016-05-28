package net.cattaka.android.adaptertoolbox.classic;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import net.cattaka.android.adaptertoolbox.adapter.AbsScrambleAdapter;
import net.cattaka.android.adaptertoolbox.adapter.AdapterConverter;
import net.cattaka.android.adaptertoolbox.adapter.ScrambleAdapter;
import net.cattaka.android.adaptertoolbox.adapter.listener.ForwardingListener;
import net.cattaka.android.adaptertoolbox.adapter.listener.ListenerRelay;
import net.cattaka.android.adaptertoolbox.classic.listener.ClassicForwardingListener;
import net.cattaka.android.adaptertoolbox.classic.listener.ClassicListenerRelay;

import java.util.Arrays;
import java.util.List;

/**
 * Created by takao on 2016/05/27.
 */
public class ClassicScrambleAdapter<T> extends AdapterConverter<ScrambleAdapter<T>, RecyclerView.ViewHolder, T> {
    ListenerRelay<ScrambleAdapter<?>, RecyclerView.ViewHolder> mListenerRelay = new ListenerRelay<>();

    public ClassicScrambleAdapter(
            @NonNull Context context,
            @NonNull List<T> items,
            ClassicListenerRelay classicListenerRelay,
            AbsScrambleAdapter.IViewHolderFactory<
                    ScrambleAdapter<?>,
                    RecyclerView.ViewHolder,
                    ForwardingListener<ScrambleAdapter<?>, RecyclerView.ViewHolder>, ?
                    >... iViewHolderFactory) {
        this(context, items, classicListenerRelay, Arrays.asList(iViewHolderFactory));
    }

    public ClassicScrambleAdapter(
            @NonNull Context context,
            @NonNull List<T> items,
            ClassicListenerRelay classicListenerRelay,
            List<? extends AbsScrambleAdapter.IViewHolderFactory<
                    ScrambleAdapter<?>,
                    RecyclerView.ViewHolder,
                    ForwardingListener<ScrambleAdapter<?>, RecyclerView.ViewHolder>, ?
                    >> iViewHolderFactory) {
        super(context, new InnerScrambleAdapter<T>(context, items, classicListenerRelay, iViewHolderFactory));
        InnerScrambleAdapter<T> orig = (InnerScrambleAdapter<T>)getOrig();
        orig.setParentAdapter(this);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View itemView) {
            super(itemView);
        }
    }

    private static class InnerScrambleAdapter<T> extends ScrambleAdapter<T> {
        ClassicScrambleAdapter<T> mParentAdapter;
        ClassicListenerRelay mClassicListenerRelay;

        public InnerScrambleAdapter(
                @NonNull Context context,
                @NonNull List<T> items,
                @Nullable ClassicListenerRelay listenerRelay,
                @NonNull List<? extends IViewHolderFactory<ScrambleAdapter<?>,
                        RecyclerView.ViewHolder,
                        ForwardingListener<ScrambleAdapter<?>,
                                RecyclerView.ViewHolder>,
                        ?
                        >> iViewHolderFactories) {
            super(context, items, null, iViewHolderFactories);
            mClassicListenerRelay = listenerRelay;
        }

        public void setParentAdapter(ClassicScrambleAdapter<T> parentAdapter) {
            mParentAdapter = parentAdapter;
        }

        @Override
        public ForwardingListener<ScrambleAdapter<?>, RecyclerView.ViewHolder> createForwardingListener(IViewHolderFactory<ScrambleAdapter<?>, RecyclerView.ViewHolder, ForwardingListener<ScrambleAdapter<?>, RecyclerView.ViewHolder>, ?> viewHolderFactory) {
            if (viewHolderFactory instanceof ScrambleAdapter.AbsViewHolderFactory) {
                ClassicForwardingListener forwardingListener = ((AbsViewHolderFactory) viewHolderFactory).createClassicForwardingListener();
                forwardingListener.setAdapter(mParentAdapter);
                forwardingListener.setClassicListenerRelay(mClassicListenerRelay);
                return forwardingListener;
            } else {
                return super.createForwardingListener(viewHolderFactory);
            }
        }
    }
}
