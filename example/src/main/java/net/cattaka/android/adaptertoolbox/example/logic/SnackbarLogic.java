package net.cattaka.android.adaptertoolbox.example.logic;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import com.google.android.material.snackbar.Snackbar;
import android.view.View;

/**
 * Created by cattaka on 16/06/05.
 */
public class SnackbarLogic {
    public SnackbarLogic() {
    }

    @NonNull
    public Snackbar make(@NonNull View view, @NonNull CharSequence text, int duration) {
        return Snackbar.make(view, text, duration);
    }

    @NonNull
    public Snackbar make(@NonNull View view, @StringRes int resId, int duration) {
        return Snackbar.make(view, resId, duration);
    }
}
