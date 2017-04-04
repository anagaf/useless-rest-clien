package com.anagaf.uselessrestclient.service;

import com.anagaf.uselessrestclient.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface JsonPlaceholderService {

    Api getApi();

    /* ========== Inner Classes ========== */

    interface Api {
        @GET("users")
        Call<List<User>> getUsers();
    }
}
