package com.server.edm.service.factory;

import java.util.Collections;
import java.util.List;

public class BasicFactoryManager implements FactoryManager {
    private final List<ServiceFactory> serviceFactories;

    public BasicFactoryManager(List<ServiceFactory> serviceFactories) {
        this.serviceFactories = serviceFactories;
    }

    @Override
    public List<ServiceFactory> getAllFactories() {
        return Collections.unmodifiableList(serviceFactories);
    }
}
