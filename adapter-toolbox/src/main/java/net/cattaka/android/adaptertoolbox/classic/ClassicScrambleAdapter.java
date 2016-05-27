package net.cattaka.android.adaptertoolbox.classic;

import android.content.Context;
import android.support.annotation.NonNull;

import net.cattaka.android.adaptertoolbox.adapter.AbsScrambleAdapter;
import net.cattaka.android.adaptertoolbox.adapter.AdapterConverter;
import net.cattaka.android.adaptertoolbox.classic.listener.ClassicForwardingListener;
import net.cattaka.android.adaptertoolbox.classic.listener.ClassicListenerRelay;

import java.util.Arrays;
import java.util.List;

/**
 * Created by takao on 2016/05/27.
 */
public class ClassicScrambleAdapter<T> extends AdapterConverter<InnerScrambleAdapter<T>, AdapterConverter.ViewHolder, T> {
    public ClassicScrambleAdapter(
            @NonNull Context context,
            @NonNull List<T> items,
            @NonNull ClassicListenerRelay<InnerScrambleAdapter<?>, ViewHolder> listenerRelay,
            @NonNull AbsScrambleAdapter.IViewHolderFactory<InnerScrambleAdapter<?>,
                    ViewHolder,
                    ClassicForwardingListener<InnerScrambleAdapter<?>>,
                    ?,
                    ClassicListenerRelay<InnerScrambleAdapter<?>,
                            ViewHolder>>... iViewHolderFactories
    ) {
        super(context, new InnerScrambleAdapter<T>(context, items, listenerRelay, Arrays.asList(iViewHolderFactories)));
    }

    public ClassicScrambleAdapter(
            @NonNull Context context,
            @NonNull List<T> items,
            @NonNull ClassicListenerRelay<InnerScrambleAdapter<?>, ViewHolder> listenerRelay,
            @NonNull List<? extends AbsScrambleAdapter.IViewHolderFactory<InnerScrambleAdapter<?>,
                    ViewHolder,
                    ClassicForwardingListener<InnerScrambleAdapter<?>>,
                    ?,
                    ClassicListenerRelay<InnerScrambleAdapter<?>,
                            ViewHolder>>> iViewHolderFactories
    ) {
        super(context, new InnerScrambleAdapter<T>(context, items, listenerRelay, iViewHolderFactories));
    }
}
