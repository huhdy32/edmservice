package com.server.edm.service;

import com.server.edm.service.dto.ServiceStatus;

import java.util.List;

public interface ServiceManager {
    void addService(final EdmService edmService);

    List<ServiceStatus> getServicesInfo();

    List<EdmService> getAllServices();

    boolean remove(final EdmService edmService);

    EdmService getService(final int serviceNumber);
}
