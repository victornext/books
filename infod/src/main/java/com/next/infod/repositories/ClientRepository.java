package com.next.infod.repositories;


import com.next.infod.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ClientRepository extends JpaRepository<Client, UUID> {


    Client findByClientId(String clientId);
    Optional<Client> findByClientIdIgnoreCase(String clientId); // Busca por ClientId com nome diferente
}
