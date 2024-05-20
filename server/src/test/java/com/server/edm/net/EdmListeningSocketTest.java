package com.server.edm.net;

import com.server.edm.distribute.StreamConnectionDistributor;
import com.server.edm.service.downlaod.DownLoadService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.channels.Selector;
import java.util.List;

class EdmListeningSocketTest {
    private static final int SERVERPORT = 41722;

    private final EdmListeningSocket edmListeningSocket;
    {
        try {
            edmListeningSocket = new EdmListeningSocket(Selector.open(), SERVERPORT, new StreamConnectionDistributor(List.of(new DownLoadService()), new ByteParser()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void singleConnectionTest() {
        startServer();
        Socket client = new Socket();
        Assertions.assertDoesNotThrow(() -> client.connect(new InetSocketAddress(SERVERPORT)));
    }

    @Test
    void multiConnectionTest() {
        startServer();
        for (int i = 0 ; i < 100; i ++) {
            Assertions.assertDoesNotThrow(() -> {
                Socket clientSocket = new Socket();
                clientSocket.connect(new InetSocketAddress(SERVERPORT));
            });
        }
    }

    void startServer() {
        Thread serverSocket = new Thread(edmListeningSocket);
        serverSocket.start();
    }
}