package com.rootser;

import android.test.suitebuilder.TestSuiteBuilder;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * Created by john on 5/3/14.
 */
public class AllTests extends TestSuite {
    public static Test suite() {
        Class[] tests = new Class[] {MainActivityTest.class};
        return new TestSuiteBuilder(AllTests.class)
                .includeAllPackagesUnderHere()
                .build();
    }
}
