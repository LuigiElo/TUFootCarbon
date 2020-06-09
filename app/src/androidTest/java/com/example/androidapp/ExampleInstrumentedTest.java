package com.example.androidapp;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;

import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.contrib.DrawerActions;
import androidx.test.espresso.contrib.NavigationViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;
import androidx.test.espresso.*;

import com.example.androidapp.ui.flights.FlightsFragment;

import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.DrawerMatchers.isClosed;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import static androidx.test.espresso.matcher.ViewMatchers.withSpinnerText;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static java.lang.Thread.sleep;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;


import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {


    @Rule
    public ActivityTestRule<DrawerActivity> activityTestRule = new ActivityTestRule<>(DrawerActivity.class);

    @Test
    public void testHomeScreen(){
        onView(withId(R.id.text_home2)).check(matches(isDisplayed()));
        onView(withId(R.id.text_home)).check(matches(isDisplayed()));
        onView(withId(R.id.text_home3)).check(matches(isDisplayed()));

    }

    @Test
    public void testFlightCalculation() throws InterruptedException {

        sleep(1000);
        onView(withId(R.id.drawer_layout))
                .check(matches(isClosed(Gravity.LEFT))) // Left Drawer should be closed.
                .perform(DrawerActions.open());

        onView(withId(R.id.nav_view))
                .perform(NavigationViewActions.navigateTo(R.id.nav_flights));

        //Avoids hierarchy errors
        sleep(1000);

        String iataCode1 = "Portugal, Funchal (Madeira) [FNC]";
        String iataCode2 = "Denmark, Copenhagen [CPH]";


        String flightClass = "economy";
        String resultExpected = "600.0248";
        onView(withId(R.id.departureSpinner)).perform(click());
        sleep(1000);
        onData(allOf(is(instanceOf(String.class)), is(iataCode1))).perform(click());
        sleep(1000);
        onView(withId(R.id.departureSpinner)).check(matches(withSpinnerText(containsString(iataCode1))));

        onView(withId(R.id.arrivalSpinner)).perform(click());
        sleep(1000);
        onData(allOf(is(instanceOf(String.class)), is(iataCode2))).perform(click());
        sleep(1000);
        onView(withId(R.id.arrivalSpinner)).check(matches(withSpinnerText(containsString(iataCode2))));

        onView(withId(R.id.classSpinner)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is(flightClass))).perform(click());
        onView(withId(R.id.classSpinner)).check(matches(withSpinnerText(containsString(flightClass))));

        onView(withId(R.id.journeysText)).perform(click()).perform(typeText("1"));
        onView(withId(R.id.isReturnSwitch)).perform(click());

        onView(withId(R.id.buttonFlights)).perform(click());
        sleep(1000);
        onView(withId(R.id.text_slideshow)).check(matches(withText(containsString(resultExpected))));


    }



    @Test
    public void testElectricityCalculation() throws InterruptedException {

        sleep(1000);
        onView(withId(R.id.drawer_layout))
                .check(matches(isClosed(Gravity.LEFT))) // Left Drawer should be closed.
                .perform(DrawerActions.open());

        onView(withId(R.id.nav_view))
                .perform(NavigationViewActions.navigateTo(R.id.nav_electricity));

        //Avoids hierarchy errors
        sleep(1000);


        String country = "Denmark";

        String resultExpected = "35.6";
        onView(withId(R.id.countriesSpinner)).perform(click());
        sleep(1000);
        onData(allOf(is(instanceOf(String.class)), is(country))).perform(click());
        sleep(1000);
        onView(withId(R.id.countriesSpinner)).check(matches(withSpinnerText(containsString(country))));


        onView(withId(R.id.editText)).perform(click()).perform(typeText("100"));
        sleep(1000);
        onView(withId(R.id.button3)).perform(click());

        sleep(1000);
        onView(withId(R.id.text_electricity)).check(matches(withText(containsString(resultExpected))));


    }

    @Test
    public void testWaterCalculation() throws InterruptedException {

        sleep(1000);
        onView(withId(R.id.drawer_layout))
                .check(matches(isClosed(Gravity.LEFT))) // Left Drawer should be closed.
                .perform(DrawerActions.open());

        onView(withId(R.id.nav_view))
                .perform(NavigationViewActions.navigateTo(R.id.nav_water_usage));

        //Avoids hierarchy errors
        sleep(1000);


        String country = "shower";

        String resultExpected = "26.574911";
        onView(withId(R.id.spinner)).perform(click());
        sleep(1000);
        onData(allOf(is(instanceOf(String.class)), is(country))).perform(click());
        sleep(1000);
        onView(withId(R.id.spinner)).check(matches(withSpinnerText(containsString(country))));


        onView(withId(R.id.waterText)).perform(click()).perform(typeText("1"));
        sleep(1000);
        onView(withId(R.id.button)).perform(click());

        sleep(1000);
        onView(withId(R.id.text_send)).check(matches(withText(containsString(resultExpected))));


    }


    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();

        assertEquals("com.example.androidapp", appContext.getPackageName());
    }



}
