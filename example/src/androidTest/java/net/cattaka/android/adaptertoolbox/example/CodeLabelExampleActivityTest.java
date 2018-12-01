package net.cattaka.android.adaptertoolbox.example;

import android.content.res.Resources;
import android.support.test.rule.ActivityTestRule;
import android.view.View;

import net.cattaka.android.adaptertoolbox.example.data.OrdinalLabel;
import net.cattaka.android.adaptertoolbox.example.test.MockSnackbarLogic;

import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.contains;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

/**
 * Created by cattaka on 16/06/05.
 */
public class CodeLabelExampleActivityTest {
    @Rule
    public ActivityTestRule<CodeLabelExampleActivity> mActivityTestRule = new ActivityTestRule<CodeLabelExampleActivity>(CodeLabelExampleActivity.class, false, false);

    @Test
    public void clickSpinner() {
        CodeLabelExampleActivity activity = mActivityTestRule.launchActivity(null);
        activity.mSnackbarLogic = spy(new MockSnackbarLogic());

        for (OrdinalLabel item : OrdinalLabel.values()) {
            onView(withId(R.id.spinner)).perform(click());
            onData(is(item)).perform(click());

            Resources res = activity.getResources();
            verify(activity.mSnackbarLogic).make(any(View.class), contains(item.getLabel(res)), anyInt());
        }
    }
}
