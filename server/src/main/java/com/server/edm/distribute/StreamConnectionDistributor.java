package com.server.edm.distribute;

import com.server.edm.net.BasicMessageTransferManager;
import com.server.edm.net.MessageTransferManager;
import com.server.edm.service.EdmService;
import com.server.edm.service.register.ConnectionManager;

import java.io.IOException;
import java.nio.channels.SocketChannel;
import java.util.List;

/**
 * Java bio를 이용해 스트림 기반으로 통신하여 매핑 수행
 */
public class StreamConnectionDistributor implements ConnectionDistributor {
    private final List<EdmService> edmServices;
    private final ConnectionManager connectionManager;
    private final MessageTransferManager messageTransferManger;

    public StreamConnectionDistributor(List<EdmService> edmServices, ConnectionManager connectionManager, MessageTransferManager messageTransferManger) {
        this.edmServices = edmServices;
        this.connectionManager = connectionManager;
        this.messageTransferManger = messageTransferManger;
    }

    @Override
    public void distribute(final SocketChannel clientSocketChannel) {
        final boolean registered = messageTransferManger.requestUntil(
                clientSocketChannel,
                "다음 서비스 중 택 1 : " + edmServices.toString(),
                clientResponse -> edmServices.stream().anyMatch(edmService -> edmService.register(clientResponse, clientSocketChannel, connectionManager))
        ).isPresent();

        if (!registered) {
            try {
                clientSocketChannel.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }


    private String getAllServiceName() {
        return edmServices.toString();
    }
}
