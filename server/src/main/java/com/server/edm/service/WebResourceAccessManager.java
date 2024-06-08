package com.server.edm.service;

import java.io.BufferedInputStream;

public interface WebResourceAccessManager {
    WebResource access(final String url);
}
