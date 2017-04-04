package com.anagaf.uselessrestclient.service;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public final class DefaultJsonPlaceholderService implements JsonPlaceholderService {

    private static final String HOST = "https://jsonplaceholder.typicode.com";

    @Override
    public JsonPlaceholderService.Api getApi() {
        final Retrofit retrofit = new Retrofit
                .Builder()
                .baseUrl(HOST)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(JsonPlaceholderService.Api.class);
    }
}
