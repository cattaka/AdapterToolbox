package net.cattaka.android.adaptertoolbox.utils;

import android.widget.Spinner;

/**
 * Created by cattaka on 16/05/15.
 */
public class SpinnerUtils {
    public static boolean selectSpinnerValue(Spinner spinner, Object value) {
        if (value == null) {
            for (int i = 0; i < spinner.getCount(); i++) {
                if (spinner.getItemAtPosition(i) == null) {
                    spinner.setSelection(i);
                    return true;
                }
            }
            return false;
        }
        for (int i = 0; i < spinner.getCount(); i++) {
            if (value.equals(spinner.getItemAtPosition(i))) {
                spinner.setSelection(i);
                return true;
            }
        }
        return false;
    }
}
