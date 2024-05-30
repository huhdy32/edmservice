package com.client.edm.net;

public interface Connection {
    void sendAndRecieve(final String message);
    String recieve();
}
