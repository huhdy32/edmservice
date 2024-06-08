package com.server.edm.net;

import java.nio.channels.SocketChannel;
import java.util.Optional;
import java.util.function.Predicate;

/**
 * 클라이언트에게 기본값으로 최대 5번 요청을 보냅니다. predicate와 클라이언트의 응답값이 일치한다면 바로 리턴합니다.
 */
public class BasicMessageTransferManager implements MessageTransferManager {
    private final DataTransferManager dataTransferManager;
    private final Encoder encoder;

    private final int MAX_TRY_COUNT = 5;

    public BasicMessageTransferManager(final DataTransferManager dataTransferManager, final Encoder encoder) {
        this.dataTransferManager = dataTransferManager;
        this.encoder = encoder;
    }

    @Override
    public void send(SocketChannel socketChannel, String message) {
        final byte[] data = encoder.encode(addNewLine(message));
        dataTransferManager.send(socketChannel, data, data.length);
    }

    /**
     * MAX_TRY_COUNT = 5 입니다. 5번의 시도에도 predicate 결과가 false 이면, 빈 optional 을 리턴합니다.
     * @param socketChannel 전송할 대상입니다.
     * @param request       전송할 데이터 입니다.
     * @param predicate     예측할 수 있는 클라이언트의 응답값입니다.
     * @return 클라이언트로부터의 응답 값입니다. predicate와 일치하지 않을 경우, 빈 Optional 을 리턴합니다.
     */
    @Override
    public Optional<String> requestUntil(final SocketChannel socketChannel, final String request, final Predicate<String> predicate) {
        final byte[] data = encoder.encode(this.addNewLine(request));
        return request(socketChannel, data, predicate, MAX_TRY_COUNT);
    }

    /**
     * @param socketChannel 전송할 대상입니다.
     * @param request       전송할 데이터 입니다.
     * @param predicate     예측할 수 있는 클라이언트의 응답값입니다.
     * @param tryCount      최대 요청 횟수입니다.
     * @return 클라이언트로부터의 응답 값입니다. predicate와 일치하지 않을 경우, 빈 Optional 을 리턴합니다.
     */
    @Override
    public Optional<String> requestUntil(final SocketChannel socketChannel, final String request, final Predicate<String> predicate, final int tryCount) {
        final byte[] data = encoder.encode(this.addNewLine(request));
        return request(socketChannel, data, predicate, tryCount);
    }

    /**
     * 1번의 요청을 보냅니다.
     * @param socketChannel 전송할 대상입니다.
     * @param request       전송할 데이터 입니다.
     * @param predicate     예측할 수 있는 클라이언트의 응답값입니다.
     * @return 클라이언트로부터의 응답 값입니다. predicate와 일치하지 않을 경우, 빈 Optional 을 리턴합니다.
     */
    @Override
    public boolean request(final SocketChannel socketChannel, final String request, final Predicate<String> predicate) {
        return request(socketChannel, encoder.encode(this.addNewLine(request)), predicate, 1).isPresent();
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

    private String addNewLine(final String message) {
        return message +"\n";
    }
}