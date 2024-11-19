package com.next.infod.validator;


import com.next.infod.exceptions.ArquivoDuplicado;
import com.next.infod.model.BooksModel;
import com.next.infod.repositories.BooksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AutorValidator {
    @Autowired
    private BooksRepository repositorio;


    public void validar(BooksModel autor){
        if(existeAutor(autor)) {
            throw new ArquivoDuplicado("Autor Ja Cadastrado!");
        }
    }

    private boolean existeAutor(BooksModel autor){
        Optional<BooksModel> autorFinded = repositorio.findByAutorAndNascimentoAndNationality(
                autor.getAutor(),
                autor.getNascimento(),
                autor.getNationality());


        if(autor.getId() == null) {
            return autorFinded.isPresent();
        }

        return !autor.getId().equals(autorFinded.get().getId()) && autorFinded.isPresent();
    }

}
