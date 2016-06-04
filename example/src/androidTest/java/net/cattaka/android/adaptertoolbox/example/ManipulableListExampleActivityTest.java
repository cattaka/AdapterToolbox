package net.cattaka.android.adaptertoolbox.example;

import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.rule.ActivityTestRule;
import android.view.View;

import net.cattaka.android.adaptertoolbox.adapter.ScrambleAdapter;
import net.cattaka.android.adaptertoolbox.example.data.MyInfo;
import net.cattaka.android.adaptertoolbox.example.data.OrdinalLabel;
import net.cattaka.android.adaptertoolbox.example.data.TextInfo;
import net.cattaka.android.adaptertoolbox.example.logic.SnackbarLogic;
import net.cattaka.android.adaptertoolbox.example.test.TestUtils;
import net.cattaka.android.adaptertoolbox.thirdparty.MergeRecyclerAdapter;

import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.contrib.RecyclerViewActions.scrollToPosition;
import static android.support.test.espresso.matcher.ViewMatchers.isChecked;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static net.cattaka.android.adaptertoolbox.example.test.TestUtils.find;
import static net.cattaka.android.adaptertoolbox.example.test.TestUtils.hasProgress;
import static net.cattaka.android.adaptertoolbox.example.test.TestUtils.hasSelectedItem;
import static net.cattaka.android.adaptertoolbox.example.test.TestUtils.setProgress;
import static net.cattaka.android.adaptertoolbox.example.test.TestUtils.withIdInRecyclerView;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.argThat;
import static org.mockito.Matchers.matches;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

/**
 * Created by cattaka on 16/06/05.
 */
public class ManipulableListExampleActivityTest {
    @Rule
    public ActivityTestRule<ManipulableListExampleActivity> mActivityTestRule = new ActivityTestRule<>(ManipulableListExampleActivity.class, false, false);

    @Test
    public void editMyInfo() {
        ManipulableListExampleActivity activity = mActivityTestRule.launchActivity(null);
        MergeRecyclerAdapter mergeRecyclerAdapter = (MergeRecyclerAdapter) activity.mRecyclerView.getAdapter();
        ScrambleAdapter headerAdapter = (ScrambleAdapter) mergeRecyclerAdapter.getSubAdapter(0);
        ScrambleAdapter adapter = (ScrambleAdapter) mergeRecyclerAdapter.getSubAdapter(1);

        activity.mSnackbarLogic = spy(new SnackbarLogic());

        {   // Edit MyInfo
            int position = 1;
            TestUtils.Entry<MyInfo> entry = find(adapter.getItems(), MyInfo.class, position);
            int globalPosition = entry.index + headerAdapter.getItemCount();
            onView(withId(R.id.recycler)).perform(scrollToPosition(globalPosition));

            // Set value to seekBar
            onView(withIdInRecyclerView(R.id.seek_int_value, R.id.recycler, globalPosition)).perform(setProgress(23));
            // Select spinner item
            onView(withIdInRecyclerView(R.id.spinner_ordinal_label, R.id.recycler, globalPosition)).perform(click());
            onData(is(OrdinalLabel.SECOND)).perform(click());
            // Click switch
            onView(withIdInRecyclerView(R.id.switch_checked, R.id.recycler, globalPosition)).perform(click());
        }
        {   // Once scroll out
            onView(withId(R.id.recycler)).perform(scrollToPosition(adapter.getItemCount() - 1));
        }
        {   // Check MyInfo
            int position = 1;
            TestUtils.Entry<MyInfo> entry = find(adapter.getItems(), MyInfo.class, position);
            int globalPosition = entry.index + headerAdapter.getItemCount();
            onView(withId(R.id.recycler)).perform(scrollToPosition(globalPosition));

            // Set value to seekBar
            onView(withIdInRecyclerView(R.id.seek_int_value, R.id.recycler, globalPosition))
                    .check(hasProgress(is(23)));
            // Select spinner item
            onView(withIdInRecyclerView(R.id.spinner_ordinal_label, R.id.recycler, globalPosition))
                    .check(hasSelectedItem(is(OrdinalLabel.SECOND)));
            // Click switch
            onView(withIdInRecyclerView(R.id.switch_checked, R.id.recycler, globalPosition))
                    .check(ViewAssertions.matches(isChecked()));
        }
    }

    @Test
    public void editTextInfo() {
        ManipulableListExampleActivity activity = mActivityTestRule.launchActivity(null);
        MergeRecyclerAdapter mergeRecyclerAdapter = (MergeRecyclerAdapter) activity.mRecyclerView.getAdapter();
        ScrambleAdapter headerAdapter = (ScrambleAdapter) mergeRecyclerAdapter.getSubAdapter(0);
        ScrambleAdapter adapter = (ScrambleAdapter) mergeRecyclerAdapter.getSubAdapter(1);

        activity.mSnackbarLogic = spy(new SnackbarLogic());

        {   // Edit TextInfo
            int position = 2;
            TestUtils.Entry<TextInfo> entry = find(adapter.getItems(), TextInfo.class, position);
            int globalPosition = entry.index + headerAdapter.getItemCount();
            onView(withId(R.id.recycler)).perform(scrollToPosition(globalPosition));
            // Input text
            onView(withIdInRecyclerView(R.id.edit_text, R.id.recycler, globalPosition)).perform(replaceText("hoge fuga foo bar"));
        }
        {   // Once scroll out
            onView(withId(R.id.recycler)).perform(scrollToPosition(adapter.getItemCount() - 1));
        }
        {   // Check TextInfo
            int position = 2;
            TestUtils.Entry<TextInfo> entry = find(adapter.getItems(), TextInfo.class, position);
            int globalPosition = entry.index + headerAdapter.getItemCount();
            onView(withId(R.id.recycler)).perform(scrollToPosition(globalPosition));
            // Check inputted text
            onView(withIdInRecyclerView(R.id.edit_text, R.id.recycler, globalPosition))
                    .check(ViewAssertions.matches(withText("hoge fuga foo bar")));
        }
    }
}
