package com.next.infod.security;

import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class CustomRegisteredClientRepository implements RegisteredClientRepository {

    private final Map<String, RegisteredClient> registeredClients = new HashMap<>();

    @Override
    public void save(RegisteredClient registeredClient) {
        if (registeredClient == null) {
            throw new IllegalArgumentException("RegisteredClient cannot be null");
        }
        registeredClients.put(registeredClient.getId(), registeredClient);
    }

    @Override
    public RegisteredClient findById(String id) {
        return registeredClients.get(id);
    }

    @Override
    public RegisteredClient findByClientId(String clientId) {
        return registeredClients.values().stream()
                .filter(client -> client.getClientId().equals(clientId))
                .findFirst()
                .orElse(null);
    }
}
