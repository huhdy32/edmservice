package com.client.edm.service;

import java.util.List;

public interface ServiceClientManager {
    List<?> getAllServiceStatuses();
    boolean addService();
}
