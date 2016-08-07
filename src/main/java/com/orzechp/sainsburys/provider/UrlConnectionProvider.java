package com.orzechp.sainsburys.provider;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

@Component
public class UrlConnectionProvider {
    public URLConnection provideURLConnection(String url) throws IOException {
        return new URL(url).openConnection();
    }
}
