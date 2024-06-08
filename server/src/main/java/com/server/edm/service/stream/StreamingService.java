package com.server.edm.service.stream;

import com.server.edm.service.EdmService;
import com.server.edm.service.ServiceCategory;
import com.server.edm.service.client.ClientManager;

import java.nio.channels.SocketChannel;

public class StreamingService implements EdmService{
    private static final String STREAMING = "stream";
    private final ClientManager clientManager;

    public StreamingService(final ClientManager clientManager) {
        this.clientManager = clientManager;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public boolean register(SocketChannel socketChannel) {
        return false;
    }

    @Override
    public void doService() {

    }

    @Override
    public boolean isRunning() {
        return false;
    }

    @Override
    public ServiceCategory getCategory() {
        return null;
    }

    @Override
    public int getClientSize() {
        return 0;
    }

    @Override
    public String toString() {
        return STREAMING;
    }
}
