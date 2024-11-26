package com.next.infod.validator;

import com.next.infod.exceptions.ArquivoDuplicado;
import com.next.infod.exceptions.Illegal;
import com.next.infod.model.Livro;
import com.next.infod.repositories.LivroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class LivroValidator {

    private final LivroRepository repositorio;

    @Autowired
    public LivroValidator(LivroRepository repositorio) {
        this.repositorio = repositorio;
    }

    public void validar(Livro livro) {
        verificarDuplicidade(livro);  // Verifica duplicidade completa
        verificarIgualdadeDeTitulo(livro);  // Verifica igualdade de título
    }

    // Verifica se existe um livro com os mesmos dados (exceto ID)
    private void verificarDuplicidade(Livro livro) {
        Optional<Livro> livroExistente = repositorio.findByIsbnAndTituloAndGeneroAndPreco(
                livro.getIsbn(),
                livro.getTitulo(),
                livro.getGenero(),
                livro.getPreco()
        );

        if (livroExistente.isPresent()) {
            // Caso o livro seja novo (id é null) ou diferente pelo ID
            if (livro.getId() == null || !livro.getId().equals(livroExistente.get().getId())) {
                throw new ArquivoDuplicado("Livro já cadastrado com os mesmos dados!");
            }
        }
    }

    // Verifica se o título é igual ao de outro livro já existente
    private void verificarIgualdadeDeTitulo(Livro livro) {
        Optional<Livro> livroComMesmoTitulo = repositorio.findByTitulo(livro.getTitulo());

        if (livroComMesmoTitulo.isPresent()) {
            Livro existente = livroComMesmoTitulo.get();
            // Evita conflito de título com outro registro, exceto se for o mesmo ID
            if (livro.getId() == null || !livro.getId().equals(existente.getId())) {
                throw new Illegal("Título já existe em outro livro cadastrado!");
            }
        }
    }
}
