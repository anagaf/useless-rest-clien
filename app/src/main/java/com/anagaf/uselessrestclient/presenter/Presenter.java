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
                .subscribe(new DefaultObserver<List<User>>() {
                    @Override
                    public void onNext(final List<User> value) {
                        if (view != null) {
                            view.showUsers(value);
                        }
                    }

                    @Override
                    public void onError(final Throwable e) {
                        if (view != null) {
                            view.showError("Unable to retrieve users: " + e.getMessage());
                        }
                    }

                    @Override
                    public void onComplete() {
                        // do nothing
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
