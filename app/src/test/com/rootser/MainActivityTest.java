package com.rootser;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.TextView;

/**
 * Created by john on 5/3/14.
 */
public class MainActivityTest extends ActivityInstrumentationTestCase2<MainActivity> {

    private MainActivity mMainActivity;
    private TextView downloadStatusText;

    public MainActivityTest() {
        super(MainActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mMainActivity = getActivity();
    }

    public void testPreconditions() {
        assertNotNull("mMainActivity is null", mMainActivity);
    }
}