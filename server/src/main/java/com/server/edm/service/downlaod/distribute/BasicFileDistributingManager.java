package com.server.edm.service.downlaod.distribute;

import com.protocol.edm.DownloadCommand;
import com.server.edm.net.DataTransferManager;
import com.server.edm.net.MessageTransferManager;
import com.server.edm.service.web.WebResource;

import java.io.IOException;
import java.nio.channels.SocketChannel;
import java.util.Set;
import java.util.stream.Collectors;

/**
 *
 */
public class BasicFileDistributingManager implements FileDistributingManager {
    static final int BUFFER_SIZE = 1024;
    private final MessageTransferManager messageTransferManager;
    private final DataTransferManager dataTransferManager;

    public BasicFileDistributingManager(MessageTransferManager messageTransferManager, DataTransferManager dataTransferManager) {
        this.messageTransferManager = messageTransferManager;
        this.dataTransferManager = dataTransferManager;
    }

    @Override
    public void distribute(Set<SocketChannel> channels, WebResource webResource, String fileName) {
        final Set<SocketChannel> readyChannels = readyClient(channels, fileName);
        final byte[] buffer = new byte[BUFFER_SIZE];
        int byteCount = 0;
        try {
            while ((byteCount = webResource.inputStream().read(buffer)) != -1) {
                this.sendToAllChannels(readyChannels, buffer, byteCount);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Set<SocketChannel> readyClient(final Set<SocketChannel> channels, final String fileName) {
        final String message = DownloadCommand.START_DOWNLOAD.name() + " " + "1024" + " " + fileName;
        return channels.parallelStream()
                .filter(socketChannel -> messageTransferManager.request(
                        socketChannel,
                        message,
                        result -> true)
                ).collect(Collectors.toSet());
    }

    private void sendToAllChannels(final Set<SocketChannel> channels, final byte[] bytes, final int byteCount) {
        channels.parallelStream()
                .forEach(socketChannel -> dataTransferManager.send(socketChannel, bytes, byteCount));
    }
}
