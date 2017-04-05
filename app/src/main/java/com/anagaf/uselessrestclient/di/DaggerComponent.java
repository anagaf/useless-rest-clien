package com.anagaf.uselessrestclient.di;

import com.anagaf.uselessrestclient.view.MainActivity;

import javax.inject.Singleton;

import dagger.Component;

@Component(modules = DaggerModule.class)
@Singleton
public interface DaggerComponent {
    void inject(MainActivity mainActivity);
}
