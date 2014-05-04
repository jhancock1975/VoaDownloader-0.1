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
        downloadStatusText = (TextView) mMainActivity.findViewById(R.id.downloadStatusText);
    }

    public void testPreconditions() {
        assertNotNull("mMainActivity is null", mMainActivity);
        assertNotNull("downloadStatusText is null", downloadStatusText);
    }

    public void testMyFirstTestTextView_labelText() {
        final String expected = mMainActivity.getString(R.string.ready);
        final String actual = downloadStatusText.getText().toString();
        assertEquals("downloadStatusText contains wrong text", expected, actual);
    }
}