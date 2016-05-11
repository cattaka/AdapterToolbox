package net.cattaka.android.snippets.test;

import android.app.Activity;
import android.os.SystemClock;
import android.support.annotation.IdRes;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Checkable;
import android.widget.TextView;

import org.hamcrest.Matcher;
import org.hamcrest.Matchers;

import java.lang.reflect.Field;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.hamcrest.CoreMatchers.allOf;

import android.support.test.espresso.action.ViewActions;

/**
 * Created by takao on 2014/12/12.
 */
public class TestUtils {
    public static <T> T pickValue(Object obj, String name) throws Exception {
        Field field = obj.getClass().getDeclaredField(name);
        field.setAccessible(true);
        return (T) field.get(obj);
    }

    public static void replaceValue(Object obj, String name, Object value) throws Exception {
        Field field = obj.getClass().getDeclaredField(name);
        field.setAccessible(true);
        field.set(obj, value);
    }

    public static void performClick(final View view) throws Throwable {
        InstrumentationRegistry.getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {
                view.performClick();
            }
        });
        InstrumentationRegistry.getInstrumentation().waitForIdleSync();
    }

    public static void performClickMenuItem(final Toolbar.OnMenuItemClickListener listener, final Toolbar toolbar, @IdRes final int menuId) {
        InstrumentationRegistry.getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {
                MenuItem item = toolbar.getMenu().findItem(menuId);
                listener.onMenuItemClick(item);
            }
        });
        InstrumentationRegistry.getInstrumentation().waitForIdleSync();
    }

    /**
     * Wait for given function returns true on UI thread
     */
    public static boolean waitForTrue(final BooleanFunc func, int timeout) throws Throwable {
        long l = SystemClock.elapsedRealtime();
        do {
            final AtomicBoolean flag = new AtomicBoolean();
            InstrumentationRegistry.getInstrumentation().runOnMainSync(new Runnable() {
                @Override
                public void run() {
                    flag.set(func.run());
                }
            });
            if (flag.get()) {
                return true;
            }
            SystemClock.sleep(20);
        } while (SystemClock.elapsedRealtime() - l < timeout);

        return false;
    }

    public static boolean waitForActivityFinished(final Activity activity, int timeout) throws Throwable {
        return TestUtils.waitForTrue(new TestUtils.BooleanFunc() {
            @Override
            public boolean run() {
                return activity.isFinishing();
            }
        }, timeout);
    }

    public static boolean waitForFragmentResumed(final Fragment activity, int timeout) throws Throwable {
        return TestUtils.waitForTrue(new TestUtils.BooleanFunc() {
            @Override
            public boolean run() {
                return activity.isResumed();
            }
        }, timeout);
    }

    public static boolean waitForTextChanged(final TextView textView, final String expected, int timeout) throws Throwable {
        return TestUtils.waitForTrue(new TestUtils.BooleanFunc() {
            @Override
            public boolean run() {
                return String.valueOf(textView.getText()).equals(expected);
            }
        }, timeout);
    }

    public static boolean waitForCheckable(final Checkable view, final boolean checked, int timeout) throws Throwable {
        return TestUtils.waitForTrue(new TestUtils.BooleanFunc() {
            @Override
            public boolean run() {
                return view.isChecked() == checked;
            }
        }, timeout);
    }

    public static Matcher<View> withId(int id, Fragment fragment) {
        return allOf(ViewMatchers.withId(id), ViewMatchers.isDescendantOfA(Matchers.equalTo(fragment.getView())));
    }

    public static ViewAction scrollToEx() {
        return ViewActions.actionWithAssertions(new ScrollToExAction());
    }

    public static void showOverflowMenu(final Toolbar toolbar) {
        InstrumentationRegistry.getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {
                toolbar.showOverflowMenu();
            }
        });
        InstrumentationRegistry.getInstrumentation().waitForIdleSync();
    }

    public interface BooleanFunc {
        boolean run();
    }
}
