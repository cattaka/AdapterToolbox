package net.cattaka.android.snippets.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

/**
 * Created by cattaka on 16/05/01.
 */
public class WrapContentListView extends LinearLayout {
    public interface OnItemClickListener {
        void onItemClick(WrapContentListView parent, View view, int position, long id);
    }

    private RecyclerView.AdapterDataObserver mAdapterDataObserver = new RecyclerView.AdapterDataObserver() {
        @Override
        public void onChanged() {
            super.onChanged();
            refleshViews();
        }
    };

    private RecyclerView.Adapter mAdapter;

    private OnItemClickListener mOnItemClickListener;

    public WrapContentListView(Context context) {
        super(context);
    }

    public WrapContentListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public WrapContentListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public RecyclerView.Adapter getAdapter() {
        return mAdapter;
    }

    public void setAdapter(RecyclerView.Adapter adapter) {
        if (mAdapter != null) {
            mAdapter.unregisterAdapterDataObserver(mAdapterDataObserver);
        }
        mAdapter = adapter;
        mAdapter.registerAdapterDataObserver(mAdapterDataObserver);

        refleshViews();
    }

    private void refleshViews() {
        removeAllViews();
        for (int i = 0; i < mAdapter.getItemCount(); i++) {
            int itemViewType = mAdapter.getItemViewType(i);
            RecyclerView.ViewHolder vh = mAdapter.createViewHolder(this, itemViewType);
            mAdapter.onBindViewHolder(vh, i);
            FrameLayout.LayoutParams params;
            if (getOrientation() == VERTICAL) {
                params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            } else {
                params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
            }
            vh.itemView.setLayoutParams(params);
            vh.itemView.setClickable(true);
            final int position = i;
            vh.itemView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onItemClick(WrapContentListView.this, v, position, -1);
                    }
                }
            });

            FrameLayout frame = new FrameLayout(getContext());
            if (getOrientation() == VERTICAL) {
                frame.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            } else {
                frame.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT));
            }
            frame.addView(vh.itemView);

            this.addView(frame);
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }
}
