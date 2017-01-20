package net.cattaka.android.adaptertoolbox.view;

import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.annotation.StyleRes;
import android.support.v4.view.NestedScrollingChildHelper;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
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
                syncWithRecyclerView();
            }
            refreshIndexLabel();
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
    private NestedScrollingChildHelper mScrollingChildHelper;

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

        mKnobMinHeight = getResources().getDimensionPixelSize(R.dimen.adapter_toolbox_fast_scroll_knob_min_height);

        if (isInEditMode()) {
            return;
        }

        mIndexLabelView.setAlpha(0);
        mKnobView.setAlpha(0);

        setNestedScrollingEnabled(true);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getActionMasked() == MotionEvent.ACTION_DOWN) {
            if (isOnScrollArea(event.getX())) {
                changeDragging(true);
                startNestedScroll(SCROLL_AXIS_VERTICAL);
            }
        } else if (event.getActionMasked() == MotionEvent.ACTION_MOVE) {
            if (mDragging) {
                getParent().requestDisallowInterceptTouchEvent(true);

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
                stopNestedScroll();
            }
        }
        return mDragging;
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
            int childCount = mRecyclerView.getChildCount();  // FIXME: getChildCount() is unreliable.
            RecyclerView.ViewHolder holder = childCount > 0 ? mRecyclerView.getChildViewHolder(mRecyclerView.getChildAt(0)) : null;
            int currPosition = (holder != null) ? holder.getAdapterPosition() : 0;

            RecyclerView.Adapter adapter = mRecyclerView.getAdapter();
            int count = (adapter != null) ? adapter.getItemCount() : 1;
            if (count > childCount) {
                int p = (int) ((count - childCount) * value);
                p = Math.max(0, Math.min(p, count - childCount));
                if (currPosition != p) {
                    RecyclerView.LayoutManager layoutManager = mRecyclerView.getLayoutManager();
                    // HELPME: I want to remove these cast.
                    if (layoutManager instanceof LinearLayoutManager) {
                        ((LinearLayoutManager) layoutManager).scrollToPositionWithOffset(p, 0);
                    } else if (layoutManager instanceof StaggeredGridLayoutManager) {
                        ((StaggeredGridLayoutManager) layoutManager).scrollToPositionWithOffset(p, 0);
                    } else {
                        layoutManager.scrollToPosition(p);
                    }
                }
            }
        }
    }

    public void syncWithRecyclerView() {
        if (mRecyclerView != null) {
            int childCount = mRecyclerView.getChildCount();  // FIXME: getChildCount() is unreliable.
            RecyclerView.ViewHolder holder = childCount > 0 ? mRecyclerView.getChildViewHolder(mRecyclerView.getChildAt(0)) : null;
            float p = (holder != null) ? holder.getAdapterPosition() : 0;
            RecyclerView.Adapter adapter = mRecyclerView.getAdapter();
            int count = (adapter != null) ? adapter.getItemCount() : 1;
            if (holder != null && holder.itemView.getHeight() > 0) {
                p -= (float) holder.itemView.getTop() / (float) obtainRealHeight(holder.itemView);
            }

            float value = (count > childCount) ? (p / (float) (count - childCount)) : 0;
            setScrollPosition(value);
        }
    }

    private int obtainRealHeight(View view) {
        int h = view.getHeight();
        ViewGroup.LayoutParams params = view.getLayoutParams();
        if (params instanceof MarginLayoutParams) {
            h += ((MarginLayoutParams) params).topMargin + ((MarginLayoutParams) params).bottomMargin;
        }
        Rect rect = new Rect();
        mRecyclerView.getLayoutManager().calculateItemDecorationsForChild(view, rect);
        h += rect.top + rect.bottom;
        return h;
    }

    private void refreshIndexLabel() {
        if (mRecyclerView != null) {
            View view = getIndexLabelView();
            boolean success = false;
            for (int i = 0; i < mRecyclerView.getChildCount(); i++) {
                RecyclerView.ViewHolder holder = mRecyclerView.getChildViewHolder(mRecyclerView.getChildAt(i));
                if (holder instanceof IFastScrollViewHolder) {
                    success = true;
                    ((IFastScrollViewHolder) holder).updateIndexLabelView(view);
                    break;
                }
            }
            view.setVisibility(success ? VISIBLE : INVISIBLE);
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

    // NestedScrollingChild

    @Override
    public void setNestedScrollingEnabled(boolean enabled) {
        getScrollingChildHelper().setNestedScrollingEnabled(enabled);
    }

    @Override
    public boolean isNestedScrollingEnabled() {
        return getScrollingChildHelper().isNestedScrollingEnabled();
    }

    @Override
    public boolean startNestedScroll(int axes) {
        return getScrollingChildHelper().startNestedScroll(axes);
    }

    @Override
    public void stopNestedScroll() {
        getScrollingChildHelper().stopNestedScroll();
    }

    @Override
    public boolean hasNestedScrollingParent() {
        return getScrollingChildHelper().hasNestedScrollingParent();
    }

    @Override
    public boolean dispatchNestedScroll(int dxConsumed, int dyConsumed, int dxUnconsumed,
                                        int dyUnconsumed, int[] offsetInWindow) {
        return getScrollingChildHelper().dispatchNestedScroll(dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, offsetInWindow);
    }

    @Override
    public boolean dispatchNestedPreScroll(int dx, int dy, int[] consumed, int[] offsetInWindow) {
        return getScrollingChildHelper().dispatchNestedPreScroll(dx, dy, consumed, offsetInWindow);
    }

    @Override
    public boolean dispatchNestedFling(float velocityX, float velocityY, boolean consumed) {
        return getScrollingChildHelper().dispatchNestedFling(velocityX, velocityY, consumed);
    }

    @Override
    public boolean dispatchNestedPreFling(float velocityX, float velocityY) {
        return getScrollingChildHelper().dispatchNestedPreFling(velocityX, velocityY);
    }

    private NestedScrollingChildHelper getScrollingChildHelper() {
        if (mScrollingChildHelper == null) {
            mScrollingChildHelper = new NestedScrollingChildHelper(this);
        }
        return mScrollingChildHelper;
    }
}
