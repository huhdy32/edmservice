package com.server.edm.service.downlaod;

import com.protocol.edm.ServerServiceCode;
import com.server.edm.net.MessageTransferManager;
import com.server.edm.service.EdmService;
import com.server.edm.service.ServiceCategory;
import com.server.edm.service.WebResource;
import com.server.edm.service.WebResourceAccessManager;
import com.server.edm.service.client.ClientManager;
import com.server.edm.service.downlaod.distribute.FileDistributingManager;
import com.server.edm.service.downlaod.ui.DownloadServiceUI;

import java.io.BufferedInputStream;
import java.nio.channels.SocketChannel;
import java.util.Set;

/**
 * Download Service v1.0
 * 클라이언트가 접속하고 나면, 후에 서버가 어떤 파일을 웹으로부터 다운로드 받을지 정하는 방식
 */
public class DownLoadService implements EdmService {
    private static final ServiceCategory CATEGORY = ServiceCategory.DOWNLOAD;
    private final String serviceName;
    private final ClientManager clientManager;
    private final WebResourceAccessManager webResourceAccessManager;
    private final MessageTransferManager messageTransferManager;
    private final DownloadServiceUI downloadServiceUI;
    private final FileDistributingManager fileDistributingManager;
    private Boolean running = false;

    public DownLoadService(final String serviceName, final ClientManager clientManager, final WebResourceAccessManager webResourceAccessManager, final DownloadServiceUI downloadServiceUI, final FileDistributingManager fileDistributingManager, final MessageTransferManager messageTransferManager) {
        this.serviceName = serviceName;
        this.clientManager = clientManager;
        this.webResourceAccessManager = webResourceAccessManager;
        this.downloadServiceUI = downloadServiceUI;
        this.fileDistributingManager = fileDistributingManager;
        this.messageTransferManager = messageTransferManager;
    }

    @Override
    public boolean register(final SocketChannel socketChannel) {
        if (getCurrState()) {
            throw new IllegalArgumentException("이미 다운로드가 시작되었습니다.");
        }
        messageTransferManager.send(socketChannel, ServerServiceCode.DOWNLOAD_SERVICE.getServerResponse());
        return clientManager.register(this, socketChannel);
    }

    @Override
    public void doService() {
        // 서버 사용자로부터 어떤 파일을 다운로드 받을지 지정하는 로직
        if (getCurrState()) {
            throw new IllegalArgumentException("이미 실행중인 서비스 입니다");
        }
        changeCurrState(true);
        final String url = downloadServiceUI.getUrl();
        final String fileName = downloadServiceUI.getFileName();

        final WebResource webResource = webResourceAccessManager.access(url);
        final Set<SocketChannel> channels = clientManager.getChannels(this);

        fileDistributingManager.distribute(channels, webResource, fileName);
        changeCurrState(false);
    }

    private void changeCurrState(final boolean running) {
        synchronized (this.running) {
            this.running = running;
        }
    }

    private boolean getCurrState() {
        synchronized (this.running) {
            return this.running;
        }
    }

    @Override
    public String getName() {
        return serviceName;
    }

    @Override
    public boolean isRunning() {
        return this.getCurrState();
    }

    @Override
    public ServiceCategory getCategory() {
        return CATEGORY;
    }

    @Override
    public int getClientSize() {
        return clientManager.getChannels(this).size();
    }
}
