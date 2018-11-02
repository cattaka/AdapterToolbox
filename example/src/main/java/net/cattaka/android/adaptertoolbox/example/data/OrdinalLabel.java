package net.cattaka.android.adaptertoolbox.example.data;

import android.content.res.Resources;
import androidx.annotation.StringRes;

import net.cattaka.android.adaptertoolbox.data.ICodeLabel;
import net.cattaka.android.adaptertoolbox.example.R;

/**
 * Created by cattaka on 16/05/02.
 */
public enum OrdinalLabel implements ICodeLabel {
    FIRST("first", R.string.ordinal_label_first),
    SECOND("second", R.string.ordinal_label_second),
    THIRD("third", R.string.ordinal_label_third),
    FOURTH("fourth", R.string.ordinal_label_fourth),
    //
    ;

    final String code;
    @StringRes
    final int resId;

    OrdinalLabel(String code, @StringRes int resId) {
        this.code = code;
        this.resId = resId;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getLabel(Resources res) {
        return res.getString(resId);
    }
}
