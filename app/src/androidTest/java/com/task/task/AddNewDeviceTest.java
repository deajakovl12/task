package com.task.task;

import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.NoMatchingRootException;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.ActivityInstrumentationTestCase2;

import com.task.task.ui.home.HomeActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.isDialog;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.task.task.utils.Constants.HomeActivityConstants.DATA_INFO;
import static com.task.task.utils.Constants.HomeActivityConstants.DATA_LOADED_FROM_DB;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class AddNewDeviceTest extends ActivityInstrumentationTestCase2<HomeActivity> {

    public AddNewDeviceTest() {
        super(HomeActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        getActivity();
    }

    private void grantPermissions(String permission) {
        InstrumentationRegistry.getInstrumentation().getUiAutomation().executeShellCommand(
                "pm grant " + InstrumentationRegistry.getTargetContext().getPackageName()
                        + " " + permission);
    }

    @Rule
    public ActivityTestRule<HomeActivity> activityRule = new ActivityTestRule<HomeActivity>(HomeActivity.class) {
        @Override
        protected Intent getActivityIntent() {

            grantPermissions("android.permission.READ_EXTERNAL_STORAGE");
            grantPermissions("android.permission.ACCESS_COARSE_LOCATION");
            grantPermissions("android.permission.ACCESS_FINE_LOCATION");

            Intent intent = new Intent(InstrumentationRegistry.getContext(), HomeActivity.class);
            intent.putExtra(DATA_INFO, DATA_LOADED_FROM_DB);
            return intent;
        }
    };

    @Test
    public void addNewRestaurant() throws Exception {

        onView(withId(R.id.home_activity_recycler_view))
                .perform(RecyclerViewActions.scrollToPosition(20));
        onView(withId(R.id.home_activity_recycler_view)).perform(RecyclerViewActions.actionOnItemAtPosition(20, click()));
        onView(withId(R.id.activity_restaurant_details_restaurant_name))
                .perform(clearText())
                .perform(typeText("PRESSING BACK"),
                        closeSoftKeyboard());
        Espresso.pressBack();
        onView(withId(R.id.home_activity_recycler_view)).perform(RecyclerViewActions.actionOnItemAtPosition(20, click()));
        onView(withId(R.id.activity_restaurant_details_restaurant_name))
                .perform(clearText())
                .perform(typeText("PRESSING SAVE"),
                        closeSoftKeyboard());
        onView(withId(R.id.activity_restaurant_details_save))
                .perform(click());
        onView(withId(R.id.home_activity_fab))
                .perform(click());

        try {
            onView(withText(R.string.gps_not_found_title))
                    .inRoot(isDialog())
                    .check(matches(isDisplayed()));
            onView(withId(android.R.id.button2))
                    .inRoot(isDialog())
                    .check(matches(withText(R.string.no)))
                    .check(matches(isDisplayed()));
            onView(withId(android.R.id.button2))
                    .inRoot(isDialog())
                    .perform(click());
            pressBack();
        } catch (NoMatchingRootException e) {
            onView(withId(R.id.activity_restaurant_details_save))
                    .perform(click());
            onView(withId(R.id.activity_restaurant_details_restaurant_name))
                    .perform(clearText())
                    .perform(typeText("NEW RESTAURANT"),
                            closeSoftKeyboard());
            onView(withId(R.id.activity_restaurant_details_restaurant_address))
                    .perform(clearText())
                    .perform(typeText("NEW ADDRESS"),
                            closeSoftKeyboard());
            onView(withId(R.id.activity_restaurant_details_save))
                    .perform(click());
        }

        onView(withId(R.id.home_activity_google_map))
                .perform(click());
    }

}