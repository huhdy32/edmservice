package com.client.edm.service.status;

import com.client.edm.service.status.ServiceStatus;

public class DownloadServiceStatus implements ServiceStatus {

    @Override
    public boolean running() {
        return false;
    }

    @Override
    public int getProgressedRate() {
        return 0;
    }

    @Override
    public boolean isCompleted() {
        return false;
    }
}
