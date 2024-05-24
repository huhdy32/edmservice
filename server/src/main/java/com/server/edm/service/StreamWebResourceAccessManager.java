package com.server.edm.service;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class StreamWebResourceAccessManager implements WebResourceAccessManager {

    @Override
    public BufferedInputStream access(final String path) {
        return getResourceStream(path);
    }

    private BufferedInputStream getResourceStream(final String path) {
        return getInputStream(getConnection(getURL(path)));
    }
    private URLConnection getConnection(final URL url) {
        try {
            return url.openConnection();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private URL getURL(final String url) {
        try {
            return new URL(url);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }
    private BufferedInputStream getInputStream(final URLConnection connection) {
        try {
            return new BufferedInputStream(connection.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
