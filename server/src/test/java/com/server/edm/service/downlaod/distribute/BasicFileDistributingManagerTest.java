package com.server.edm.service.downlaod.distribute;

import com.server.edm.net.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.channels.SocketChannel;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

class BasicFileDistributingManagerTest {
    private MessageTransferManager messageTransferManager;
    private DataTransferManager dataTransferManager;
    private Encoder encoder = new UTFEncoder();

    private AtomicInteger resultCounter = new AtomicInteger();
    void initMessageTransferManager() {
        this.messageTransferManager = new BasicMessageTransferManager(dataTransferManager, encoder);
    }
    void modifyClientResponse(final String clientResponse) {
        this.dataTransferManager = new DataTransferManager() {
            @Override
            public byte[] sendAndRecieve(SocketChannel socketChannel, byte[] data, int byteCount) {
                // 성공 가정
                return encoder.encode(clientResponse);
            }

            @Override
            public void send(SocketChannel socketChannel, byte[] data, int byteCount) {
                // 클라이언트에게 데이터 전송이 되는지 확인
                resultCounter.incrementAndGet();
            }
        };
    }

    void initResultCounter() {
        resultCounter = new AtomicInteger();
    }

    @Test
    void clientResponseIsOK() {
        initResultCounter();
        modifyClientResponse(BasicFileDistributingManager.OK_SIGN);
        initMessageTransferManager();
        final FileDistributingManager fileDistributingManager = new BasicFileDistributingManager(messageTransferManager, dataTransferManager);


        final SocketChannel socketChannel1;
        final SocketChannel socketChannel2;
        try {
            socketChannel1 = SocketChannel.open();
            socketChannel2 = SocketChannel.open();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Set<SocketChannel> channels = Set.of(socketChannel1, socketChannel2);
        // test data
        final byte[] data = {1, 2, 34, 5};
        final BufferedInputStream bufferedInputStream = new BufferedInputStream(new ByteArrayInputStream(data));


        fileDistributingManager.distribute(channels, bufferedInputStream, "test");

        Assertions.assertEquals(resultCounter.get(), 2);
    }

    @Test
    void clientResponseIsNotOK() {
        initResultCounter();
        modifyClientResponse("maybe not ok?");
        initMessageTransferManager();
        final FileDistributingManager fileDistributingManager = new BasicFileDistributingManager(messageTransferManager, dataTransferManager);


        final SocketChannel socketChannel1;
        final SocketChannel socketChannel2;
        try {
            socketChannel1 = SocketChannel.open();
            socketChannel2 = SocketChannel.open();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Set<SocketChannel> channels = Set.of(socketChannel1, socketChannel2);
        // test data
        final byte[] data = {1, 2, 34, 5};
        final BufferedInputStream bufferedInputStream = new BufferedInputStream(new ByteArrayInputStream(data));


        fileDistributingManager.distribute(channels, bufferedInputStream, "test");
        Assertions.assertEquals(resultCounter.get(), 0);
    }
            
}