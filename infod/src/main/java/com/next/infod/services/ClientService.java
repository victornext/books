package com.next.infod.services;


import com.next.infod.model.Client;
import com.next.infod.repositories.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository repository;
    private final PasswordEncoder encoder;


    public Client salvar(Client client) {
        var senhaCriptografada = encoder.encode(client.getClientSecret());
        client.setClientSecret(senhaCriptografada);
        return repository.save(client);
    }

    public Client obterPorClientId(String clientId){
        return repository.findByClientId(clientId);
    }
}
