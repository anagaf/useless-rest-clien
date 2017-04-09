package com.anagaf.uselessrestclient.view;

import android.support.test.rule.ActivityTestRule;

import com.anagaf.uselessrestclient.R;
import com.anagaf.uselessrestclient.dagger.DaggerModule;
import com.anagaf.uselessrestclient.dagger.DaggerWrapper;
import com.anagaf.uselessrestclient.model.User;
import com.anagaf.uselessrestclient.presenter.Presenter;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class MainActivityAndroidTest {

    private Presenter presenter;

    @Rule
    public final ActivityTestRule<MainActivity> activityRule = new ActivityTestRule<>(MainActivity.class, false, false);

    @Before
    public void setUp() throws Exception {
        presenter = mock(Presenter.class);
        DaggerWrapper.INSTANCE.setModule(new TestDaggerModule());
        activityRule.launchActivity(null);
    }

    @Test
    public void shouldRetrieveUsers() {
        onView(withId(R.id.retrieve_users)).perform(click());
        verify(presenter).retrieveUsers();
    }

    @Test
    public void shouldShowProgressBar() {
        activityRule.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                activityRule.getActivity().showProgressBar();
            }
        });

        onView(withId(R.id.progress)).check(matches(isDisplayed()));
        onView(withId(R.id.error)).check(doesNotExist());
        onView(withId(R.id.users)).check(doesNotExist());
    }

    @Test
    public void shouldShowUsers() throws JSONException {
        final List<User> users = Arrays.asList(
                createUser("name1", "email1"),
                createUser("name2", "email2")
        );

        activityRule.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                activityRule.getActivity().showUsers(users);
            }
        });

        for (User user : users) {
            onView(withText(user.getName())).check(matches(isDisplayed()));
            onView(withText(user.getEmail())).check(matches(isDisplayed()));
        }
    }

    @Test
    public void shouldShowError() {
        final String errorMessage = "error";

        activityRule.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                activityRule.getActivity().showError(errorMessage);
            }
        });

        onView(withText(errorMessage)).check(matches(isDisplayed()));
    }

    private static User createUser(final String name, final String email) throws JSONException {
        final JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("email", email);
        return new Gson().fromJson(json.toString(), User.class);
    }

    /* ========== Inner Classes ========== */

    private final class TestDaggerModule extends DaggerModule {

        @Override
        public Presenter providePresenter() {
            return presenter;
        }
    }
}
