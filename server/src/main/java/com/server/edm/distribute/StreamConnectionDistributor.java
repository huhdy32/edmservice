package com.server.edm.distribute;

import com.server.edm.net.DataTransferManager;
import com.server.edm.service.EdmService;

import java.io.IOException;
import java.nio.channels.SocketChannel;
import java.util.List;

/**
 * Java bio를 이용해 스트림 기반으로 통신하여 매핑 수행
 */
public class StreamConnectionDistributor implements ConnectionDistributor {
    private final List<EdmService> edmServices;
    private final DataTransferManager dataTransferManager;

    public StreamConnectionDistributor(final List<EdmService> edmServices, final DataTransferManager dataTransferManager) {
        this.edmServices = edmServices;
        this.dataTransferManager = dataTransferManager;
    }

    @Override
    public void distribute(final SocketChannel clientSocketChannel) {
        int errorCount = 0;

        while (true) {
            final String clientCommand = dataTransferManager.sendAndRecieve(clientSocketChannel, ("다음 서비스 중 택 1 : " + getAllServiceName()).getBytes());
            for (final EdmService edmService : edmServices) {
                if (edmService.isAcceptable(clientCommand, clientSocketChannel)) {
                    return;
                }
            }
            dataTransferManager.send(clientSocketChannel, ("[ERROR] 잘못된 입력 [" + errorCount + "] 회").getBytes());
            errorCount += 1;
            if(errorCount == 5) {
                dataTransferManager.send(clientSocketChannel, " 접속 종료 ".getBytes());
                try {
                    clientSocketChannel.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                throw new IllegalArgumentException("잘못된 입력");
            }
        }
    }

    private String getAllServiceName() {
        return edmServices.toString();
    }
}
