package com.example.jatingarg.imagedownloader;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;
import android.widget.ImageView;

import junit.framework.Assert;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static junit.framework.Assert.assertEquals;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.action.ViewActions.click;
/**
 * Created by jatingarg on 09/04/17.
 */
@RunWith(AndroidJUnit4.class)
public class MainActivityTests {
    private static final String TAG = "MainActivityTests";
    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<MainActivity>(MainActivity.class);

    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();
        assertEquals("com.example.jatingarg.imagedownloader", appContext.getPackageName());
    }

    @Test
    public void testViewsExist(){
        onView(withId(R.id.downloadButton)).check(matches(isDisplayed()));
        onView(withId(R.id.downloadedImage)).check(matches(isDisplayed()));
        onView(withId(R.id.progressBar)).check(matches(isDisplayed()));
    }

    @Test
    public void testImageDownloadOperation(){
        onView(withId(R.id.downloadButton)).perform(click());
        Drawable imageDrawable = ((ImageView)mActivityTestRule.getActivity().findViewById(R.id.downloadedImage)).getDrawable();
        Assert.assertNotNull(imageDrawable);
    }




}
