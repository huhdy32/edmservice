package com.server.edm.service.client;

import com.server.edm.net.*;
import com.server.edm.service.EdmService;
import com.server.edm.service.downlaod.DownLoadService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.channels.SocketChannel;

class BasicClientManagerTest {

    private BasicClientManager basicClientManager;
    private MessageTransferManager messageTransferManager;
    private Encoder encoder = new UTFEncoder();

    void init(final DataTransferManager dataTransferManager) {
        this.messageTransferManager = new BasicMessageTransferManager(dataTransferManager, encoder);
        this.basicClientManager = new BasicClientManager(messageTransferManager);
    }

    DataTransferManager modifyClientResponse(final String response) {
        return new DataTransferManager() {
            @Override
            public byte[] sendAndRecieve(SocketChannel socketChannel, byte[] data) {
                return encoder.encode(response);
            }

            @Override
            public void send(SocketChannel socketChannel, byte[] data) {

            }
        };
    }

    @Test
    void duplicateNameInSameServiceTest() throws IOException {
        final EdmService edmService = new DownLoadService(basicClientManager);
        final String name = "tester";
        init(modifyClientResponse(name));

        final SocketChannel socketChannel = SocketChannel.open();
        basicClientManager.register(edmService, socketChannel);
        final SocketChannel socketChannel1 = SocketChannel.open();

        Assertions.assertThrows(IllegalArgumentException.class, () -> basicClientManager.register(edmService, socketChannel1));
    }

    @Test
    void duplicateNameInDifferentServiceTest() throws IOException {
        final EdmService edmService1 = new DownLoadService(basicClientManager);
        final EdmService edmService2 = new DownLoadService(basicClientManager);
        final String name = "tester";
        init(modifyClientResponse(name));

        final SocketChannel socketChannel1 = SocketChannel.open();
        final SocketChannel socketChannel2 = SocketChannel.open();

        basicClientManager.register(edmService1, socketChannel1);
        basicClientManager.register(edmService2, socketChannel2);
    }
}