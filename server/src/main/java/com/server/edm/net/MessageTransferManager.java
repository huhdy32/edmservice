package com.server.edm.net;

import java.nio.channels.SocketChannel;
import java.util.Optional;
import java.util.function.Predicate;

/**
 * predicate와 클라이언트의 응답이 같을때까지 클라이언트에게 text 를 보내는 인터페이스 입니다.
 */
public interface MessageTransferManager {
    Optional<String> requestUntil(final SocketChannel socketChannel, final String request, final Predicate<String> predicate);
    Optional<String> requestUntil(final SocketChannel socketChannel, final String request, final Predicate<String> predicate, final int tryCount);
    boolean request(final SocketChannel socketChannel, final String request, final Predicate<String> predicate);
}

