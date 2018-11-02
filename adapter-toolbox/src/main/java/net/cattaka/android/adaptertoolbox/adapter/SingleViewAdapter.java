package net.cattaka.android.adaptertoolbox.adapter;

import android.content.Context;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Collections;
import java.util.List;

/**
 * Created by cattaka on 2015/07/30.
 *
 * @deprecated Replaced with {@link SingleViewAdapter2}
 */
public class SingleViewAdapter extends CustomRecyclerAdapter<SingleViewAdapter, RecyclerView.ViewHolder, Object> {
    private Context mContext;
    @LayoutRes
    private int mViewResId;
    private boolean mVisible = true;
    private Object mDummy = new Object();

    public SingleViewAdapter(@NonNull Context context, @LayoutRes int viewResId) {
        mContext = context;
        mViewResId = viewResId;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(mViewResId, parent, false);
        RecyclerView.ViewHolder vh = new RecyclerView.ViewHolder(view) {
        };

        view.setOnClickListener(getForwardingListener(parent));
        view.setOnLongClickListener(getForwardingListener(parent));

        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        // no-op
    }

    @Override
    public int getItemCount() {
        return mVisible ? 1 : 0;
    }

    public boolean isVisible() {
        return mVisible;
    }

    public void setVisible(boolean visible) {
        if (mVisible != visible) {
            mVisible = visible;
            if (mVisible) {
                notifyItemInserted(0);
            } else {
                notifyItemRemoved(0);
            }
        }
    }

    @NonNull
    @Override
    public Object getItemAt(int position) {
        return mDummy;
    }

    @NonNull
    @Override
    public List<Object> getItems() {
        return Collections.singletonList(mDummy);
    }
}
