package net.cattaka.android.adaptertoolbox.example.logic;

import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.view.View;

/**
 * Created by cattaka on 16/06/05.
 */
public class SnackbarLogic {
    public SnackbarLogic() {
    }

    @NonNull
    public ISnackbarWrapper make(@NonNull View view, @NonNull CharSequence text, int duration) {
        return new SnackbarWrapperImpl(Snackbar.make(view, text, duration));
    }

    @NonNull
    public ISnackbarWrapper make(@NonNull View view, @StringRes int resId, int duration) {
        return new SnackbarWrapperImpl(Snackbar.make(view, resId, duration));
    }

    /**
     * For InstrumentationTest
     */
    public interface ISnackbarWrapper {
        void show();
    }

    private static class SnackbarWrapperImpl implements ISnackbarWrapper {
        private Snackbar orig;

        public SnackbarWrapperImpl(Snackbar orig) {
            this.orig = orig;
        }

        @Override
        public void show() {
            orig.show();
        }
    }
}
