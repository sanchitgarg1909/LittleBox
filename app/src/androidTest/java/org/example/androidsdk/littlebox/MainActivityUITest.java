package org.example.androidsdk.littlebox;

import android.os.IBinder;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Root;
import android.support.test.espresso.contrib.PickerActions;
import android.test.ActivityInstrumentationTestCase2;
import android.view.WindowManager;
import android.widget.TimePicker;

import com.squareup.okhttp.mockwebserver.MockResponse;
import com.squareup.okhttp.mockwebserver.MockWebServer;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.hamcrest.TypeSafeMatcher;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
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
        server = new MockWebServer(); //using mockwebserver to mock server's response
        server.start();
        injectInstrumentation(InstrumentationRegistry.getInstrumentation());
        Constants.BASE_URL = server.url("/").toString();
    }

    public void testSetTime() {
        getActivity();
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

        String fileName = "get_response.json";
        server.enqueue(new MockResponse()
                .setResponseCode(200)
                .setBody(RestServiceTestHelper.getStringFromFile(getInstrumentation().getContext(), fileName)));

        getActivity();
        onView(withId(R.id.start_time)).check(matches(withText("9:00")));
        onView(withId(R.id.end_time)).check(matches(withText("18:00")));

    }

    public void testGetWorkingHoursError() throws Exception {

        String fileName = "error_response.json";
        server.enqueue(new MockResponse()
                .setResponseCode(404)
                .setBody(RestServiceTestHelper.getStringFromFile(getInstrumentation().getContext(), fileName)));

        getActivity();

        onView(withText("User id not found")).inRoot(isToast()).check(matches(isDisplayed()));
    }

    //custom matcher for checking Toast message
    public static Matcher<Root> isToast() {
        return new TypeSafeMatcher<Root>() {

            @Override
            public void describeTo(Description description) {
                description.appendText("is toast");
            }

            @Override
            public boolean matchesSafely(Root root) {
                int type = root.getWindowLayoutParams().get().type;
                if ((type == WindowManager.LayoutParams.TYPE_TOAST)) {
                    IBinder windowToken = root.getDecorView().getWindowToken();
                    IBinder appToken = root.getDecorView().getApplicationWindowToken();
                    if (windowToken == appToken) {
                        // windowToken == appToken means this window isn't contained by any other windows.
                        // if it was a window for an activity, it would have TYPE_BASE_APPLICATION.
                        return true;
                    }
                }
                return false;
            }
        };
    }
}
