package com.server.edm.service.downlaod;

import com.server.edm.service.EdmService;
import com.server.edm.service.WebResourceAccessManager;
import com.server.edm.service.client.ClientManager;

import java.nio.channels.SocketChannel;

/**
 * Download Service v1.0
 * 클라이언트가 접속하고 나면, 후에 서버가 어떤 파일을 웹으로부터 다운로드 받을지 정하는 방식
 */
public class DownLoadService implements EdmService {
    private static final String DOWNLOAD = "download";
    private final ClientManager clientManager;

    public DownLoadService(final ClientManager clientManager) {
        this.clientManager = clientManager;
    }

    @Override
    public boolean register(final String clientRequestServiceName, final SocketChannel socketChannel) {
        if (clientRequestServiceName.equals(DOWNLOAD)) {
            clientManager.register(this, socketChannel);
            return true;
        }
        return false;
    }

    @Override
    public void doService() {
        // 서버 사용자로부터 어떤 파일을 다운로드 받을지 지정하는 로직
        // 웹 url 을 입력받아
    }

    @Override
    public String toString() {
        return DOWNLOAD;
    }
}
