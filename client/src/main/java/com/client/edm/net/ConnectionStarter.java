package com.client.edm.net;

public interface ConnectionStarter {
    Connection getConnection(String address, int port);
}
