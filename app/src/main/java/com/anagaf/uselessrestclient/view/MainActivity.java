package com.anagaf.uselessrestclient.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ProgressBar;
import android.widget.TextView;

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

    private ProgressBar progressBar;

    private TextView errorMessageTextView;

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DaggerWrapper.INSTANCE.getComponent().inject(this);

        progressBar = (ProgressBar) findViewById(R.id.progress);

        errorMessageTextView = (TextView) findViewById(R.id.error_message);

        recyclerView = (RecyclerView) findViewById(R.id.users);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onStart() {
        super.onStart();

        presenter.start(this);

        presenter.retrieveUsers();
    }

    @Override
    protected void onStop() {
        super.onStop();

        presenter.stop();

        recyclerView.setVisibility(android.view.View.GONE);
    }

    @Override
    public void showProgressBar() {
        progressBar.setAlpha(1);
        progressBar.setVisibility(android.view.View.VISIBLE);
    }

    @Override
    public void showUsers(final List<User> users) {
        recyclerView.setAdapter(new UsersAdapter(users));
        animateCrossfade(recyclerView);
    }

    @Override
    public void showError(final String message) {
        errorMessageTextView.setText(message);
        animateCrossfade(errorMessageTextView);
    }

    private void animateCrossfade(android.view.View destinationView) {
        destinationView.setAlpha(0f);
        destinationView.setVisibility(android.view.View.VISIBLE);

        destinationView.animate()
                .alpha(1f)
                .setListener(null);

        progressBar.animate()
                .alpha(0f)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        progressBar.setVisibility(android.view.View.GONE);
                    }
                });
    }
}
