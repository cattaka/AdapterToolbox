package net.cattaka.android.snippets.view;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.NestedScrollingChildHelper;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by cattaka on 2016/04/27.
 */
public class NestedCoordinatorLayout extends CoordinatorLayout {
    private final NestedScrollingChildHelper mScrollingChildHelper;
    private final int[] mParentOffsetInWindow = new int[2];

    public NestedCoordinatorLayout(Context context) {
        super(context);
        mScrollingChildHelper = new NestedScrollingChildHelper(this);
        mScrollingChildHelper.setNestedScrollingEnabled(true);
    }

    public NestedCoordinatorLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mScrollingChildHelper = new NestedScrollingChildHelper(this);
        mScrollingChildHelper.setNestedScrollingEnabled(true);
    }

    public NestedCoordinatorLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mScrollingChildHelper = new NestedScrollingChildHelper(this);
        mScrollingChildHelper.setNestedScrollingEnabled(true);
    }

    // NestedScrollingParent

    @Override
    public boolean onStartNestedScroll(View child, View target, int nestedScrollAxes) {
        boolean handled = super.onStartNestedScroll(child, target, nestedScrollAxes);
        handled |= mScrollingChildHelper.startNestedScroll(nestedScrollAxes);
        return handled;
    }

    @Override
    public void onStopNestedScroll(View target) {
        super.onStopNestedScroll(target);
        mScrollingChildHelper.stopNestedScroll();
    }

    @Override
    public void onNestedScrollAccepted(View child, View target, int nestedScrollAxes) {
        if (!mScrollingChildHelper.startNestedScroll(nestedScrollAxes)) {
            super.onNestedScrollAccepted(child, target, nestedScrollAxes);
        }
    }

    @Override
    public void onNestedScroll(View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        if (!mScrollingChildHelper.dispatchNestedScroll(dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, mParentOffsetInWindow)) {
            super.onNestedScroll(target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed);
        }
    }

    @Override
    public void onNestedPreScroll(View target, int dx, int dy, int[] consumed) {
        if (!mScrollingChildHelper.dispatchNestedPreScroll(dx, dy, consumed, mParentOffsetInWindow)) {
            super.onNestedPreScroll(target, dx, dy, consumed);
        }
    }

    @Override
    public boolean onNestedFling(View target, float velocityX, float velocityY, boolean consumed) {
        return mScrollingChildHelper.dispatchNestedFling(velocityX, velocityY, consumed)
                || super.onNestedFling(target, velocityX, velocityY, consumed);
    }

    @Override
    public boolean onNestedPreFling(View target, float velocityX, float velocityY) {
        return mScrollingChildHelper.dispatchNestedPreFling(velocityX, velocityY)
                || super.onNestedPreFling(target, velocityX, velocityY);
    }

    @Override
    public int getNestedScrollAxes() {
        return super.getNestedScrollAxes();
    }
}
