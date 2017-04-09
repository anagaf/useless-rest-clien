package com.anagaf.uselessrestclient.presenter;

import com.anagaf.uselessrestclient.model.User;

import java.util.List;

public interface Presenter {

    void start(final View view);

    void stop();

    void retrieveUsers();

    /* ========== Inner Classes ========== */

    interface View {
        void showProgressBar();

        void showUsers(List<User> users);

        void showError(String message);
    }
}
