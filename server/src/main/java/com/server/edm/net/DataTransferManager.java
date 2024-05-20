package com.server.edm.net;

import java.nio.channels.SocketChannel;

public interface DataTransferManager {
    String sendAndRecieve(final SocketChannel socketChannel, byte[] data);
    void send(final SocketChannel socketChannel, byte[] data);
}
