package org.example.androidsdk.littlebox;

import android.support.test.espresso.contrib.PickerActions;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.TimePicker;

import org.hamcrest.Matchers;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

public class MainActivityUITest extends ActivityInstrumentationTestCase2<MainActivity> {

    public MainActivityUITest() {
        super(MainActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        getActivity();
    }

    public void testSetTime() {
        //testing values
        int start_hour = 10;
        int start_minutes = 59;
        int end_hour = 11;
        int end_minutes = 59;

        onView(withId(R.id.set_button)).perform(click()); //set button clicked
        onView(withClassName(Matchers.equalTo(TimePicker.class.getName()))).perform(PickerActions.setTime(start_hour, start_minutes)); //start time is set
        onView(withId(android.R.id.button1)).perform(click()); //ok button is clicked
        onView(withClassName(Matchers.equalTo(TimePicker.class.getName()))).perform(PickerActions.setTime(end_hour, end_minutes)); //end time is set
        onView(withId(android.R.id.button1)).perform(click()); //ok button is clicked
        onView(withId(R.id.start_time)).check(matches(withText(start_hour+":"+start_minutes))); //start_tv is checked
        onView(withId(R.id.end_time)).check(matches(withText(end_hour+":"+end_minutes))); //end_tv is checked

    }

}
