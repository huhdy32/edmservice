package com.client.edm.service.download;

import com.client.edm.net.Connection;
import com.client.edm.service.EdmClientService;
import com.protocol.edm.DownloadCommand;
import com.protocol.edm.ServerServiceCode;

import java.io.*;

public class DownloadClientService implements EdmClientService {

    private final DownloadUI downloadUI;
    private final FileTransferManager fileTransferManager;

    public DownloadClientService(DownloadUI downloadUI, FileTransferManager fileTransferManager) {
        this.downloadUI = downloadUI;
        this.fileTransferManager = fileTransferManager;
    }

    @Override
    public void doService(final Connection connection) {
        connection.send("OK");
        final String clientName = downloadUI.getClientName(connection.readLine());
        connection.send(clientName);
        final String directoryPath = downloadUI.getDirectoryPath();

        // waiting for server service start
        final String[] serverMessage = connection.readLine().split(" ");
        final DownloadCommand downloadCommand = DownloadCommand.getCommand(serverMessage[0]);
        if (serverMessage.length == 3 && downloadCommand.equals(DownloadCommand.START_DOWNLOAD)) {
            final Long fileSize = Long.parseLong(serverMessage[1]);
            final String fileName = serverMessage[2];
            final InputStream inputStream = connection.getInputStream();
            connection.send("OK");
            try {
                final DownloadInfo downloadInfo = new DownloadInfo(fileName, fileSize, inputStream, directoryPath);
                fileTransferManager.download(downloadInfo);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            throw new IllegalArgumentException("잘못된 접근입니다.");
        }
    }

    @Override
    public boolean match(ServerServiceCode serviceCode) {
        if(serviceCode.equals(ServerServiceCode.DOWNLOAD_SERVICE)) {
            return true;
        }
        return false;
    }
}
