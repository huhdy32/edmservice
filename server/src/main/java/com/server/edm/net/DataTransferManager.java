package com.server.edm.net;

import java.nio.channels.SocketChannel;

/**
 * 커넥션을 통한 데이터 전송 로직 담당
 */
public interface DataTransferManager {
    byte[] sendAndRecieve(final SocketChannel socketChannel, byte[] data);
    void send(final SocketChannel socketChannel, byte[] data);
}
