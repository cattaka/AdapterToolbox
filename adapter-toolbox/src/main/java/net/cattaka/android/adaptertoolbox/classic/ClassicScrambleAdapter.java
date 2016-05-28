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

import java.util.List;

/**
 * Created by takao on 2016/05/27.
 */
public class ClassicScrambleAdapter<T> extends AdapterConverter<ScrambleAdapter<T>, RecyclerView.ViewHolder, T> {
    ListenerRelay<ScrambleAdapter<?>, RecyclerView.ViewHolder> mListenerRelay = new ListenerRelay<>();

    public ClassicScrambleAdapter(
            @NonNull Context context,
            @NonNull List<T> items,
            ListenerRelay<ScrambleAdapter<?>, RecyclerView.ViewHolder> listenerRelay,
            AbsScrambleAdapter.IViewHolderFactory<
                    ScrambleAdapter<?>,
                    RecyclerView.ViewHolder,
                    ForwardingListener<ScrambleAdapter<?>, RecyclerView.ViewHolder>, ?, ListenerRelay<ScrambleAdapter<?>, RecyclerView.ViewHolder>
                    >... iViewHolderFactory) {
        super(context, new ScrambleAdapter<T>(context, items, null, iViewHolderFactory));
    }

    public ClassicScrambleAdapter(
            @NonNull Context context,
            @NonNull List<T> items,
            ListenerRelay<ScrambleAdapter<?>, RecyclerView.ViewHolder> listenerRelay,
            List<? extends AbsScrambleAdapter.IViewHolderFactory<
                    ScrambleAdapter<?>,
                    RecyclerView.ViewHolder,
                    ForwardingListener<ScrambleAdapter<?>, RecyclerView.ViewHolder>, ?, ListenerRelay<ScrambleAdapter<?>, RecyclerView.ViewHolder>
                    >> iViewHolderFactory) {
        super(context, new ScrambleAdapter<T>(context, items, null, iViewHolderFactory));
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View itemView) {
            super(itemView);
        }
    }

    private void initialize() {
        ClassicForwardingListener<ScrambleAdapter<?>, RecyclerView.ViewHolder> forwardingListener = new ClassicForwardingListener<>();
    }

    private static class InnerScrambleAdapter<T> extends ScrambleAdapter<T> {
        ClassicListenerRelay mListenerRelay;
        public InnerScrambleAdapter(
                @NonNull Context context,
                @NonNull List<T> items,
                @Nullable ClassicListenerRelay listenerRelay,
                @NonNull IViewHolderFactory<ScrambleAdapter<?>,
                        RecyclerView.ViewHolder,
                        ForwardingListener<ScrambleAdapter<?>,
                                RecyclerView.ViewHolder>,
                        ?,
                        ListenerRelay<ScrambleAdapter<?>,
                                RecyclerView.ViewHolder>
                        >... iViewHolderFactories) {
            super(context, items, null, iViewHolderFactories);
            mListenerRelay = listenerRelay;
        }

        public InnerScrambleAdapter(
                @NonNull Context context,
                @NonNull List<T> items,
                @Nullable ClassicListenerRelay listenerRelay,
                @NonNull List<? extends IViewHolderFactory<ScrambleAdapter<?>,
                        RecyclerView.ViewHolder,
                        ForwardingListener<ScrambleAdapter<?>,
                                RecyclerView.ViewHolder>,
                        ?,
                        ListenerRelay<ScrambleAdapter<?>,
                                RecyclerView.ViewHolder>>
                        > iViewHolderFactories) {
            super(context, items, null, iViewHolderFactories);
            mListenerRelay = listenerRelay;
        }


    }
}
