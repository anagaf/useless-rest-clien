package com.anagaf.uselessrestclient.presenter;

import com.anagaf.uselessrestclient.model.User;
import com.anagaf.uselessrestclient.service.JsonPlaceholderService;

import java.util.List;

import io.reactivex.observers.DefaultObserver;
import io.reactivex.schedulers.Schedulers;

public class Presenter {

    private View view;

    private final JsonPlaceholderService service;

    public Presenter(final JsonPlaceholderService service) {
        this.service = service;
    }

    public void start(final View view) {
        this.view = view;
    }

    public void stop() {
        this.view = null;
    }

    public void retrieveUsers() {
        view.showProgressBar();
        service.getApi().getUsers()
                .subscribeOn(Schedulers.io())
                .subscribe(users -> {
                    if (view != null) {
                        view.showUsers(users);
                    }
                }, error -> {
                    if (view != null) {
                        view.showError("Unable to retrieve users: " + error.getMessage());
                    }
                });
    }

    /* ========== Inner Classes ========== */

    public interface View {
        void showProgressBar();

        void showUsers(List<User> users);

        void showError(String message);
    }
}
