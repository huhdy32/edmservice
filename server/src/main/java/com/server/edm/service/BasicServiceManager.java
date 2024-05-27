package com.server.edm.service;

import com.server.edm.service.dto.ServiceStatus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class BasicServiceManager implements ServiceManager {
    private final List<EdmService> edmServices = new CopyOnWriteArrayList<>();

    @Override
    public void addService(final EdmService edmService) {
        edmServices.add(edmService);
    }

    @Override
    public List<ServiceStatus> getServicesInfo() {
        final List<ServiceStatus> statuses = new ArrayList<>();
        for (int i = 0; i < edmServices.size(); i++) {
            final EdmService service = edmServices.get(i);
            statuses.add(new ServiceStatus(i, service.getName(), service.getCategory(), service.isRunning(), service.getClientSize()));
        }
        return Collections.unmodifiableList(statuses);
    }

    @Override
    public List<EdmService> getAllServices() {
        return Collections.unmodifiableList(this.edmServices);
    }

    @Override
    public boolean remove(EdmService edmService) {
        return edmServices.remove(edmService);
    }

    @Override
    public EdmService getService(final int serviceNumber) {
        if (serviceNumber < 0 || serviceNumber >= this.edmServices.size()) {
            throw new IllegalArgumentException("해당 서비스가 존재하지 않습니다");
        }
        return this.edmServices.get(serviceNumber);
    }
}
