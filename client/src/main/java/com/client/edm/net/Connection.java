package com.client.edm.net;

import java.io.InputStream;

public interface Connection {
    void send(String message);
    String readLine();
    InputStream getInputStream();
}
