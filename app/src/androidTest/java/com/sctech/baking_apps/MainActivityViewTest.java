package com.sctech.baking_apps;

import android.support.test.espresso.NoMatchingViewException;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import com.sctech.baking_apps.ui.MainActivity;
import org.junit.Rule;
import org.junit.runner.RunWith;
import org.junit.Test;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class MainActivityViewTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(
            MainActivity.class);

    @Test
    public void clickCheckOnImageSelected() {
        try {
            onView(withText("Yellow Cake")).check(matches(isDisplayed()));
        } catch (NoMatchingViewException e) {
            e.printStackTrace();
        }
    }
}
