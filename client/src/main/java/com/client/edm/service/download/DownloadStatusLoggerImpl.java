package com.client.edm.service.download;

import com.client.edm.log.Logger;

import java.io.Console;
import java.util.logging.ConsoleHandler;

public class DownloadStatusLoggerImpl implements DownloadStatusLogger {

    private final Logger logger;

    public DownloadStatusLoggerImpl(Logger logger) {
        this.logger = logger;
    }

    @Override
    public void printDownloadInfo(DownloadInfo downloadInfo) {
        logger.print("[ DOWNLOAD PROGRESS ] \n " + " DIR PATH : " + downloadInfo.getDirPath() + "\n" +
                "FILE NAME : " + downloadInfo.getFileName() + "\n");
    }

    @Override
    public void printTransferState(DownloadInfo downloadInfo, long currTransferred) {
        final int blockCount = downloadInfo.getTransferredPercentage(currTransferred);

        final StringBuilder sb = new StringBuilder();
        logger.print("\r" + " CURR STATUS : ");
        sb.append("[");
        for (int i = 0; i < 100 ; i ++) {
            if (i <= blockCount) {
                sb.append("#");
                continue;
            }
            sb.append(" ");
        }
        sb.append("]");
        sb.append("  " + blockCount + "%  ");
        sb.append("    " + currTransferred + " / " + downloadInfo.getFileSize());
        logger.print(sb.toString());
    }
}
