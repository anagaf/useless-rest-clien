package com.anagaf.uselessrestclient.presenter;

import com.anagaf.uselessrestclient.model.User;
import com.anagaf.uselessrestclient.service.JsonPlaceholderService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
        service.getApi().getUsers().enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(final Call<List<User>> call, final Response<List<User>> response) {
                if (view != null) {
                    if (response.isSuccessful()) {
                        view.showUsers(response.body());
                    } else {
                        view.showError("Unable to retrieve users (response code " + response.code() + ")");
                    }
                }
            }

            @Override
            public void onFailure(final Call<List<User>> call, final Throwable t) {
                if (view != null) {
                    view.showError(t.getMessage());
                }
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
