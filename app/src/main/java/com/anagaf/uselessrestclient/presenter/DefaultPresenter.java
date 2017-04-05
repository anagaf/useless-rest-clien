package com.anagaf.uselessrestclient.presenter;

import com.anagaf.uselessrestclient.model.User;
import com.anagaf.uselessrestclient.service.JsonPlaceholderService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DefaultPresenter implements Presenter{

    private Listener listener;

    private final JsonPlaceholderService service;

    public DefaultPresenter(final JsonPlaceholderService service) {
        this.service = service;
    }

    public void start(final Listener listener) {
        this.listener = listener;
    }

    public void stop() {
        this.listener = null;
    }

    @Override
    public void retrieveUsers() {
        service.getApi().getUsers().enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(final Call<List<User>> call, final Response<List<User>> response) {
                if (listener != null) {
                    if (response.isSuccessful()) {
                        listener.onUsersAvailable(response.body());
                    } else {
                        listener.onError("Unable to retrieve users (response code " + response.code() + ")");
                    }
                }
            }

            @Override
            public void onFailure(final Call<List<User>> call, final Throwable t) {
                if (listener != null) {
                    listener.onError(t.getMessage());
                }
            }
        });
    }

}
