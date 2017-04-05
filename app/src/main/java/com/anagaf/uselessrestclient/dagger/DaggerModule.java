package com.anagaf.uselessrestclient.dagger;

import com.anagaf.uselessrestclient.presenter.DefaultPresenter;
import com.anagaf.uselessrestclient.presenter.Presenter;
import com.anagaf.uselessrestclient.service.ProductionJsonPlaceholderService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class DaggerModule {

    @Provides
    @Singleton
    public Presenter providePresenter() {
        return new DefaultPresenter(new ProductionJsonPlaceholderService());
    }
}
