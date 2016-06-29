package com.example.nickwph.jackandjillannotationtest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import static org.mockito.Mockito.verify;

/**
 * Created by nickwph on 6/28/16.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(application = TestApplication.class, constants = BuildConfig.class, sdk = 18)
public class MainActivityTest {

    @Mock Utility utility;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        TestApplication application = (TestApplication) RuntimeEnvironment.application;
        TestComponent component = (TestComponent) application.getComponent();
        component.setUtility(utility);
    }

    @Test
    public void shouldRunUtilityOnCreate() throws Exception {
        Robolectric.buildActivity(MainActivity.class).create();
        verify(utility).run();
    }
}
