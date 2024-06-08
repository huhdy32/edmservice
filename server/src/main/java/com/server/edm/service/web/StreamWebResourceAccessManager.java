package com.server.edm.service.web;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class StreamWebResourceAccessManager implements WebResourceAccessManager {

    @Override
    public WebResource access(final String path) {
        final URLConnection connection = getConnection(getURL(path));
        return new WebResource(getInputStream(connection), getFileSize(connection));
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
    private Long getFileSize(final URLConnection connection) {
        return connection.getContentLengthLong();
    }
}
