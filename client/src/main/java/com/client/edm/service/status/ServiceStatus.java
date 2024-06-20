package com.client.edm.service.status;

public interface ServiceStatus {
    boolean running();
    int getProgressedRate();
    boolean isCompleted();
}
