package com.anagaf.uselessrestclient.service;

public final class ProductionJsonPlaceholderService extends AbstractJsonPlaceholderService {
    @Override
    protected String getHost() {
        return "https://jsonplaceholder.typicode.com";
    }
}
