package com.server.edm.service.dto;

import com.server.edm.service.ServiceCategory;

public record ServiceStatus(int id, String name, ServiceCategory category, boolean running, int clientSize) {
}
