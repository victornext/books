package com.next.infod.repositories.specs;

import com.next.infod.Enums.GeneroLivro;
import com.next.infod.model.Livro;
import org.springframework.data.jpa.domain.Specification;

public class LivroSpecs {
    public static Specification<Livro> isbnEqual(String isbn){
        return ((root, query, cb) -> cb.equal(root.get("isbn"), isbn));
    }


    public static Specification<Livro> tituloLike(String titulo){
        // upper(livro.titulo) like (%:param%)
        return (root, query, cb) ->
                cb.like( cb.upper(root.get("titulo")), "%" + titulo.toUpperCase() + "%");
    }


    public static Specification<Livro> generoEqual(GeneroLivro genero){
        return (root, query, cb) -> cb.equal(root.get("genero"), genero);
    }


    public static Specification<Livro> AnoPublicacaoEqual(Integer anoPublicacao){
        return (root, query, cb) ->
                cb.equal(cb.function("to_char", String.class, root.get("dataPublicacao"), cb.literal("YYYY")), anoPublicacao.toString());
    }


    public static Specification<Livro> NomeAutorLike(String autor){
        return (root, query, cb) -> {
            return cb.like(cb.upper(root.get("books").get("autor")), "%" + autor.toUpperCase() + "%");

        };
    }

}