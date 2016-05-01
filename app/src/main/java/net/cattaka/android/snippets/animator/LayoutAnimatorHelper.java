package net.cattaka.android.snippets.animator;

import android.support.annotation.Keep;
import android.view.View;
import android.view.ViewGroup;

/**
 * Save all methods from proguard!!
 * <p/>
 * Created by cattaka on 2016/04/15.
 */
@Keep
public class LayoutAnimatorHelper {
    View mView;

    public LayoutAnimatorHelper(View view) {
        mView = view;
    }

    public int getTopMargin() {
        return ((ViewGroup.MarginLayoutParams) mView.getLayoutParams()).topMargin;
    }

    public void setTopMargin(int v) {
        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) mView.getLayoutParams();
        params.topMargin = v;
        mView.setLayoutParams(params);
    }

    public int getLeftMargin() {
        return ((ViewGroup.MarginLayoutParams) mView.getLayoutParams()).leftMargin;
    }

    public void setLeftMargin(int v) {
        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) mView.getLayoutParams();
        params.leftMargin = v;
        mView.setLayoutParams(params);
    }

    public int getRightMargin() {
        return ((ViewGroup.MarginLayoutParams) mView.getLayoutParams()).rightMargin;
    }

    public void setRightMargin(int v) {
        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) mView.getLayoutParams();
        params.rightMargin = v;
        mView.setLayoutParams(params);
    }

    public int getBottomMargin() {
        return ((ViewGroup.MarginLayoutParams) mView.getLayoutParams()).bottomMargin;
    }

    public void setBottomMargin(int v) {
        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) mView.getLayoutParams();
        params.bottomMargin = v;
        mView.setLayoutParams(params);
    }

    public int getWidth() {
        return (mView.getLayoutParams()).width;
    }

    public void setWidth(int v) {
        ViewGroup.LayoutParams params = mView.getLayoutParams();
        params.width = v;
        mView.setLayoutParams(params);
    }

    public int getHeight() {
        return (mView.getLayoutParams()).height;
    }

    public void setHeight(int v) {
        ViewGroup.LayoutParams params = mView.getLayoutParams();
        params.height = v;
        mView.setLayoutParams(params);
    }
}
