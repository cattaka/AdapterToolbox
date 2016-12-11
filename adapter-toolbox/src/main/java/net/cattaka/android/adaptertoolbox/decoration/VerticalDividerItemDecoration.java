package net.cattaka.android.adaptertoolbox.decoration;

import android.content.Context;
import android.content.res.TypedArray;
import android.databinding.BindingAdapter;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import net.cattaka.android.adaptertoolbox.adapter.listener.ForwardingListener;

/**
 * Created by cattaka on 16/12/11.
 */
public class VerticalDividerItemDecoration extends RecyclerView.ItemDecoration {
    private Drawable mDrawable;
    private boolean mIgnoreParentPadding = true;

    public VerticalDividerItemDecoration(Context context, boolean ignoreParentPadding) {
        mIgnoreParentPadding = ignoreParentPadding;
        final TypedArray a = context.obtainStyledAttributes(new int[]{
                android.R.attr.listDivider
        });
        mDrawable = a.getDrawable(0);
        a.recycle();
    }

    public VerticalDividerItemDecoration(Context context, boolean ignoreParentPadding, @DrawableRes int drawableRes) {
        mIgnoreParentPadding = ignoreParentPadding;
        mDrawable = ContextCompat.getDrawable(context, drawableRes);
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        if (mDrawable == null) {
            return;
        }
        int left = (mIgnoreParentPadding) ? 0 : parent.getPaddingLeft();
        int right = (mIgnoreParentPadding) ? 0 : (parent.getWidth() - parent.getPaddingRight());

        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            RecyclerView.ViewHolder nextHolder = ForwardingListener.findContainingViewHolder(parent, child);
            RecyclerView.ViewHolder prevHolder = findPrevViewHolder(parent, nextHolder);
            if (nextHolder != null && prevHolder != null && isAssignable(parent, prevHolder, nextHolder)) {
                final ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) child.getLayoutParams();
                final int bottom = child.getTop() - params.topMargin;
                final int top = bottom - mDrawable.getIntrinsicHeight();
                mDrawable.setBounds(left, top, right, bottom);
                mDrawable.draw(c);
            }
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        RecyclerView.ViewHolder nextHolder = ForwardingListener.findContainingViewHolder(parent, view);
        RecyclerView.ViewHolder prevHolder = findPrevViewHolder(parent, nextHolder);
        if (mDrawable == null && nextHolder != null && prevHolder != null && isAssignable(parent, prevHolder, nextHolder)) {
            outRect.set(0, mDrawable.getIntrinsicHeight(), 0, 0);
        } else {
            outRect.set(0, 0, 0, 0);
        }
    }

    @Nullable
    private RecyclerView.ViewHolder findPrevViewHolder(@NonNull RecyclerView parent, @Nullable RecyclerView.ViewHolder nextHolder) {
        if (nextHolder == null) {
            return null;
        }
        RecyclerView.Adapter adapter = parent.getAdapter();
        if (adapter != null && nextHolder.getLayoutPosition() > 0) {
            View prevView = parent.getChildAt(nextHolder.getLayoutPosition() - 1);
            return ForwardingListener.findContainingViewHolder(parent, prevView);
        } else {
            return null;
        }
    }

    /**
     * Override this if needed.
     */
    public boolean isAssignable(@NonNull RecyclerView parent, @NonNull RecyclerView.ViewHolder prevHolder, @NonNull RecyclerView.ViewHolder nextHolder) {
        return true;
    }

    public Drawable getDrawable() {
        return mDrawable;
    }

    public void setDrawable(Drawable drawable) {
        mDrawable = drawable;
    }

    public boolean isIgnoreParentPadding() {
        return mIgnoreParentPadding;
    }

    public void setIgnoreParentPadding(boolean ignoreParentPadding) {
        mIgnoreParentPadding = ignoreParentPadding;
    }

    @BindingAdapter("verticalDivider")
    public static void verticalDivider(RecyclerView view, @DrawableRes int drawableRes) {
        view.addItemDecoration(new VerticalDividerItemDecoration(view.getContext(), false, drawableRes));
    }

    @BindingAdapter("verticalDividerIgnoreParentPadding")
    public static void verticalDividerIgnoreParentPadding(RecyclerView view, @DrawableRes int drawableRes) {
        view.addItemDecoration(new VerticalDividerItemDecoration(view.getContext(), true, drawableRes));
    }
}
