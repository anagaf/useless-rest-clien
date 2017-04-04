package com.anagaf.uselessrestclient.view;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.anagaf.uselessrestclient.R;
import com.anagaf.uselessrestclient.api.DefaultJsonPlaceholderService;
import com.anagaf.uselessrestclient.model.User;
import com.anagaf.uselessrestclient.presenter.Presenter;

import java.util.List;

public class MainActivity extends Activity implements Presenter.Listener {

    private Presenter presenter;

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        presenter = new Presenter(this, new DefaultJsonPlaceholderService());

        recyclerView = (RecyclerView) findViewById(R.id.users);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.retreiveUsers();
    }

    @Override
    public void onUsersAvailable(final List<User> users) {
        recyclerView.setAdapter(new UsersAdapter(users));
        recyclerView.getAdapter().notifyDataSetChanged();
    }

    @Override
    public void onError(final String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
}
