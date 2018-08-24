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

public class SingleViewAdapter2 extends CustomRecyclerAdapter<SingleViewAdapter2, SingleViewAdapter2.ViewHolder, Object> {
    private Context mContext;
    @LayoutRes
    private int mViewResId;
    private boolean mVisible = true;
    private Object mDummy = new Object();

    public SingleViewAdapter2(@NonNull Context context, @LayoutRes int viewResId) {
        mContext = context;
        mViewResId = viewResId;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(mViewResId, parent, false);
        ViewHolder vh = createViewHolder(view, mViewResId);

        view.setOnClickListener(getForwardingListener(parent));
        view.setOnLongClickListener(getForwardingListener(parent));

        return vh;
    }

    protected ViewHolder createViewHolder(@NonNull View view, @LayoutRes int viewResId) {
        return new ViewHolder(view, viewResId);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
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

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private int mViewResId;

        public ViewHolder(View itemView, @LayoutRes int viewResId) {
            super(itemView);
            mViewResId = viewResId;
        }

        @LayoutRes
        public int getViewResId() {
            return mViewResId;
        }
    }
}
