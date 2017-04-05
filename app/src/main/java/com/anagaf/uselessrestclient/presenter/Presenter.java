package com.anagaf.uselessrestclient.presenter;

import com.anagaf.uselessrestclient.model.User;

import java.util.List;

public interface Presenter {

    void start(final Listener listener);

    void stop();

    void retrieveUsers();

    /* ========== Inner Classes ========== */

    interface Listener {
        void onUsersAvailable(List<User> users);

        void onError(String message);
    }
}
