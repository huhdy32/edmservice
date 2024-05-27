package com.server.edm.net;

import java.nio.channels.SocketChannel;
import java.util.Optional;
import java.util.function.Predicate;

public class BasicMessageTransferManager implements MessageTransferManager {
    private final DataTransferManager dataTransferManager;
    private final Encoder encoder;

    private final int MAX_TRY_COUNT = 5;

    public BasicMessageTransferManager(final DataTransferManager dataTransferManager, final Encoder encoder) {
        this.dataTransferManager = dataTransferManager;
        this.encoder = encoder;
    }

    /**
     * @param socketChannel 전송할 대상입니다.
     * @param request       전송할 데이터 입니다.
     * @param predicate     MAX_TRY_COUNT = 5 입니다. 5번의 시도에도 predicate 결과가 false 이면, 빈 optional 을 리턴합니다.
     * @return
     */
    @Override
    public Optional<String> requestUntil(final SocketChannel socketChannel, final String request, final Predicate<String> predicate) {
        final byte[] data = encoder.encode(request);
        return request(socketChannel, data, predicate, MAX_TRY_COUNT);
    }

    @Override
    public Optional<String> requestUntil(final SocketChannel socketChannel, final String request, final Predicate<String> predicate, final int tryCount) {
        final byte[] data = encoder.encode(request);
        return request(socketChannel, data, predicate, tryCount);
    }

    @Override
    public boolean request(SocketChannel socketChannel, String request, Predicate<String> predicate) {
        return request(socketChannel, encoder.encode(request), predicate, 1).isPresent();
    }

    private Optional<String> request(final SocketChannel socketChannel, final byte[] data, final Predicate<String> predicate, final int tryCount) {
        for (int i = 0; i < tryCount; i++) {
            final byte[] result = dataTransferManager.sendAndRecieve(socketChannel,data, data.length);
            final String decodedResult = encoder.decode(result);
            if (predicate.test(decodedResult)) {
                return Optional.of(decodedResult);
            }
        }
        return Optional.empty();
    }
}