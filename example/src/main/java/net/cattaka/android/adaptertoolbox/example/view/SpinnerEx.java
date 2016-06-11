package net.cattaka.android.adaptertoolbox.example.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.Spinner;

import net.cattaka.android.adaptertoolbox.utils.SpinnerUtils;

/**
 * Created by takao on 2016/06/11.
 */
public class SpinnerEx extends Spinner {
    public SpinnerEx(Context context) {
        super(context);
    }

    public SpinnerEx(Context context, int mode) {
        super(context, mode);
    }

    public SpinnerEx(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SpinnerEx(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public SpinnerEx(Context context, AttributeSet attrs, int defStyleAttr, int mode) {
        super(context, attrs, defStyleAttr, mode);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public SpinnerEx(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes, int mode) {
        super(context, attrs, defStyleAttr, defStyleRes, mode);
    }

    @TargetApi(Build.VERSION_CODES.M)
    public SpinnerEx(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes, int mode, Resources.Theme popupTheme) {
        super(context, attrs, defStyleAttr, defStyleRes, mode, popupTheme);
    }

    public Object getSelectedItem() {
        return getItemAtPosition(getSelectedItemPosition());
    }

    public void setSelectedItem(Object value) {
        SpinnerUtils.selectSpinnerValue(this, value);
    }
}
