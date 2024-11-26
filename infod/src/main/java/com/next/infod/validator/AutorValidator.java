package com.next.infod.validator;

import com.next.infod.exceptions.ArquivoDuplicado;
import com.next.infod.exceptions.Illegal;
import com.next.infod.model.BooksModel;
import com.next.infod.repositories.BooksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AutorValidator {
    @Autowired
    private BooksRepository repositorio;

    public void validar(BooksModel autor) {
        dadosIguais(autor); // Verifica se o autor é igual ao autor existente
        if (existeAutor(autor)) {
            throw new ArquivoDuplicado("Autor Já Cadastrado!");
        }
    }

    private void dadosIguais(BooksModel autor) {
        // Verifica se o nome do autor já está registrado no banco
        Optional<BooksModel> autorFinded = repositorio.findByAutorAndNascimentoAndNationality(
                autor.getAutor(),
                autor.getNascimento(),
                autor.getNationality()
        );

        if (autorFinded.isPresent()) {
            BooksModel existingAutor = autorFinded.get();

            // Se o autor enviado for igual ao já registrado, gera a exceção
            if (autor.getAutor().equals(existingAutor.getAutor())) {
                throw new Illegal("O autor não pode ser igual ao autor já existente!");
            }
        }
    }


    private boolean existeAutor(BooksModel autor) {
        Optional<BooksModel> autorFinded = repositorio.findByAutorAndNascimentoAndNationality(
                autor.getAutor(),
                autor.getNascimento(),
                autor.getNationality()
        );

        if (autor.getId() == null) {
            return autorFinded.isPresent();
        }

        return autorFinded.isPresent() && !autor.getId().equals(autorFinded.get().getId());
    }
}
