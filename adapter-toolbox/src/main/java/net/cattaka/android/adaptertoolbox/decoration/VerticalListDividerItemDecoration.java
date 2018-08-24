package net.cattaka.android.adaptertoolbox.decoration;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import net.cattaka.android.adaptertoolbox.adapter.listener.ForwardingListener;

/**
 * Created by cattaka on 16/12/11.
 */
public class VerticalListDividerItemDecoration extends RecyclerView.ItemDecoration {
    private boolean mIgnoreParentPadding = true;
    private Drawable mDrawable;

    public VerticalListDividerItemDecoration(Context context, boolean ignoreParentPadding) {
        mIgnoreParentPadding = ignoreParentPadding;
        final TypedArray a = context.obtainStyledAttributes(new int[]{
                android.R.attr.listDivider
        });
        mDrawable = a.getDrawable(0);
        a.recycle();
    }

    public VerticalListDividerItemDecoration(Context context, boolean ignoreParentPadding, @DrawableRes int drawableRes) {
        mIgnoreParentPadding = ignoreParentPadding;
        mDrawable = ContextCompat.getDrawable(context, drawableRes);
    }

    public VerticalListDividerItemDecoration(boolean ignoreParentPadding, Drawable drawable) {
        mDrawable = drawable;
        mIgnoreParentPadding = ignoreParentPadding;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        if (mDrawable == null) {
            return;
        }
        int left = (mIgnoreParentPadding) ? 0 : parent.getPaddingLeft();
        int right = (mIgnoreParentPadding) ? parent.getWidth() : (parent.getWidth() - parent.getPaddingRight());

        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            RecyclerView.ViewHolder nextHolder = ForwardingListener.findContainingViewHolder(parent, child);
            Integer prevViewType = findPrevViewType(parent, nextHolder);
            if (nextHolder != null && prevViewType != null && isAssignable(parent, nextHolder, prevViewType)) {
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
        Integer prevViewType = findPrevViewType(parent, nextHolder);
        if (mDrawable != null && nextHolder != null && prevViewType != null && isAssignable(parent, nextHolder, prevViewType)) {
            outRect.set(0, mDrawable.getIntrinsicHeight(), 0, 0);
        } else {
            outRect.set(0, 0, 0, 0);
        }
    }

    @Nullable
    private Integer findPrevViewType(@NonNull RecyclerView parent, @Nullable RecyclerView.ViewHolder nextHolder) {
        if (nextHolder == null) {
            return null;
        }
        RecyclerView.Adapter adapter = parent.getAdapter();
        if (adapter != null && nextHolder.getAdapterPosition() > 0) {
            return adapter.getItemViewType(nextHolder.getAdapterPosition() - 1);
        } else {
            return null;
        }
    }

    /**
     * Override this if needed.
     */
    public boolean isAssignable(@NonNull RecyclerView parent, RecyclerView.ViewHolder nextViewHolder, int prevViewType) {
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
}
