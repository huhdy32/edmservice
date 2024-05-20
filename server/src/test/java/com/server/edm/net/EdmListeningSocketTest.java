package com.server.edm.net;

import com.server.edm.distribute.ConnectionDistributor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

class EdmListeningSocketTest {
    private static final int SERVER_PORT = 41722;
    private static ConnectionDistributor mockConnectionDistributor;

    private static EdmListeningSocket edmListeningSocket;

    private static void initTestServer() {
        mockConnectionDistributor = new ConnectionDistributor() {
            @Override
            public void distribute(SocketChannel clientSocketChannel) {
            }
        };
        try {
            edmListeningSocket = new EdmListeningSocket(Selector.open(), SERVER_PORT, mockConnectionDistributor);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @BeforeAll
    static void init() {
        initTestServer();
        startServer();
    }

    static void startServer() {
        Thread serverSocket = new Thread(edmListeningSocket);
        serverSocket.start();
    }

    @Test
    void singleConnectionTest() {
        Socket client = new Socket();
        Assertions.assertDoesNotThrow(() -> client.connect(new InetSocketAddress(SERVER_PORT)));
    }

    @Test
    void multiConnectionTest() {
        for (int i = 0 ; i < 25; i ++) {
            Assertions.assertDoesNotThrow(() -> {
                Socket clientSocket = new Socket();
                clientSocket.connect(new InetSocketAddress(SERVER_PORT));
            });
        }
    }
}