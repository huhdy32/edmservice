package com.client.edm.service.mapper;

import com.client.edm.net.Connection;
import com.client.edm.service.EdmClientService;
import com.client.edm.ui.MappingUI;
import com.protocol.edm.ServerServiceCode;

import java.util.List;

public class BasicServiceMapper implements ServiceMapper {
    private final MappingUI mappingUI;
    private final List<EdmClientService> clientServices;

    public BasicServiceMapper(MappingUI mappingUI, List<EdmClientService> clientServices) {
        this.mappingUI = mappingUI;
        this.clientServices = clientServices;
    }

    @Override
    public void mapToService(Connection connection) {
        final String serverMessage = connection.readLine();
        mappingUI.showServiceList(serverMessage);
        final int serviceIndex = mappingUI.getSelectedId();
        connection.send(String.valueOf(serviceIndex) + "\n");

        final String serverResponse = connection.readLine();
        final ServerServiceCode serviceCode = ServerServiceCode.getServiceCode(serverResponse);

        boolean mapped = false;
        System.out.println(mapped);
        for (final EdmClientService edmClientService : clientServices) {
            if (edmClientService.match(serviceCode)) {
                mapped = true;
                edmClientService.doService(connection);
                System.out.println("등록성공");
            }
        }
    }
}
