package com.next.infod.services;

import com.next.infod.controller.DTOS.BooksDTO;
import com.next.infod.exceptions.ArquivoDuplicado;
import com.next.infod.exceptions.LivroNaoEncontrado;
import com.next.infod.model.BooksModel;
import com.next.infod.model.Usuario;
import com.next.infod.repositories.BooksRepository;
import com.next.infod.security.SecurityService;
import com.next.infod.validator.AutorValidator;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;
import java.util.UUID;



@Service
@RequiredArgsConstructor
public class BooksService {

    private  final BooksRepository repositorio;
    private final SecurityService securityService;
    private  final AutorValidator validator;


    public ResponseEntity<BooksModel> Create(BooksModel books) {
        validator.validar(books);
        Usuario usuario = securityService.obterUsuarioLogado();
        books.setUsuario(usuario);
        BooksModel savedBook = repositorio.save(books);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedBook);
    }




    public ResponseEntity<Object> update(UUID id, BooksModel books) {
        Optional<BooksModel> books0 = repositorio.findById(id);

        if(books0.isEmpty()) {
            throw new LivroNaoEncontrado("Não encontrado");
        }

        var booksModel = books0.get();
        BeanUtils.copyProperties(books, booksModel);
        validator.validar(booksModel);
        return ResponseEntity.status(HttpStatus.OK).body(repositorio.save(booksModel));
    }



    public ResponseEntity<List<BooksModel>> FindAll() {
        return ResponseEntity.status(HttpStatus.OK).body(repositorio.findAll());
    }





    public ResponseEntity<Object> Delete(UUID id) {
        Optional<BooksModel> books0 = repositorio.findById(id);
        if(books0.isEmpty()) {
            throw new LivroNaoEncontrado("Não encontrado");
        }
        repositorio.delete(books0.get());
        return ResponseEntity.status(HttpStatus.OK).body("Sucesso!!");

    }





    public ResponseEntity<Object> findById(UUID id) {
        Optional<BooksModel> books0 = repositorio.findById(id);
        if(books0.isEmpty()){
            System.out.println("Autor não encontrado");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("ERRO");
        }

        return ResponseEntity.status(HttpStatus.OK).body(books0.get());

    }





    public Optional<BooksModel> obterPorId(UUID id){
        return repositorio.findById(id);
    }





    public List<BooksModel> PesquisaByExample(String autor, String nationality){
        var booksModel = new BooksModel();
        booksModel.setAutor(autor);
        booksModel.setNationality(nationality);

        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreNullValues()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example<BooksModel> autorExample= Example.of(booksModel, matcher);
           return repositorio.findAll(autorExample);
    }

}

