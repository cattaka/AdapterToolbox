package net.cattaka.android.adaptertoolbox.example;

import android.app.Activity;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;

import net.cattaka.android.adaptertoolbox.example.data.ActivityEntry;

import org.junit.Rule;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.contrib.RecyclerViewActions.scrollToPosition;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static net.cattaka.android.adaptertoolbox.example.test.TestUtils.monitorActivity;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

/**
 * Created by cattaka on 16/06/05.
 */
public class MainActivityTest {
    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class, false, false);

    @Test
    public void clickAll() {
        mActivityTestRule.launchActivity(null);
        List<ActivityEntry> dataPoinsts = pullDataPoints(new ArrayList<ActivityEntry>(), MainActivity.ACTIVITY_ENTRIES);
        for (int i = 0; i < dataPoinsts.size(); i++) {
            ActivityEntry item = dataPoinsts.get(i);
            if (item.getClazz() == null) {
                continue;
            }
            mActivityTestRule.getActivity();
            final int position = i;
            Activity nextActivity = monitorActivity(item.getClazz(), 5000, new Runnable() {
                @Override
                public void run() {
                    onView(withId(R.id.recycler)).perform(scrollToPosition(position));
                    onView(withId(R.id.recycler)).perform(actionOnItemAtPosition(position, click()));
                }
            });
            assertThat(nextActivity, is(notNullValue()));
            nextActivity.finish();
            InstrumentationRegistry.getInstrumentation().waitForIdleSync();
        }
    }

    private List<ActivityEntry> pullDataPoints(List<ActivityEntry> dest, List<ActivityEntry> entries) {
        for (ActivityEntry entry : entries) {
            dest.add(entry);
            if (entry.getChildren() != null) {
                pullDataPoints(dest, entry.getChildren());
            }
        }
        return dest;
    }
}
