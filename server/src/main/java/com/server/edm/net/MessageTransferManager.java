package com.server.edm.net;

import java.nio.channels.SocketChannel;
import java.util.Optional;
import java.util.function.Predicate;

public interface MessageTransferManager {
    Optional<String> requestUntil(final SocketChannel socketChannel, final String request, final Predicate<String> predicate);
    Optional<String> requestUntil(final SocketChannel socketChannel, final String request, final Predicate<String> predicate, final int tryCount);
    boolean request(final SocketChannel socketChannel, final String request, final Predicate<String> predicate);
}

