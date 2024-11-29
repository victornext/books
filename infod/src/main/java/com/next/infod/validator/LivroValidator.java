package com.next.infod.validator;

import com.next.infod.exceptions.ArquivoDuplicado;
import com.next.infod.exceptions.Illegal;
import com.next.infod.model.Livro;
import com.next.infod.repositories.LivroRepository;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.apache.catalina.LifecycleState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class LivroValidator {

    private final LivroRepository repositorio;

    public void validar(Livro livro) {
        verificarDuplicidade(livro);  // Verifica duplicidade completa
        exiteLivroIsbn(livro);  // Verifica igualdade de título
    }

    // Verifica se existe um livro com os mesmos dados (exceto ID)
    private void verificarDuplicidade(Livro livro) {
            if(exiteLivroIsbn(livro)) {
                throw new ArquivoDuplicado("Isbn ja cadastrado existente!");
            }


    }


    private boolean exiteLivroIsbn(Livro livro) {
        Optional<Livro> LivroEncontrado = repositorio.findByIsbn(livro.getIsbn());
        if(livro.getId() == null) {
            return LivroEncontrado.isPresent();
        }

        return LivroEncontrado.map(livro::getId).stream().anyMatch(id -> !id.equals(livro.getId()));
    }
}
