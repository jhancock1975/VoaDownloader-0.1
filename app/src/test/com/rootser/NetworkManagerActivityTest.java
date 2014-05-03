package com.rootser;
import android.test.ActivityInstrumentationTestCase2;

/**
 * Created by john on 5/2/14.
 */
public class NetworkManagerActivityTest extends ActivityInstrumentationTestCase2<NetworkManagerActivity> {
    private NetworkManagerActivity mNetworkManagerActivity;

    public NetworkManagerActivityTest( ){
        super(NetworkManagerActivity.class);
    }

    protected void setUp() throws Exception {
        super.setUp();

        // Starts the activity under test using the default Intent with:
        // action = {@link Intent#ACTION_MAIN}
        // flags = {@link Intent#FLAG_ACTIVITY_NEW_TASK}
        // All other fields are null or empty.
        mNetworkManagerActivity = getActivity();
    }

    public void testPreconditions() {
        assertNotNull("mNetworkManagerActivity is null", mNetworkManagerActivity);
    }

    /**
     * Tests the correctness of the initial text.
     */
    public void isNetworkAvailableTest() {
        assertNotNull(mNetworkManagerActivity.isNetworkAvailable() );
    }
    public void retryNetworkAvailableTest(){
        assertNotNull(mNetworkManagerActivity.retryNetwork());
    }
}
