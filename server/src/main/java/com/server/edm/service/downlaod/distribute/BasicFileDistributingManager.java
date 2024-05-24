package com.server.edm.service.downlaod.distribute;

import com.server.edm.net.DataTransferManager;
import com.server.edm.net.MessageTransferManager;

import java.io.BufferedInputStream;
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
     static final String START_DOWNLOAD = "START DOWNLOAD";
     static final String OK_SIGN = "OK";

    public BasicFileDistributingManager(MessageTransferManager messageTransferManager, DataTransferManager dataTransferManager) {
        this.messageTransferManager = messageTransferManager;
        this.dataTransferManager = dataTransferManager;
    }

    @Override
    public void distribute(Set<SocketChannel> channels, BufferedInputStream fileInputStream, String fileName) {
        final Set<SocketChannel> readyChannels = readyClient(channels, fileName);
        final byte[] buffer = new byte[BUFFER_SIZE];
        int byteCount = 0;
        try {
            while ((byteCount = fileInputStream.read(buffer)) != -1) {
                this.sendToAllChannels(readyChannels, buffer, byteCount);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Set<SocketChannel> readyClient(final Set<SocketChannel> channels, final String fileName) {
        final String message = START_DOWNLOAD + ":" + fileName;
        return channels.parallelStream()
                .filter(socketChannel -> messageTransferManager.request(
                socketChannel,
                message,
                result -> result.equals(OK_SIGN))
        ).collect(Collectors.toSet());
    }

    private void sendToAllChannels(final Set<SocketChannel> channels, final byte[] bytes, final int byteCount) {
        channels.parallelStream()
                .forEach(socketChannel -> dataTransferManager.send(socketChannel, bytes, byteCount));
    }
}
