package com.example.samar.backingapp;

import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.CoreMatchers.allOf;

/**
 * Created by Samar on 21/06/2018.
 */

@RunWith(AndroidJUnit4.class)
@android.support.test.filters.LargeTest
public class BakingTest {




    @Test
    public void clickListViewItem() {

        //Check Recipes Recycle view is displayed
        onView(allOf(withId(R.id.recycler_view),
                isDisplayed()));

    }

}