package com.anagaf.uselessrestclient.view;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.anagaf.uselessrestclient.R;

public class ProgressFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, final Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_progress, container, false);
    }
}
