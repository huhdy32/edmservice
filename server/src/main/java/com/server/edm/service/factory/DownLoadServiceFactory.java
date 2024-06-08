package com.server.edm.service.factory;

import com.server.edm.net.MessageTransferManager;
import com.server.edm.service.EdmService;
import com.server.edm.service.web.WebResourceAccessManager;
import com.server.edm.service.client.ClientManager;
import com.server.edm.service.downlaod.DownLoadService;
import com.server.edm.service.downlaod.distribute.FileDistributingManager;
import com.server.edm.service.downlaod.ui.DownloadServiceUI;

public class DownLoadServiceFactory implements ServiceFactory {
    private final ClientManager clientManager;
    private final WebResourceAccessManager webResourceAccessManager;
    private final DownloadServiceUI downloadServiceUI;
    private final FileDistributingManager fileDistributingManager;
    private final MessageTransferManager messageTransferManager;

    public DownLoadServiceFactory(ClientManager clientManager,
                                  WebResourceAccessManager webResourceAccessManager,
                                  DownloadServiceUI downloadServiceUI,
                                  FileDistributingManager fileDistributingManager,
                                  MessageTransferManager messageTransferManager) {
        this.clientManager = clientManager;
        this.webResourceAccessManager = webResourceAccessManager;
        this.downloadServiceUI = downloadServiceUI;
        this.fileDistributingManager = fileDistributingManager;
        this.messageTransferManager = messageTransferManager;
    }

    @Override
    public EdmService create(String serviceName) {
        return new DownLoadService(serviceName, clientManager, webResourceAccessManager, downloadServiceUI, fileDistributingManager, messageTransferManager);
    }

    @Override
    public String toString() {
        return "DownLoadServiceFactory";
    }
}
