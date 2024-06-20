package com.client.edm.service.download;

import java.io.*;

public class DownloadInfo {
    private final String fileName;
    private final String dirPath;
    private final long fileSize;
    private final InputStream inputStream;
    private final OutputStream outputStream;

    public DownloadInfo(String fileName, long fileSize, InputStream inputStream, String dirPath) {
        this.fileName = fileName;
        this.dirPath = dirPath;
        this.outputStream = createFile(createFilePath(fileName, dirPath));
        this.inputStream = inputStream;
        this.fileSize = fileSize;
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public String getFileName() {
        return fileName;
    }

    public OutputStream getOutputStream() {
        return outputStream;
    }

    public String getDirPath() {
        return this.dirPath;
    }

    public long getFileSize() {
        return fileSize;
    }

    public int getTransferredPercentage(final long currTransferred) {
        return (int) (currTransferred * 100 / fileSize);
    }

    private String createFilePath(final String fileName, final String dirPath) {
        return dirPath + "/" + fileName;
    }

    private DataOutputStream createFile(final String filePath) {
        try {
            System.out.println("파일 생성 " + filePath);
            return new DataOutputStream(new FileOutputStream(filePath));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
