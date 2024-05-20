package com.server.edm.distribute;

import com.server.edm.net.DataTransferManager;
import com.server.edm.service.EdmService;
import com.server.edm.service.downlaod.DownLoadService;
import com.server.edm.service.stream.StreamingService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.channels.SocketChannel;
import java.util.List;

class StreamConnectionDistributorTest {
    private StreamConnectionDistributor streamConnectionDistributor;
    private EdmService downloadService = new DownLoadService();
    private EdmService streamingService = new StreamingService();
    private DataTransferManager dataTransferManager;

    private void modifyDataTransferManagerRecieveData(final String command) {
        this.dataTransferManager = new DataTransferManager() {
            @Override
            public String sendAndRecieve(SocketChannel socketChannel, byte[] data) {
                return command;
            }

            @Override
            public void send(SocketChannel socketChannel, byte[] data) {
            }
        };
        initDistributor();
    }
    void initDistributor() {
        this.streamConnectionDistributor = new StreamConnectionDistributor(List.of(downloadService, streamingService), dataTransferManager);
    }

    @Test
    void streamingServiceMappingTest() {
        modifyDataTransferManagerRecieveData(streamingService.toString());
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
        modifyDataTransferManagerRecieveData(downloadService.toString());
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
        modifyDataTransferManagerRecieveData("test");
        SocketChannel socketChannel;
        try {
            socketChannel = SocketChannel.open();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Assertions.assertThrows(IllegalArgumentException.class, () -> this.streamConnectionDistributor.distribute(socketChannel));
    }
}