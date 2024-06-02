package com.server.edm.distribute;

import com.server.edm.net.MessageTransferManager;
import com.server.edm.service.EdmService;
import com.server.edm.service.ServiceManager;
import com.server.edm.service.client.ClientManager;

import java.io.IOException;
import java.nio.channels.SocketChannel;

/**
 * Java bio를 이용해 스트림 기반으로 통신하여 매핑 수행
 */
public class StreamConnectionDistributor implements ConnectionDistributor {
    private final ServiceManager serviceManager;
    private final ClientManager clientManager;
    private final MessageTransferManager messageTransferManger;

    public StreamConnectionDistributor(ServiceManager serviceManager, ClientManager connectionManager, MessageTransferManager messageTransferManger) {
        this.serviceManager = serviceManager;
        this.clientManager = connectionManager;
        this.messageTransferManger = messageTransferManger;
    }

    @Override
    public void distribute(final SocketChannel clientSocketChannel) {
        final boolean registered = messageTransferManger.requestUntil(
                clientSocketChannel,
                "다음 서비스 중 택 1 : " + serviceManager.getServicesInfo().toString() + "\n",
                clientResponse -> {
                    try{
                        final int serviceNumber = Integer.parseInt(clientResponse);
                        final EdmService edmService = serviceManager.getService(serviceNumber);
                        return edmService.register(clientSocketChannel);
                    } catch (Exception e) {
                        e.printStackTrace();
                        return false;
                    }
                }
        ).isPresent();

        if (!registered) {
            try {
                messageTransferManger.send(clientSocketChannel, "다시 하쇼");
                clientSocketChannel.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        messageTransferManger.send(clientSocketChannel, "연결 성곤");
    }
}
