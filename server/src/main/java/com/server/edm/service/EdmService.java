package com.server.edm.service;

import com.server.edm.service.client.ClientManager;

import java.nio.channels.SocketChannel;

public interface EdmService {
    String getName();
    boolean register(final SocketChannel socketChannel);
    void doService();
    boolean isRunning();
    ServiceCategory getCategory();
    int getClientSize();

}
