package com.server.edm.service.client;

import com.server.edm.net.DataTransferManager;
import com.server.edm.net.MessageTransferManager;
import com.server.edm.service.EdmService;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.nio.channels.SocketChannel;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class BasicClientManager implements ClientManager {
    private final Set<Client> clients = new HashSet<>();
    private final MessageTransferManager messageTransferManger;

    public BasicClientManager(MessageTransferManager messageTransferManger) {
        this.messageTransferManger = messageTransferManger;
    }

    /**
     * 해당 함수를 호출 시, 서비스에서 사용할 클라이언트의 이름을 지정하는 로직이 시작됩니다.
     * @param edmService 클라이언트가 등록하고자 했던 서비스.
     * @param socketChannel 연결된 세션
     */
    @Override
    public void register(EdmService edmService, SocketChannel socketChannel) {
        final Client client = createClient(edmService, socketChannel);
        clients.add(client);
    }

    @Override
    public Set<SocketChannel> getChannels(final EdmService edmService) {
        return clients.stream()
                .filter(client -> client.edmService.equals(edmService))
                .map(client -> client.socketChannel)
                .collect(Collectors.toSet());
    }

    /**
     * SocketChannel을 닫고, 서비스에 연결을 해제합니다.
     * 서비스에 등록되지 않은 SocketChannel일 경우, 연결을 종료합니다.
     * @param socketChannel edmService에 등록된 socketChannel
     */
    @Override
    public void drop(SocketChannel socketChannel) {
        try {
            socketChannel.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        clients.removeIf(client -> client.socketChannel.equals(socketChannel));
    }

    private Client createClient(final EdmService edmService, final SocketChannel socketChannel) {
        final String clientName = requestName(socketChannel, edmService);
        return new Client(clientName, edmService, socketChannel);
    }

    private String requestName(final SocketChannel socketChannel, final EdmService edmService) {
        final String request = "사용할 이름을 알려주세요.";
        final String clientName = messageTransferManger.requestUntil(
                socketChannel,
                request,
                clientResponse -> !clients.contains(new Client(clientResponse, edmService, socketChannel))
        ).orElseThrow(() -> new IllegalArgumentException("계속 이상한 이름 보냄 이놈 탈락!!"));
        return clientName;
    }

    /**
     * '해당 서버에 접속중인 클라이언트'
     */
    private static class Client {
        private final String name;
        private final EdmService edmService;
        private final SocketChannel socketChannel;

        private Client(String name, EdmService edmService, SocketChannel socketChannel) {
            this.name = name;
            this.edmService = edmService;
            this.socketChannel = socketChannel;
        }

        /**
         * 동일한 edmService에 같은 이름이 존재하지 않도록 하기 위함
         *
         */
        @Override
        public boolean equals(Object o) {

            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Client client = (Client) o;
            return name.equals(client.name) && edmService.equals(client.edmService);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name, edmService);
        }
    }
}
