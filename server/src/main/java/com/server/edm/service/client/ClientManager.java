package com.server.edm.service.client;

import com.server.edm.service.EdmService;

import java.io.BufferedInputStream;
import java.nio.channels.SocketChannel;
import java.util.Set;

/**
 * 서비스에 연결된 모든 사용자를 관리
 */
public interface ClientManager {
    boolean register(EdmService edmService, SocketChannel socketChannel);
    Set<SocketChannel> getChannels(EdmService edmService);
    void drop(SocketChannel socketChannel);
}
