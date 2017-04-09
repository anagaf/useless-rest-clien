package com.anagaf.uselessrestclient.view;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.anagaf.uselessrestclient.R;
import com.anagaf.uselessrestclient.dagger.DaggerWrapper;
import com.anagaf.uselessrestclient.model.User;
import com.anagaf.uselessrestclient.presenter.Presenter;

import java.util.List;

import javax.inject.Inject;

public class MainActivity extends Activity implements Presenter.View {

    @Inject
    @SuppressWarnings({"WeakerAccess", "CanBeFinal"})
    Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DaggerWrapper.INSTANCE.getComponent().inject(this);

        findViewById(R.id.retrieve_users).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                presenter.retrieveUsers();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.start(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        presenter.stop();
    }

    @Override
    public void showProgressBar() {
        getFragmentManager().beginTransaction()
                .replace(R.id.content_container, new ProgressFragment())
                .commit();
    }

    @Override
    public void showUsers(final List<User> users) {
        getFragmentManager().beginTransaction()
                .replace(R.id.content_container, UsersFragment.createFragment(users))
                .commit();
    }

    @Override
    public void showError(final String message) {
        getFragmentManager().beginTransaction()
                .replace(R.id.content_container, ErrorFragment.createFragment(message))
                .commit();
    }
}
