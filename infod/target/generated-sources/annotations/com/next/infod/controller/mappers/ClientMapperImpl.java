package com.next.infod.controller.mappers;

import com.next.infod.controller.DTOS.ClientDTO;
import com.next.infod.model.Client;
import java.util.UUID;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-01-23T23:41:09-0300",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.5 (Azul Systems, Inc.)"
)
@Component
public class ClientMapperImpl implements ClientMapper {

    @Override
    public Client toEntity(ClientDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Client client = new Client();

        client.setId( dto.id() );
        client.setClientId( dto.clientId() );
        client.setClientSecret( dto.clientSecret() );
        client.setRedirectURI( dto.redirectURI() );
        client.setScope( dto.scope() );

        return client;
    }

    @Override
    public ClientDTO toDTO(Client client) {
        if ( client == null ) {
            return null;
        }

        UUID id = null;
        String clientId = null;
        String clientSecret = null;
        String redirectURI = null;
        String scope = null;

        id = client.getId();
        clientId = client.getClientId();
        clientSecret = client.getClientSecret();
        redirectURI = client.getRedirectURI();
        scope = client.getScope();

        ClientDTO clientDTO = new ClientDTO( id, clientId, clientSecret, redirectURI, scope );

        return clientDTO;
    }
}
