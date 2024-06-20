package com.client.edm.service.download;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Objects;

public class FileTransferManagerImpl implements FileTransferManager {
    private static final int DEFAULT_BUFFER_SIZE = 1024;
    private final DownloadStatusLogger downloadStatusLogger;

    public FileTransferManagerImpl(DownloadStatusLogger downloadStatusLogger) {
        this.downloadStatusLogger = downloadStatusLogger;
    }

    @Override
    public void download(final DownloadInfo downloadInfo) throws IOException{
        final OutputStream output = downloadInfo.getOutputStream();
        final InputStream input = downloadInfo.getInputStream();

        long transferred = 0L;
        byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
        int read;
        downloadStatusLogger.printDownloadInfo(downloadInfo);
        while ((read = input.read(buffer, 0, DEFAULT_BUFFER_SIZE)) >= 0) {
            output.write(buffer, 0, read);
            transferred += read;
            downloadStatusLogger.printTransferState(downloadInfo, transferred);
        }
    }
}