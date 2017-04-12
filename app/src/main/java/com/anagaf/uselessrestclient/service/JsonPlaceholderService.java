package com.anagaf.uselessrestclient.service;

import com.anagaf.uselessrestclient.model.User;
import com.google.common.annotations.VisibleForTesting;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public class JsonPlaceholderService {

    private String host = "https://jsonplaceholder.typicode.com";

    public Api getApi() {
        final Retrofit retrofit = new Retrofit
                .Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(host)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(Api.class);
    }

    @VisibleForTesting
    public void setHost(final String host) {
        this.host = host;
    }

    /* ========== Inner Classes ========== */

    public interface Api {
        @GET("users")
        Observable<List<User>> getUsers();
    }
}
