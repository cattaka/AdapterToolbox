package net.cattaka.android.adaptertoolbox.example;

import android.content.res.Resources;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.view.View;
import android.widget.ListView;

import net.cattaka.android.adaptertoolbox.adapter.ScrambleAdapter;
import net.cattaka.android.adaptertoolbox.classic.ClassicScrambleAdapter;
import net.cattaka.android.adaptertoolbox.data.ICodeLabel;
import net.cattaka.android.adaptertoolbox.example.test.MockSnackbarLogic;
import net.cattaka.android.adaptertoolbox.example.test.TestUtils;

import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static net.cattaka.android.adaptertoolbox.example.test.TestUtils.withIdInAdapterView;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.contains;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

/**
 * Created by cattaka on 16/06/05.
 */
public class ClassicScrambleAdapterExampleActivityTest {
    @Rule
    public ActivityTestRule<ClassicScrambleAdapterExampleActivity> mActivityTestRule = new ActivityTestRule<>(ClassicScrambleAdapterExampleActivity.class, false, false);

    @Test
    public void clickAll() {
        ClassicScrambleAdapterExampleActivity activity = mActivityTestRule.launchActivity(null);
        ClassicScrambleAdapter<Object> csAdapter = (ClassicScrambleAdapter<Object>) activity.mListView.getAdapter();
        ScrambleAdapter<Object> adapter = csAdapter.getOrig();

        activity.mSnackbarLogic = spy(new MockSnackbarLogic());

        {
            TestUtils.Entry<Number> entry = TestUtils.find(adapter.getItems(), Number.class, 2);
            scrollListView(activity.mListView, entry.index);
            onView(withIdInAdapterView(R.id.text, R.id.list, entry.index)).perform(click());

            verify(activity.mSnackbarLogic).make(any(View.class), contains(String.valueOf(entry.object)), anyInt());
        }

        {
            TestUtils.Entry<String> entry = TestUtils.find(adapter.getItems(), String.class, 4);
            scrollListView(activity.mListView, entry.index);
            onView(withIdInAdapterView(R.id.text, R.id.list, entry.index)).perform(click());

            verify(activity.mSnackbarLogic).make(any(View.class), contains(String.valueOf(entry.object)), anyInt());
        }

        {
            TestUtils.Entry<ICodeLabel> entry = TestUtils.find(adapter.getItems(), ICodeLabel.class, 5);
            scrollListView(activity.mListView, entry.index);
            onView(withIdInAdapterView(R.id.text_code, R.id.list, entry.index)).perform(click());

            verify(activity.mSnackbarLogic).make(any(View.class), contains(String.valueOf(entry.object.getCode())), anyInt());
        }

        {
            TestUtils.Entry<ICodeLabel> entry = TestUtils.find(adapter.getItems(), ICodeLabel.class, 6);
            scrollListView(activity.mListView, entry.index);
            onView(withIdInAdapterView(R.id.text_code, R.id.list, entry.index)).perform(click());

            Resources res = activity.getResources();
            verify(activity.mSnackbarLogic).make(any(View.class), contains(String.valueOf(entry.object.getLabel(res))), anyInt());
        }
    }

    private void scrollListView(final ListView target, final int position) {
        InstrumentationRegistry.getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {
                target.setSelection(position);
            }
        });
        InstrumentationRegistry.getInstrumentation().waitForIdleSync();
    }
}
