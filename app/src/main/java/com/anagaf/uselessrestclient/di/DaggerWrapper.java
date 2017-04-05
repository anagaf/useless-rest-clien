package com.anagaf.uselessrestclient.di;

public enum DaggerWrapper {
    INSTANCE;

    private DaggerComponent component;

    DaggerWrapper() {
        setModule(new DaggerModule());
    }

    public void setModule(final DaggerModule daggerModule) {
        component = DaggerDaggerComponent.builder().daggerModule(daggerModule).build();
    }

    public DaggerComponent getComponent() {
        return component;
    }
}
