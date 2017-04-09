package com.anagaf.uselessrestclient.view;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.anagaf.uselessrestclient.R;
import com.anagaf.uselessrestclient.model.User;

import java.io.Serializable;
import java.util.List;

public class UsersFragment extends Fragment {

    private static final String USERS_KEY = "users";

    private RecyclerView recyclerView;

    public static UsersFragment createFragment(final List<User> users) {
        final Bundle args = new Bundle();
        args.putSerializable(USERS_KEY, (Serializable) users);
        final UsersFragment fragment = new UsersFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, final Bundle savedInstanceState) {
        recyclerView = (RecyclerView) inflater.inflate(R.layout.fragment_users, container, false);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        final List<User> users = (List<User>) getArguments().getSerializable(USERS_KEY);
        recyclerView.setAdapter(new UsersAdapter(users));

        return recyclerView;
    }

    public void setUsers(final List<User> users) {
        recyclerView.setAdapter(new UsersAdapter(users));
    }
}
