package com.client.edm.net;

public interface Connection {
    void send(String message);
    String recieve();
    byte[] recieveData();
}
