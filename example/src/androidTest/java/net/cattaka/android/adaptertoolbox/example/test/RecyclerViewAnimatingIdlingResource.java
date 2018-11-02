package net.cattaka.android.adaptertoolbox.example.test;

import androidx.test.espresso.IdlingResource;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by cattaka on 16/06/05.
 */
public class RecyclerViewAnimatingIdlingResource implements IdlingResource {
    RecyclerView mRecyclerView;
    ResourceCallback mCallback;

    public RecyclerViewAnimatingIdlingResource(RecyclerView recyclerView) {
        mRecyclerView = recyclerView;
    }

    @Override
    public void registerIdleTransitionCallback(ResourceCallback callback) {
        mCallback = callback;
    }

    @Override
    public boolean isIdleNow() {
        if (!mRecyclerView.isAnimating()) {
            if (mCallback != null) {
                mCallback.onTransitionToIdle();
            }
            return true;
        } else {
            return false;
        }
    }

    @Override
    public String getName() {
        return "RecyclerViewAnimatingIdlingResource";
    }
}
