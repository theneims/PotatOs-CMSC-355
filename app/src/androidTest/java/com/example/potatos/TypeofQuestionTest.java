package com.example.potatos;

import android.app.Activity;
import android.app.Instrumentation;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static junit.framework.Assert.assertNotNull;

@RunWith(AndroidJUnit4.class)

public class TypeofQuestionTest {
    @Rule
    public ActivityTestRule<TypeofQuestion> myActivityTestRule = new ActivityTestRule<>(TypeofQuestion.class);

    private TypeofQuestion myActivity = null;

    private Instrumentation.ActivityMonitor monitor = getInstrumentation().addMonitor(TypeofQuestion.class.getName(), null, false);

    @Before
    public void setUp() throws Exception {

        myActivity = myActivityTestRule.getActivity();
    }



    @Test
    public void testChoosePhotoButtonClick() {

        assertNotNull(myActivity.findViewById(R.id.bAddText));

        onView(withId(R.id.bAddText)).perform(click());

        Activity galleryActivity = getInstrumentation().waitForMonitorWithTimeout(monitor, 5000);

        assertNotNull(galleryActivity);

        galleryActivity.finish();
    }

    @After
    public void tearDown() throws Exception {

        myActivity = null;
    }
}


