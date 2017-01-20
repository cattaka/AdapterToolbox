package net.cattaka.android.adaptertoolbox.view;

import android.content.Context;
import android.os.Build;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.annotation.StyleRes;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import net.cattaka.android.adaptertoolbox.R;

/**
 * Created by takao on 2017/01/20.
 */

public class VerticalFastScrollView extends FrameLayout {
    private RecyclerView.OnScrollListener mOnScrollListener = new RecyclerView.OnScrollListener() {
        private int mLastState = RecyclerView.SCROLL_STATE_IDLE;

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            if (!mDragging) {
                syncFromRecyclerView();
            }
        }

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            if (!mDragging) {
                if (mLastState != newState) {
                    mLastState = newState;
                    float alpha = (newState == RecyclerView.SCROLL_STATE_IDLE) ? 0f : 1f;
                    mKnobView.animate().alpha(alpha).start();
                }
            }
        }
    };

    private View mIndexLabelView;
    private View mKnobView;
    private RecyclerView mRecyclerView;
    private boolean mDragging;
    private int mKnobMinHeight;

    public VerticalFastScrollView(@NonNull Context context) {
        super(context);
        initialize();
    }

    public VerticalFastScrollView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initialize();
    }

    public VerticalFastScrollView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public VerticalFastScrollView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr, @StyleRes int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initialize();
    }

    private void initialize() {
        setClickable(true);

        LayoutInflater inflater = LayoutInflater.from(getContext());
        mIndexLabelView = inflater.inflate(R.layout.adapter_toolbox_fast_scroll_index_label, this, false);
        mKnobView = inflater.inflate(R.layout.adapter_toolbox_fast_scroll_knob, this, false);
        addView(mIndexLabelView);
        addView(mKnobView);

        mIndexLabelView.setAlpha(0);
        mKnobView.setAlpha(0);

        mKnobMinHeight = getResources().getDimensionPixelSize(R.dimen.adapter_toolbox_fast_scroll_knob_min_height);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getActionMasked() == MotionEvent.ACTION_DOWN) {
            if (isOnScrollArea(event.getX())) {
                changeDragging(true);
            }
        } else if (event.getActionMasked() == MotionEvent.ACTION_MOVE) {
            if (mDragging) {
                View knobView = getKnobView();
                float y = event.getY();
                int height = getHeight();
                int knobHeight = knobView.getHeight();
                float value = (y - knobHeight / 2) / (height - knobHeight);

                setScrollPosition(value);
                syncToRecyclerView(value);
            }
        } else if (event.getActionMasked() == MotionEvent.ACTION_UP) {
            if (mDragging) {
                changeDragging(false);
            }
        }
        return super.onTouchEvent(event);
    }

    protected boolean isOnScrollArea(float touchX) {
        View v = getKnobView();
        float left = v.getLeft();
        float right = v.getRight();
        return left <= touchX && touchX <= right;
    }

    private void changeDragging(boolean dragging) {
        if (mDragging != dragging) {
            mDragging = dragging;

            float alpha = dragging ? 1f : 0f;
            mIndexLabelView.animate().alpha(alpha).start();
            mKnobView.animate().alpha(alpha).start();
        }
    }

    private void setScrollPosition(float value) {
        float v = Math.max(0f, Math.min(value, 1f));

        View indexLabelView = getIndexLabelView();
        View knobView = getKnobView();
        int height = getHeight();
        int knobHeight;
        {
            RecyclerView.Adapter adapter = mRecyclerView != null ? mRecyclerView.getAdapter() : null;
            int count = adapter != null ? adapter.getItemCount() : 1;
            count = Math.max(1, count);
            knobHeight = Math.max(getHeight() / count, mKnobMinHeight);
        }

        int knobTop = (int) ((height - knobHeight) * v);
        {
            MarginLayoutParams params = (MarginLayoutParams) knobView.getLayoutParams();
            if (params.topMargin != knobTop || params.height != knobHeight) {
                params.topMargin = knobTop;
                params.height = knobHeight;
                knobView.setLayoutParams(params);
            }
        }
        int indexLabelTop = Math.max(0, (knobTop + knobHeight / 2) - indexLabelView.getHeight());
        {
            MarginLayoutParams params = (MarginLayoutParams) indexLabelView.getLayoutParams();
            if (params.topMargin != indexLabelTop) {
                params.topMargin = indexLabelTop;
                indexLabelView.setLayoutParams(params);
            }
        }
    }

    private void syncToRecyclerView(float value) {
        if (mRecyclerView != null) {
            RecyclerView.ViewHolder holder = mRecyclerView.getChildCount() > 0 ? mRecyclerView.getChildViewHolder(mRecyclerView.getChildAt(0)) : null;
            int currPosition = (holder != null) ? holder.getAdapterPosition() : 0;

            RecyclerView.Adapter adapter = mRecyclerView.getAdapter();
            int count = (adapter != null) ? adapter.getItemCount() : 1;
            int p = (int) (count * value);
            p = Math.max(0, Math.min(p, count));
            if (currPosition != p) {
                mRecyclerView.scrollToPosition(p);
            }
        }
    }

    private void syncFromRecyclerView() {
        if (mRecyclerView != null) {
            RecyclerView.ViewHolder holder = mRecyclerView.getChildCount() > 0 ? mRecyclerView.getChildViewHolder(mRecyclerView.getChildAt(0)) : null;
            int p = (holder != null) ? holder.getAdapterPosition() : 0;
            RecyclerView.Adapter adapter = mRecyclerView.getAdapter();
            int count = (adapter != null) ? adapter.getItemCount() : 1;

            setScrollPosition((float) p / (float) count);
        }
    }

    public View getIndexLabelView() {
        return mIndexLabelView;
    }

    public View getKnobView() {
        return mKnobView;
    }

    public void attachRecyclerView(RecyclerView recyclerView) {
        if (mRecyclerView != null) {
            mRecyclerView.removeOnScrollListener(mOnScrollListener);
        }
        mRecyclerView = recyclerView;
        if (mRecyclerView != null) {
            mRecyclerView.addOnScrollListener(mOnScrollListener);
        }
    }
}
