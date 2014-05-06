package com.rootser;
import android.test.ActivityInstrumentationTestCase2;

/**
 * Created by john on 5/2/14.
 */
public class MainActivityNetworkTest extends ActivityInstrumentationTestCase2<MainActivity> {
    private MainActivity mMainActivity;

    public MainActivityNetworkTest( ){
        super(MainActivity.class);
    }

    protected void setUp() throws Exception {
        super.setUp();
        mMainActivity = getActivity();
    }

    public void testPreconditions() {
        assertNotNull("mNetworkManagerActivity is null", mMainActivity);
    }

    public void isNetworkAvailableTest() {
        assertNotNull(mMainActivity.isNetworkAvailable() );
    }

}
