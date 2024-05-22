package com.server.edm.service.stream;

import com.server.edm.service.EdmService;
import com.server.edm.service.client.ClientManager;

import java.nio.channels.SocketChannel;

public class StreamingService implements EdmService{
    private static final String STREAMING = "stream";

    @Override
    public boolean register(final String clientReqestServiceName, final SocketChannel socketChannel, final ClientManager clientManager) {
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
