package net.cattaka.android.adaptertoolbox.example.test;

import android.support.annotation.NonNull;
import android.view.View;

import net.cattaka.android.adaptertoolbox.example.logic.SnackbarLogic;

public class MockSnackbarLogic extends SnackbarLogic {
    @NonNull
    @Override
    public ISnackbarWrapper make(@NonNull View view, @NonNull CharSequence text, int duration) {
        return new MockSnackbarWrapper();
    }

    @NonNull
    @Override
    public ISnackbarWrapper make(@NonNull View view, int resId, int duration) {
        return new MockSnackbarWrapper();
    }

    public static class MockSnackbarWrapper implements ISnackbarWrapper {
        @Override
        public void show() {
            // no-op
        }
    }
}
