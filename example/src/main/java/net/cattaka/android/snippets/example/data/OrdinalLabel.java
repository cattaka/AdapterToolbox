package net.cattaka.android.snippets.example.data;

import android.content.res.Resources;
import android.support.annotation.StringRes;

import net.cattaka.android.snippets.adapter.ICodeLabel;
import net.cattaka.android.snippets.example.R;

/**
 * Created by cattaka on 16/05/02.
 */
public enum OrdinalLabel implements ICodeLabel {
    FIRST("first", R.string.ordinal_label_first),
    SECOND("first", R.string.ordinal_label_second),
    THIRD("first", R.string.ordinal_label_third),
    FOURTH("first", R.string.ordinal_label_fourth),
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
