package com.server.edm.service.stream;

import com.server.edm.service.EdmService;
import com.server.edm.service.WebResourceAccessManager;
import com.server.edm.service.client.ClientManager;

import java.nio.channels.SocketChannel;

public class StreamingService implements EdmService{
    private static final String STREAMING = "stream";
    private final ClientManager clientManager;

    public StreamingService(final ClientManager clientManager) {
        this.clientManager = clientManager;
    }

    @Override
    public boolean register(final String clientReqestServiceName, final SocketChannel socketChannel) {
        if (clientReqestServiceName.equals(STREAMING)) {
            // 서비스에 해당 연결을 등록하는 로직
            clientManager.register(this, socketChannel);
            return true;
        }
        return false;
    }

    @Override
    public void doService() {

    }

    @Override
    public String toString() {
        return STREAMING;
    }
}
