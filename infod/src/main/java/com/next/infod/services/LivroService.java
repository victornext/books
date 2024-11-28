package com.next.infod.services;


import com.next.infod.controller.DTOS.CadastroLivroDTO;
import com.next.infod.Enums.GeneroLivro;
import com.next.infod.controller.LivroController;
import com.next.infod.exceptions.Illegal;
import com.next.infod.exceptions.LivroNaoEncontrado;
import com.next.infod.model.Livro;
import com.next.infod.repositories.LivroRepository;
import com.next.infod.repositories.specs.LivroSpecs;
import com.next.infod.validator.LivroValidator;
import lombok.AllArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.rsocket.RSocketProperties;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class LivroService {
    @Autowired
    private final LivroRepository repositorio;
    @Autowired
    private final LivroValidator validator;

    public ResponseEntity<Livro> create(Livro cadastro) {
        //Persistindo no repositório
        Livro savedLivro = repositorio.save(cadastro);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedLivro);
    }



    public ResponseEntity<List<Livro>> findAll() {
        return ResponseEntity.status(HttpStatus.OK).body(repositorio.findAll());
    }


    public ResponseEntity<Livro> findById(UUID id) {
        Optional<Livro> livro0 = repositorio.findById(id);
        if(livro0.isEmpty()) {
            throw new LivroNaoEncontrado("Livro não localizado!    ID: " + id);

        }
        return ResponseEntity.status(HttpStatus.OK).body(livro0.get());
    }


    public ResponseEntity<Object> update(Livro livro) {
        if (livro.getId() == null) {
            throw new Illegal("Para atualizar o livro tem que estar na base!");
        }

        repositorio.save(livro);
        return ResponseEntity.ok().build();
    }


    public void Delete(Livro livro) {
        repositorio.delete(livro);
    }


    public Optional<Livro> obterPorId(UUID id){
        return repositorio.findById(id);
    }

    public List<Livro> pesquisa(
            String isbn,
            String titulo,
            String autor,
            GeneroLivro genero,
            Integer anoPublicacao){

//        Specification<Livro> specs = Specification
//                .where(LivroSpecs.isbnEqual(isbn))
//                .and(LivroSpecs.tituloLike(titulo))
//                .and(LivroSpecs.generoEqual(genero))
//                ;


        Specification<Livro> specs = Specification.where((root, query, cb) -> cb.conjunction());


        if(isbn != null ){
            specs = specs.and(LivroSpecs.isbnEqual(isbn));
        }

        if(titulo != null) {
            specs = specs.and(LivroSpecs.tituloLike(titulo));
        }

        if(genero != null) {
            specs = specs.and(LivroSpecs.generoEqual(genero));
        }

        if(anoPublicacao != null) {
            specs = specs.and(LivroSpecs.AnoPublicacaoEqual(anoPublicacao));
        }

        if(autor != null) {
            specs = specs.and(LivroSpecs.NomeAutorLike(autor));
        }

        Specification<Livro> isbnEqual = (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("isbn"), isbn);



        return repositorio.findAll(specs);
    }
}
