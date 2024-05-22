package com.server.edm.distribute;

import com.server.edm.net.MessageTransferManager;
import com.server.edm.service.EdmService;
import com.server.edm.service.downlaod.DownLoadService;
import com.server.edm.service.register.ConnectionManager;
import com.server.edm.service.stream.StreamingService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.channels.SocketChannel;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

class StreamConnectionDistributorTest {
    private StreamConnectionDistributor streamConnectionDistributor;

    /**
     * Mock
     */
    private ConnectionManager connectionManager = new ConnectionManager() {
        @Override
        public void register(EdmService edmService, SocketChannel socketChannel) {

        }

        @Override
        public void drop(SocketChannel socketChannel) {

        }
    };
    private EdmService downloadService = new DownLoadService();
    private EdmService streamingService = new StreamingService();
    /**
     * Mock
     */
    private MessageTransferManager messageTransferManger;


    private void modifyMessageTransferManagerReceivedData(final String result) {
        this.messageTransferManger = new MessageTransferManager() {
            @Override
            public Optional<String> requestUntil(SocketChannel socketChannel, String request, Predicate<String> predicate) {
                return Optional.of(result);
            }

            @Override
            public Optional<String> requestUntil(SocketChannel socketChannel, byte[] data, Predicate<String> predicate, int tryCount) {
                // not used
                return Optional.of(result);
            }
        };
    }

    void initDistributor() {
        this.streamConnectionDistributor = new StreamConnectionDistributor(List.of(downloadService, streamingService), connectionManager, messageTransferManger);
    }

    @Test
    void streamingServiceMappingTest() {
        modifyMessageTransferManagerReceivedData(streamingService.toString());
        initDistributor();

        SocketChannel socketChannel;
        try {
            socketChannel = SocketChannel.open();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Assertions.assertDoesNotThrow(() -> this.streamConnectionDistributor.distribute(socketChannel));
    }

    @Test
    void downloadServiceMappingTest() {
        modifyMessageTransferManagerReceivedData(downloadService.toString());
        initDistributor();

        SocketChannel socketChannel;
        try {
            socketChannel = SocketChannel.open();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Assertions.assertDoesNotThrow(() -> this.streamConnectionDistributor.distribute(socketChannel));
    }

    @Test
    void illegalArgumentTest() {
        modifyMessageTransferManagerReceivedData("test");
        SocketChannel socketChannel;
        try {
            socketChannel = SocketChannel.open();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Assertions.assertFalse(socketChannel.isConnected());
    }
}