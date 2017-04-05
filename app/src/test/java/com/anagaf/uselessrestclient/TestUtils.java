package com.anagaf.uselessrestclient;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

import okio.Okio;

public class TestUtils {
    public static String getResourceText(String fileName) throws IOException {
        try (InputStream responseStream = TestUtils.class.getClassLoader().getResourceAsStream(fileName)) {
            return Okio.buffer(Okio.source(responseStream)).readString(Charset.defaultCharset());
        }
    }
}
