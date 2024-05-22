package com.server.edm.service.client;

import com.server.edm.service.EdmService;

import java.nio.channels.SocketChannel;

/**
 * 서비스에 연결된 모든 사용자를 관리
 */
public interface ClientManager {
    void register(EdmService edmService, SocketChannel socketChannel);
    void drop(SocketChannel socketChannel);
}
