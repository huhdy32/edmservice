package com.server.edm.service.downlaod;

import com.server.edm.service.EdmService;
import com.server.edm.service.client.ClientManager;

import java.nio.channels.SocketChannel;

public class DownLoadService implements EdmService {
    private static final String DOWNLOAD = "download";

    @Override
    public boolean register(final String clientRequestServiceName, final SocketChannel socketChannel, final ClientManager clientManager) {
        if (clientRequestServiceName.equals(DOWNLOAD)) {
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
        return DOWNLOAD;
    }
}
