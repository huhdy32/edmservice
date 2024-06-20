package com.client.edm.service.download;

import java.io.IOException;

/**
 * input으로부터 output으로부터 바이트 단위로 데이터를 전송하며, 매 루프마다 현재 전송량을 출력합니다.
 */
public interface FileTransferManager {
    void download(DownloadInfo fileStatus) throws IOException;
}
