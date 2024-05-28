package com.server.edm.service.factory;

import com.server.edm.service.EdmService;

import java.util.List;

public interface ServiceFactory {
    EdmService create(final String serviceName);
}
