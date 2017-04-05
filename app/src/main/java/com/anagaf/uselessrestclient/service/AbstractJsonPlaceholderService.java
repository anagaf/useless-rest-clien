package com.anagaf.uselessrestclient.service;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public abstract class AbstractJsonPlaceholderService implements JsonPlaceholderService {

    @Override
    public JsonPlaceholderService.Api getApi() {
        final Retrofit retrofit = new Retrofit
                .Builder()
                .baseUrl(getHost())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(JsonPlaceholderService.Api.class);
    }

    protected abstract String getHost();
}
