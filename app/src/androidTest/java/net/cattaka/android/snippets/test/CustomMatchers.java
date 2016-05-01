package net.cattaka.android.snippets.test;

import android.support.test.espresso.matcher.BoundedMatcher;
import android.widget.EditText;

import org.hamcrest.Matcher;

/**
 * Created by cattaka on 16/05/02.
 */
public class CustomMatchers {
    public static Matcher<Object> hasInputType(final int inputType) {
        return new BoundedMatcher<Object, EditText>(EditText.class) {
            int v;

            @Override
            protected boolean matchesSafely(EditText editText) {
                v = editText.getInputType();
                return v == inputType;
            }

            @Override
            public void describeTo(org.hamcrest.Description description) {
                description.appendText("has inputyType:" + inputType + ", but has " + v);
            }
        };
    }
}
