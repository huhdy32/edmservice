package com.server.edm.distribute;

import java.nio.channels.SocketChannel;

/**
 * 연결이 수립된 소켓을 적절한 서비스로 매핑하는 역할
 */
public interface ConnectionDistributor {
    void distribute(final SocketChannel clientSocketChannel);
}
