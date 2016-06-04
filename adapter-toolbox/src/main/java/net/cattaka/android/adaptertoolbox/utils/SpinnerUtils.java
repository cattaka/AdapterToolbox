package net.cattaka.android.adaptertoolbox.utils;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.PopupWindow;
import android.widget.Spinner;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by cattaka on 16/05/15.
 */
public class SpinnerUtils {
    public static boolean selectSpinnerValue(@NonNull Spinner spinner, Object value) {
        return selectSpinnerValue(spinner, value, false);
    }

    public static boolean selectSpinnerValue(@NonNull Spinner spinner, Object value, boolean cancelListener) {
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

    public static void setSelection(@NonNull Spinner spinner, int position, boolean cancelListener) {
        if (cancelListener) {
            AdapterView.OnItemSelectedListener listener = spinner.getOnItemSelectedListener();
            if (listener != null) {
                spinner.setOnItemSelectedListener(null);
            }
            spinner.setSelection(position);
            if (listener != null) {
                spinner.setOnItemSelectedListener(listener);
            }
        } else {
            spinner.setSelection(position);
        }
    }

    public static void dismissPopup(@NonNull Spinner spinner) {
        // Does not come to mind is a good way to other...
        Class<?> clazz = spinner.getClass();
        Object popup = null;
        while(popup == null && clazz != null) {
            try {
                Field field = clazz.getDeclaredField("mPopup");
                field.setAccessible(true);
                popup = field.get(spinner);
                if (popup != null) {
                    popup.getClass().getMethod("dismiss").invoke(popup);
                    return;
                }
            } catch (Exception e) {
                // ignore
            }
            clazz = clazz.getSuperclass();
        }
    }
}
