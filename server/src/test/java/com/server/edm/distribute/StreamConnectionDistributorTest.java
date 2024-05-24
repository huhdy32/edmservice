package com.server.edm.distribute;

import com.server.edm.net.MessageTransferManager;
import com.server.edm.service.EdmService;
import com.server.edm.service.StreamWebResourceAccessManager;
import com.server.edm.service.WebResourceAccessManager;
import com.server.edm.service.distribute.FileDistributingManager;
import com.server.edm.service.downlaod.DownLoadService;
import com.server.edm.service.client.ClientManager;
import com.server.edm.service.stream.StreamingService;
import com.server.edm.ui.DownloadServiceUI;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.nio.channels.SocketChannel;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;

class StreamConnectionDistributorTest {
    private StreamConnectionDistributor streamConnectionDistributor;
    /**
     * mock
     */
    private DownloadServiceUI downloadServiceUI = new DownloadServiceUI() {
        @Override
        public String getUrl() {
            return null;
        }

        @Override
        public String getFileName() {
            return null;
        }
    }
;
    /**
     * mock
     */
    private FileDistributingManager fileDistributingManager = new FileDistributingManager() {
        @Override
        public void distribute(Set<SocketChannel> channels, BufferedInputStream fileInputStream, String fileName) {

        }
    };
    /**
     * mock
     */
    private WebResourceAccessManager webResourceAccessManager = new WebResourceAccessManager() {
        @Override
        public BufferedInputStream access(String url) {
            return null;
        }
    };

    /**
     * Mock
     */
    private ClientManager clientManager = new ClientManager() {
        @Override
        public void register(EdmService edmService, SocketChannel socketChannel) {

        }

        @Override
        public Set<SocketChannel> getChannels(EdmService edmService) {
            return null;
        }

        @Override
        public void drop(SocketChannel socketChannel) {

        }
    };
    private EdmService downloadService = new DownLoadService(clientManager, webResourceAccessManager, downloadServiceUI, fileDistributingManager);
    private EdmService streamingService = new StreamingService(clientManager);
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
            public Optional<String> requestUntil(SocketChannel socketChannel, String data, Predicate<String> predicate, int tryCount) {
                // not used
                return Optional.of(result);
            }
        };
    }

    void initDistributor() {
        this.streamConnectionDistributor = new StreamConnectionDistributor(List.of(downloadService, streamingService), clientManager, messageTransferManger);
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