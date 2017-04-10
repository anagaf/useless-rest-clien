package com.anagaf.uselessrestclient.dagger;

import com.anagaf.uselessrestclient.presenter.Presenter;
import com.anagaf.uselessrestclient.service.JsonPlaceholderService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class DaggerModule {

    @Provides
    @Singleton
    public Presenter providePresenter() {
        return new Presenter(new JsonPlaceholderService());
    }
}
