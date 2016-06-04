package net.cattaka.android.adaptertoolbox.example.test;

import android.app.Activity;
import android.app.Instrumentation;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.test.InstrumentationRegistry;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;

import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.allOf;

/**
 * Created by cattaka on 16/06/04.
 */
public class TestUtils {
    @SuppressWarnings("unchecked")
    public static <T extends Activity> T monitorActivity(@NonNull Class<T> activityClass, int timeOut, @NonNull Runnable runnable) {
        Instrumentation.ActivityMonitor monitor = new Instrumentation.ActivityMonitor(activityClass.getCanonicalName(), null, false);
        try {
            InstrumentationRegistry.getInstrumentation().addMonitor(monitor);
            runnable.run();
            return (T) monitor.waitForActivityWithTimeout(timeOut);
        } finally {
            InstrumentationRegistry.getInstrumentation().removeMonitor(monitor);
        }
    }

    public static Matcher<View> withIdInRecyclerView(int id, int recyclerViewId, int position) {
        return allOf(withId(id), isDescendantOfRecyclerView(recyclerViewId, position));
    }

    public static Matcher<View> isDescendantOfRecyclerView(final int recyclerViewId, final int position) {
        return new BaseMatcher<View>() {
            @Override
            public boolean matches(Object arg) {
                if (arg instanceof View) {
                    View v = (View) arg;
                    View parent = v;
                    while (parent.getParent() != null && parent.getParent() instanceof View) {
                        if (parent.getId() == recyclerViewId && parent instanceof RecyclerView) {
                            RecyclerView.ViewHolder holder = findContainingViewHolder((RecyclerView) parent, v);
                            if (holder != null && holder.getAdapterPosition() == position) {
                                return true;
                            }
                        }
                        parent = (View) parent.getParent();
                    }
                }
                return false;
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("isDescendantOfRecyclerView(")
                        .appendValue(InstrumentationRegistry.getTargetContext().getResources().getResourceEntryName(recyclerViewId))
                        .appendText(",")
                        .appendValue(position)
                        .appendText(")");
            }
        };
    }

    /* This method is implemented in newer RecyclerView. */
    @Nullable
    public static RecyclerView.ViewHolder findContainingViewHolder(RecyclerView recyclerView, View view) {
        View v = view;
        while (v != null && v.getParent() instanceof View) {
            if (v.getParent() == recyclerView) {
                return recyclerView.getChildViewHolder(v);
            }
            v = (View) v.getParent();
        }
        return null;
    }
}
