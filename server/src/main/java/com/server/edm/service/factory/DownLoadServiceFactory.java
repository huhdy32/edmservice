package com.server.edm.service.factory;

import com.server.edm.service.EdmService;
import com.server.edm.service.WebResourceAccessManager;
import com.server.edm.service.client.ClientManager;
import com.server.edm.service.downlaod.DownLoadService;
import com.server.edm.service.downlaod.distribute.FileDistributingManager;
import com.server.edm.service.downlaod.ui.DownloadServiceUI;

public class DownLoadServiceFactory implements ServiceFactory {
    private final ClientManager clientManager;
    private final WebResourceAccessManager webResourceAccessManager;
    private final DownloadServiceUI downloadServiceUI;
    private final FileDistributingManager fileDistributingManager;

    public DownLoadServiceFactory(ClientManager clientManager,
                                  WebResourceAccessManager webResourceAccessManager,
                                  DownloadServiceUI downloadServiceUI,
                                  FileDistributingManager fileDistributingManager) {
        this.clientManager = clientManager;
        this.webResourceAccessManager = webResourceAccessManager;
        this.downloadServiceUI = downloadServiceUI;
        this.fileDistributingManager = fileDistributingManager;
    }

    @Override
    public EdmService create(String serviceName) {
        return new DownLoadService(serviceName, clientManager, webResourceAccessManager, downloadServiceUI, fileDistributingManager);
    }

    @Override
    public String toString() {
        return "DownLoadServiceFactory";
    }
}
