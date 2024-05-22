package com.server.edm.service;

import com.server.edm.service.client.ClientManager;

import java.nio.channels.SocketChannel;

public interface EdmService {
    boolean register(final String clientReqestServiceName, final SocketChannel socketChannel, final ClientManager clientManager);

    void doService();
}
