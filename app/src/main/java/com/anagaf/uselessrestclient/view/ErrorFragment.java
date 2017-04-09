package com.anagaf.uselessrestclient.view;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.anagaf.uselessrestclient.R;

public class ErrorFragment extends Fragment {

    private static final String MESSAGE_KEY = "message";

    public static Fragment createFragment(final String message) {
        final Bundle args = new Bundle();
        args.putString(MESSAGE_KEY, message);
        final Fragment fragment = new ErrorFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, final Bundle savedInstanceState) {
        final TextView view = (TextView) inflater.inflate(R.layout.fragment_error, container, false);
        view.setText(getArguments().getString(MESSAGE_KEY));
        return view;
    }
}
