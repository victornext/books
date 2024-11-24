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
        validarNulos(livro); // Validação contra campos nulos
        dadosIguais(livro);  // Verifica se já existe livro com dados iguais
        if (existeLivro(livro)) {
            throw new ArquivoDuplicado("Livro já cadastrado!");
        }
    }

    // Valida se os campos são nulos
    public void validarNulos(Livro livro) {
        if (livro == null) {
            throw new Illegal("O objeto livro não pode ser nulo");
        }

        if (livro.getIsbn() == null || livro.getIsbn().isEmpty()) {
            throw new Illegal("Isbn não pode ser nulo ou vazio");
        }
        if (livro.getTitulo() == null || livro.getTitulo().isEmpty()) {
            throw new Illegal("Título não pode ser nulo ou vazio");
        }
        if (livro.getGenero() == null) {
            throw new Illegal("Gênero não pode ser nulo");
        }
        if (livro.getPreco() == null) {
            throw new Illegal("Preço não pode ser nulo");
        }
    }

    // Valida se o livro já existe com os mesmos dados
    public void dadosIguais(Livro livro) {
        Optional<Livro> livroFinded = repositorio.findByIsbnAndTituloAndGeneroAndPreco(
                livro.getIsbn(),
                livro.getTitulo(),
                livro.getGenero(),
                livro.getPreco()
        );

        if (livroFinded.isPresent()) {
            Livro existingLivro = livroFinded.get();
            if (livro.getTitulo().equals(existingLivro.getTitulo())) {
                throw new Illegal("Título não pode ser igual a um já existente");
            }
        }
    }

    // Verifica se o livro já existe no banco
    public boolean existeLivro(Livro livro) {
        // Busca registros com os mesmos atributos, exceto o ID
        Optional<Livro> livroFinded = repositorio.findByIsbnAndTituloAndGeneroAndPreco(
                livro.getIsbn(),
                livro.getTitulo(),
                livro.getGenero(),
                livro.getPreco()
        );

        // Caso o livro seja novo (id é null)
        if (livro.getId() == null) {
            return livroFinded.isPresent(); // Retorna verdadeiro se já existe um livro com os mesmos dados
        }

        // Caso o livro já exista (id não é null), verifica se o registro encontrado não tem o mesmo ID
        return livroFinded.isPresent() && !livro.getId().equals(livroFinded.get().getId());
    }

}
