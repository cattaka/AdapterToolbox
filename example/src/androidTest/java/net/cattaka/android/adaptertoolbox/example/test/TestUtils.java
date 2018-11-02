package net.cattaka.android.adaptertoolbox.example.test;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.res.Resources;
import android.os.SystemClock;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.test.InstrumentationRegistry;
import androidx.test.espresso.IdlingResource;
import androidx.test.espresso.NoMatchingViewException;
import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.ViewAssertion;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ProgressBar;
import android.widget.SeekBar;

import net.cattaka.android.adaptertoolbox.thirdparty.MergeRecyclerAdapter;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;

import java.util.List;

import static androidx.test.espresso.matcher.ViewMatchers.withId;
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
                        if ((recyclerViewId == -1 || parent.getId() == recyclerViewId)
                                && parent instanceof RecyclerView) {
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
                String resName = String.valueOf(recyclerViewId);
                try {
                    resName = InstrumentationRegistry.getTargetContext().getResources().getResourceEntryName(recyclerViewId);
                } catch (Resources.NotFoundException e) {
                    // ignore
                }
                description.appendText("isDescendantOfRecyclerView(")
                        .appendValue(resName)
                        .appendText(",")
                        .appendValue(position)
                        .appendText(")");
            }
        };
    }

    public static Matcher<View> withIdInAdapterView(int id, int adapterViewId, int position) {
        return allOf(withId(id), isDescendantOfAdapterView(adapterViewId, position));
    }

    public static Matcher<View> isDescendantOfAdapterView(final int adapterViewId, final int position) {
        return new BaseMatcher<View>() {
            @Override
            public boolean matches(Object arg) {
                if (arg instanceof View) {
                    View view = (View) arg;
                    while (view.getParent() != null && view.getParent() instanceof View) {
                        View parent = (View) view.getParent();
                        if ((adapterViewId == -1 || parent.getId() == adapterViewId)
                                && parent instanceof AdapterView) {
                            AdapterView adapterView = ((AdapterView) parent);
                            int offsetPosition = position - adapterView.getFirstVisiblePosition();
                            if (0 <= offsetPosition && offsetPosition < adapterView.getChildCount()
                                    && adapterView.getChildAt(offsetPosition) == view) {
                                return true;
                            }
                        }
                        view = parent;
                    }
                }
                return false;
            }

            @Override
            public void describeTo(Description description) {
                String resName = String.valueOf(adapterViewId);
                try {
                    resName = InstrumentationRegistry.getTargetContext().getResources().getResourceEntryName(adapterViewId);
                } catch (Resources.NotFoundException e) {
                    // ignore
                }
                description.appendText("isDescendantOfAdapterView(")
                        .appendValue(resName)
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

    public static ViewAction setProgress(final int progress) {
        return new ViewAction() {
            @Override
            public void perform(UiController uiController, View view) {
                ((SeekBar) view).setProgress(progress);
            }

            @Override
            public String getDescription() {
                return "Set a progress";
            }

            @Override
            public Matcher<View> getConstraints() {
                return ViewMatchers.isAssignableFrom(SeekBar.class);
            }
        };
    }

    public static ViewAssertion hasProgress(final Matcher<Integer> progress) {
        return new ViewAssertion() {
            @Override
            public void check(View view, NoMatchingViewException noViewFoundException) {
                if (view instanceof ProgressBar) {
                    if (progress.matches(((ProgressBar) view).getProgress())) {
                        return;
                    }
                }
                throw noViewFoundException;
            }
        };
    }

    public static ViewAssertion hasSelectedItem(final Matcher<? extends Object> item) {
        return new ViewAssertion() {
            @Override
            public void check(View view, NoMatchingViewException noViewFoundException) {
                if (view instanceof AdapterView) {
                    if (item.matches(((AdapterView) view).getSelectedItem())) {
                        return;
                    }
                }
                throw noViewFoundException;
            }
        };
    }

    /**
     * To be truthful, We should use Espresso#registerIdlingResource.
     * But sometime it become too slow.
     */
    public static void waitForIdlingResource(IdlingResource idlingResource, int timeout) {
        long l = SystemClock.elapsedRealtime();
        do {
            if (idlingResource.isIdleNow()) {
                break;
            }
            SystemClock.sleep(100);
        } while (SystemClock.elapsedRealtime() - l > timeout);
    }

    public static int calcPositionOffset(MergeRecyclerAdapter mergeRecyclerAdapter, RecyclerView.Adapter adapter) {
        int offset = 0;
        for (int i = 0; i < mergeRecyclerAdapter.getSubAdapterCount(); i++) {
            RecyclerView.Adapter a = mergeRecyclerAdapter.getSubAdapter(i);
            if (a == adapter) {
                break;
            }
            offset += a.getItemCount();
        }
        return offset;
    }


    @SuppressWarnings("unchecked")
    public static <T extends RecyclerView.Adapter> T find(@Nullable MergeRecyclerAdapter mergeRecyclerAdapter, @NonNull Class<T> clazz, int position) {
        if (mergeRecyclerAdapter == null) {
            return null;
        }
        int count = 0;
        for (int i = 0; i < mergeRecyclerAdapter.getSubAdapterCount(); i++) {
            RecyclerView.Adapter item = mergeRecyclerAdapter.getSubAdapter(i);
            if (clazz.isInstance(item)) {
                if (count == position) {
                    return (T) item;
                }
                count++;
            }
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public static <T> Entry<T> find(@Nullable List<?> items, @NonNull Class<T> clazz, int position) {
        if (items == null) {
            return null;
        }
        int count = 0;
        for (int i = 0; i < items.size(); i++) {
            Object item = items.get(i);
            if (clazz.isInstance(item)) {
                if (count == position) {
                    return new Entry<>(i, (T) item);
                }
                count++;
            }
        }
        return null;
    }

    public static class Entry<T> {
        public final int index;
        public final T object;

        public Entry(int index, T object) {
            this.index = index;
            this.object = object;
        }
    }
}
