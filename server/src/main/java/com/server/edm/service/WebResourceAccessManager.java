package com.server.edm.service;

import java.io.BufferedInputStream;

public interface WebResourceAccessManager {
    BufferedInputStream access(final String url);
}
