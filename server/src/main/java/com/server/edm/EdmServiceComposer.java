package com.server.edm;

import com.server.edm.distribute.ConnectionDistributor;
import com.server.edm.distribute.StreamConnectionDistributor;
import com.server.edm.log.ConsoleLogger;
import com.server.edm.log.Logger;
import com.server.edm.net.*;
import com.server.edm.service.BasicServiceManager;
import com.server.edm.service.ServiceManager;
import com.server.edm.service.StreamWebResourceAccessManager;
import com.server.edm.service.WebResourceAccessManager;
import com.server.edm.service.client.BasicClientManager;
import com.server.edm.service.client.ClientManager;
import com.server.edm.service.downlaod.distribute.BasicFileDistributingManager;
import com.server.edm.service.downlaod.distribute.FileDistributingManager;
import com.server.edm.service.downlaod.ui.ConsoleDownloadServiceUI;
import com.server.edm.service.downlaod.ui.DownloadServiceUI;
import com.server.edm.service.factory.BasicFactoryManager;
import com.server.edm.service.factory.DownLoadServiceFactory;
import com.server.edm.service.factory.FactoryManager;
import com.server.edm.ui.ConsoleInputUI;
import com.server.edm.ui.ConsoleOutputUI;
import com.server.edm.ui.InputUI;
import com.server.edm.ui.OutputUI;

import java.io.IOException;
import java.nio.channels.Selector;
import java.util.List;

public class EdmServiceComposer {
    private final OutputUI outputUI;
    private final InputUI inputUI;
    private final Logger logger;

    public EdmServiceComposer() {
        this.outputUI = new ConsoleOutputUI();
        this.inputUI = new ConsoleInputUI();
        this.logger = new ConsoleLogger(outputUI);
    }

    public void start() {
        outputUI.printLogo();

        final Encoder encoder = getEncoder();

        final DataTransferManager dataTransferManager = getDataTransferManager();
        final MessageTransferManager messageTransferManager = getMessageTransferManager(encoder, dataTransferManager);
        final FileDistributingManager fileDistributingManager = getFileDistributingManager(dataTransferManager, messageTransferManager);

        final ServiceManager serviceManager = getServiceManager();
        final ClientManager clientManager = getClientManager(messageTransferManager);
        final WebResourceAccessManager webResourceAccessManager = getWebResourceAccessManager();


        final DownloadServiceUI downloadServiceUI = getDownloadServiceUI(outputUI, inputUI);
        final ConnectionDistributor connectionDistributor = getConnectionDistributor(messageTransferManager, serviceManager, clientManager);

        final Selector selector;

        final DownLoadServiceFactory downLoadServiceFactory = getDownLoadServiceFactory(fileDistributingManager, clientManager, webResourceAccessManager, downloadServiceUI);
        final FactoryManager factoryManager = getFactoryManager(downLoadServiceFactory);

        final Thread serverThread;
        try {
            selector = Selector.open();
            final EdmListeningSocket listeningSocket = new EdmListeningSocket(selector, 12232, connectionDistributor);
            serverThread = new Thread(listeningSocket);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        final EdmServiceController edmServiceController = getEdmServiceController(serverThread, outputUI, inputUI, serviceManager, factoryManager);
        new Thread(edmServiceController).start();
    }

    private void logInfo(Object object) {
        logger.info("INITIALIZED : " + object.toString());
    }

    private EdmServiceController getEdmServiceController(Thread serverThread, OutputUI outputUI, InputUI inputUI, ServiceManager serviceManager, FactoryManager factoryManager) {
        final EdmServiceController edmServiceController = new EdmServiceController(serviceManager, factoryManager, serverThread, inputUI, outputUI);
        logInfo(edmServiceController);
        return edmServiceController;
    }

    private FactoryManager getFactoryManager(DownLoadServiceFactory downLoadServiceFactory) {
        final BasicFactoryManager basicFactoryManager = new BasicFactoryManager(List.of(downLoadServiceFactory));
        logInfo(basicFactoryManager.toString());
        return basicFactoryManager;
    }

    private DownLoadServiceFactory getDownLoadServiceFactory(FileDistributingManager fileDistributingManager, ClientManager clientManager, WebResourceAccessManager webResourceAccessManager, DownloadServiceUI downloadServiceUI) {
        final DownLoadServiceFactory downLoadServiceFactory = new DownLoadServiceFactory(clientManager, webResourceAccessManager, downloadServiceUI, fileDistributingManager);
        logInfo(downLoadServiceFactory.toString());
        return downLoadServiceFactory;
    }

    private ConnectionDistributor getConnectionDistributor(MessageTransferManager messageTransferManager, ServiceManager serviceManager, ClientManager clientManager) {
        final StreamConnectionDistributor streamConnectionDistributor = new StreamConnectionDistributor(serviceManager, clientManager, messageTransferManager);
        logInfo(streamConnectionDistributor.toString());
        return streamConnectionDistributor;
    }

    private DownloadServiceUI getDownloadServiceUI(OutputUI outputUI, InputUI inputUI) {
        final ConsoleDownloadServiceUI consoleDownloadServiceUI = new ConsoleDownloadServiceUI(outputUI, inputUI);
        logInfo(consoleDownloadServiceUI);
        return consoleDownloadServiceUI;
    }

    private WebResourceAccessManager getWebResourceAccessManager() {
        final StreamWebResourceAccessManager streamWebResourceAccessManager = new StreamWebResourceAccessManager();
        logInfo(streamWebResourceAccessManager.toString());
        return streamWebResourceAccessManager;
    }

    private ClientManager getClientManager(MessageTransferManager messageTransferManager) {
        final BasicClientManager basicClientManager = new BasicClientManager(messageTransferManager);
        logInfo(basicClientManager);
        return basicClientManager;
    }

    private ServiceManager getServiceManager() {
        final BasicServiceManager basicServiceManager = new BasicServiceManager();
        logInfo(basicServiceManager.toString());
        return basicServiceManager;
    }

    private FileDistributingManager getFileDistributingManager(DataTransferManager dataTransferManager, MessageTransferManager messageTransferManager) {
        final BasicFileDistributingManager basicFileDistributingManager = new BasicFileDistributingManager(messageTransferManager, dataTransferManager);
        logInfo(basicFileDistributingManager.toString());
        return basicFileDistributingManager;
    }

    private MessageTransferManager getMessageTransferManager(Encoder encoder, DataTransferManager dataTransferManager) {
        final BasicMessageTransferManager basicMessageTransferManager = new BasicMessageTransferManager(dataTransferManager, encoder);
        logInfo(basicMessageTransferManager.toString());
        return basicMessageTransferManager;
    }

    private DataTransferManager getDataTransferManager() {
        final BufferBasedDataTransferManager bufferBasedDataTransferManager = new BufferBasedDataTransferManager();
        logInfo(bufferBasedDataTransferManager.toString());
        return bufferBasedDataTransferManager;
    }

    private Encoder getEncoder() {
        final UTFEncoder utfEncoder = new UTFEncoder();
        logInfo(utfEncoder);
        return utfEncoder;
    }
}
