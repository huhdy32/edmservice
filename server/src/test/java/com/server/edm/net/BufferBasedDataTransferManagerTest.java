package com.server.edm.net;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

class BufferBasedDataTransferManagerTest {

    private static final int SERVER_PORT = 62304;
    private SocketChannel serverSide;
    private SocketChannel clientSide;


    private ServerSocketChannel initServer() {
        try {
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.bind(new InetSocketAddress("localhost", SERVER_PORT));
            return serverSocketChannel;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private SocketChannel connectToServer() {
        try {
            final SocketChannel socketChannel = SocketChannel.open();
            socketChannel.connect(new InetSocketAddress("localhost", SERVER_PORT));
            return socketChannel;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void connectClient() {
        this.clientSide = connectToServer();
    }

    private void runServer(final ServerSocketChannel serverSocketChannel) {
        new Thread(() -> this.serverSide = getEstablishedClientChannel(serverSocketChannel)).start();
    }

    @Test
    void repeatableSendingDataToClient() {
        final ServerSocketChannel serverSocketChannel = initServer();
        runServer(serverSocketChannel);
        connectClient();

        // test data
        final byte[] message1 = "H22".getBytes();
        final byte[] message2 = "i3242".getBytes();
        final byte[] last = "\n".getBytes();
        // 서버에서 데이터 전송
        final BufferBasedDataTransferManager dataTransferManager = new BufferBasedDataTransferManager();

        dataTransferManager.send(serverSide, message1, message1.length);
        dataTransferManager.send(serverSide, message2, message2.length);
        dataTransferManager.send(serverSide, last, last.length);

        try {
            // getAllBytes() 메소드는 스트림이 끝날때까지 읽음
//            Assertions.assertEquals(clientSide.socket().getInputStream().readAllBytes().length, 4);

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(clientSide.socket().getInputStream()));
            Assertions.assertEquals(bufferedReader.readLine().length(), 8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private SocketChannel getEstablishedClientChannel(final ServerSocketChannel serverSocketChannel) {
        try {
            return serverSocketChannel.accept();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}