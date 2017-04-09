package com.anagaf.uselessrestclient.model;

import java.io.Serializable;

@SuppressWarnings({"CanBeFinal", "unused"})
public class User implements Serializable {
    private String name;

    private String email;

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}
