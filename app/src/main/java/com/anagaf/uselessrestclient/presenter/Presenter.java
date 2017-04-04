package com.anagaf.uselessrestclient.presenter;

import com.anagaf.uselessrestclient.api.JsonPlaceholderService;
import com.anagaf.uselessrestclient.model.User;

import java.lang.ref.WeakReference;
import java.util.Collection;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Presenter {

    private final WeakReference<Listener> listener;

    private final JsonPlaceholderService service;

    public Presenter(final Listener listener, final JsonPlaceholderService service) {
        this.listener = new WeakReference<>(listener);
        this.service = service;
    }

    public void retreiveUsers() {
        service.getApi().getUsers().enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(final Call<List<User>> call, final Response<List<User>> response) {
                final Listener listener = Presenter.this.listener.get();
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
                final Listener listener = Presenter.this.listener.get();
                if (listener != null) {
                    listener.onError(t.getMessage());
                }
            }
        });
    }

    /* ========== Inner Classes ========== */

    public interface Listener {
        void onUsersAvailable(List<User> users);

        void onError(String message);
    }
}
