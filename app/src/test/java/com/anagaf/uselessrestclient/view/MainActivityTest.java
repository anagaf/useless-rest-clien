package com.anagaf.uselessrestclient.view;

import com.anagaf.uselessrestclient.BuildConfig;
import com.anagaf.uselessrestclient.di.DaggerModule;
import com.anagaf.uselessrestclient.di.DaggerWrapper;
import com.anagaf.uselessrestclient.presenter.Presenter;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

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

        Robolectric.buildActivity(MainActivity.class).create().start();

        verify(presenter).retrieveUsers();
    }
}
