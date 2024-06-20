package com.client.edm.service.download;

/**
 * 현재 다운로드 현황읇 로깅하는 프로그램입니다.
 * 100% 기준으로 출력합니다.
 */
public interface DownloadStatusLogger {
    void printDownloadInfo(final DownloadInfo downloadInfo);
    void printTransferState(final DownloadInfo downloadInfo, final long currTransferred);
}
