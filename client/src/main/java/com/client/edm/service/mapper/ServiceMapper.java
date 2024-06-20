package com.client.edm.service.mapper;

import com.client.edm.net.Connection;

/**
 * 서버와의 통신으로 특정 서비스와 매핑시키는 역할을 수행합니다.
 */
public interface ServiceMapper {
    void mapToService(final Connection connection);
}
