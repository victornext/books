package com.next.infod.validator;

import com.next.infod.exceptions.ArquivoDuplicado;
import com.next.infod.exceptions.Illegal;
import com.next.infod.model.Client;
import com.next.infod.repositories.ClientRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@AllArgsConstructor
public class ClientValidator {
    @Autowired
    private ClientRepository repository;

    public void validar(Client client) {
        dadosIguais(client); //Verifica se os dados ja são iguais a um usuario ja existente
        if(existeClient(client)) {
            throw new ArquivoDuplicado("Usuario já existente!!!");
        }
    }

    public void dadosIguais(Client client) {
        Optional<Client> clientFinded = repository.findByClientIdIgnoreCase(
                client.getClientId()
        );


    if(clientFinded.isPresent()) {
        Client existingClient = clientFinded.get();

        if(client.getClientId().equals(existingClient.getClientId())) {
            throw new Illegal("O Usuario já  existente!!!");
        }
    }
    }


    private boolean existeClient(Client client) {
        Optional<Client> clientFinded = repository.findByClientIdIgnoreCase(
                client.getClientId()
        );

    if(client.getClientId() == null) {
        return clientFinded.isPresent();
    }

        return clientFinded.isPresent() && !client.getClientId().equals(clientFinded.get().getClientId());
    }


}
