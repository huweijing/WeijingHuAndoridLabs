package com.example.weijinghusandoridlabs;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.ViewInteraction;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;



@LargeTest
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityScenarioRule<MainActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(MainActivity.class);

    /**
     * a test case for requirement making sure that it can detect when a password with only digits
     *
     */
    @Test
    public void mainActivityTest() {
        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction appCompatEditText = onView( withId(R.id.editTextPassword));
     //           allOf(withId(R.id.editTextPassword),
    //                    childAtPosition(
    //                            childAtPosition(
    //                                    withId(android.R.id.content),
    //                                    0),
    //                            1),


    //                    isDisplayed()));
        appCompatEditText.perform(replaceText("12345"), closeSoftKeyboard());

        ViewInteraction materialButton = onView( withId(R.id.buttonLogin));
     //           allOf(withId(R.id.buttonLogin), withText("Login"),
     //                   childAtPosition(
     //                           childAtPosition(
     //                                   withId(android.R.id.content),
     //                                   0),
      //                          1),
     //                   isDisplayed()));
        materialButton.perform(click());

        ViewInteraction textView = onView(withId(R.id.textView) );
      //          allOf(withId(R.id.textView), withText("You shall not pass!"),
      //                  withParent(withParent(withId(android.R.id.content))),
      //                  isDisplayed()));
        textView.check(matches(withText("You shall not pass!")));
    }
    /**
     * a test case for requirement making sure that it can detect when a password missing
     * upper case
     */
    @Test
    public void testFindMissingUpperCase() {
        //find the view
        ViewInteraction appCompatEditText = onView( withId(R.id.editTextPassword));
        //type in password "password123#$*"
        appCompatEditText.perform(replaceText("password123#$*"), closeSoftKeyboard());
        //find the button
        ViewInteraction materialButton = onView( withId(R.id.buttonLogin));
        //click the button
        materialButton.perform(click());
        //find the text view
        ViewInteraction textView = onView(withId(R.id.textView) );
        //check the text
        textView.check(matches(withText("You shall not pass!")));
    }
    /**
     * a test case for requirement making sure that it can detect when a password missing
     * lower case
     */
    @Test
    public void testFindMissingLowerCase() {
        //find the view
        ViewInteraction appCompatEditText = onView( withId(R.id.editTextPassword));
        //type in password "password123#$*"
        appCompatEditText.perform(replaceText("PASSWORD123#$*"), closeSoftKeyboard());
        //find the button
        ViewInteraction materialButton = onView( withId(R.id.buttonLogin));
        //click the button
        materialButton.perform(click());
        //find the text view
        ViewInteraction textView = onView(withId(R.id.textView) );
        //check the text
        textView.check(matches(withText("You shall not pass!")));
    }
    /**
     * a test case for requirement making sure that it can detect when a password missing
     * digit
     */
    @Test
    public void testFindMissingDigit() {
        //find the view
        ViewInteraction appCompatEditText = onView( withId(R.id.editTextPassword));
        //type in password "password123#$*"
        appCompatEditText.perform(replaceText("Password#$*"), closeSoftKeyboard());
        //find the button
        ViewInteraction materialButton = onView( withId(R.id.buttonLogin));
        //click the button
        materialButton.perform(click());
        //find the text view
        ViewInteraction textView = onView(withId(R.id.textView) );
        //check the text
        textView.check(matches(withText("You shall not pass!")));
    }
    /**
     * a test case for requirement making sure that it can detect when a password missing
     * special character
     */
    @Test
    public void testFindMissingSpecial() {
        //find the view
        ViewInteraction appCompatEditText = onView( withId(R.id.editTextPassword));
        //type in password "password123#$*"
        appCompatEditText.perform(replaceText("Password123"), closeSoftKeyboard());
        //find the button
        ViewInteraction materialButton = onView( withId(R.id.buttonLogin));
        //click the button
        materialButton.perform(click());
        //find the text view
        ViewInteraction textView = onView(withId(R.id.textView) );
        //check the text
        textView.check(matches(withText("You shall not pass!")));
    }
    /**
     * a test case for requirement making sure that has all of these requirements
     * and the text view should then say "Your password is complex enough".
     */
    @Test
    public void testComplexEnough() {
        //find the view
        ViewInteraction appCompatEditText = onView( withId(R.id.editTextPassword));
        //type in password "password123#$*"
        appCompatEditText.perform(replaceText("Password123$"), closeSoftKeyboard());
        //find the button
        ViewInteraction materialButton = onView( withId(R.id.buttonLogin));
        //click the button
        materialButton.perform(click());
        //find the text view
        ViewInteraction textView = onView(withId(R.id.textView) );
        //check the text
        textView.check(matches(withText("Your password meets the requirements")));
    }
    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
