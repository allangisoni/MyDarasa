package com.mydarasa.app;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;


import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withText;


@RunWith(AndroidJUnit4.class)
public class GoToSignUpActivityTest {
    String toBeTyped = "Allan";
    @Rule
    public ActivityTestRule<LoginActivity> mActivityTestRule = new ActivityTestRule(LoginActivity.class);

    @Test
    public void gotoSignUPActivity(){

        onView(ViewMatchers.withId(R.id.btn_login))
                .perform(click());

        onView(ViewMatchers.withId(R.id.etPassword))
                .perform(typeText(toBeTyped))
                .check(matches(withText(toBeTyped)));
    }
}
