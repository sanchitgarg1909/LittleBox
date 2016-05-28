package org.example.androidsdk.littlebox;

import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.contrib.PickerActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.TimePicker;

import com.squareup.okhttp.mockwebserver.MockResponse;
import com.squareup.okhttp.mockwebserver.MockWebServer;

import org.hamcrest.Matchers;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

public class MainActivityUITest extends ActivityInstrumentationTestCase2<MainActivity> {

    private MockWebServer server;

    public MainActivityUITest() {
        super(MainActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        server = new MockWebServer();
        server.start();
        injectInstrumentation(InstrumentationRegistry.getInstrumentation());
        Constants.BASE_URL = server.url("/").toString();


        String fileName = "response.json";
        server.enqueue(new MockResponse()
                .setResponseCode(200)
                .setBody(RestServiceTestHelper.getStringFromFile(getInstrumentation().getContext(), fileName)));

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

    public void testGetWorkingHours() throws Exception {
        onView(withId(R.id.start_time)).check(matches(withText("9:00")));
        onView(withId(R.id.end_time)).check(matches(withText("18:00")));
    }
}
