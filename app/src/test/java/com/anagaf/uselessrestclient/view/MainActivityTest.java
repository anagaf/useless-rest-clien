package com.anagaf.uselessrestclient.view;

import com.anagaf.uselessrestclient.BuildConfig;
import com.anagaf.uselessrestclient.dagger.DaggerModule;
import com.anagaf.uselessrestclient.dagger.DaggerWrapper;
import com.anagaf.uselessrestclient.presenter.Presenter;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * WARNING: if this test fails on Mac check "Note for Linux and Mac Users" at http://robolectric.org/getting-started/
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class MainActivityTest {

    @Test
    public void shouldRetrieveUsers() {
        final Presenter presenter = mock(Presenter.class);

        DaggerWrapper.INSTANCE.setModule(new DaggerModule() {
            @Override
            public Presenter providePresenter() {
                return presenter;
            }
        });

        Robolectric.buildActivity(MainActivity.class).create().start().get();

        verify(presenter).retrieveUsers();
    }
}
