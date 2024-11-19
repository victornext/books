package com.next.infod.services;

import com.next.infod.DTOS.BooksDTO;
import com.next.infod.model.BooksModel;
import com.next.infod.repositories.BooksRepository;
import com.next.infod.validator.AutorValidator;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
public class BooksService {
    @Autowired
    private  final BooksRepository repositorio;
    @Autowired
    private  final AutorValidator validator;

    public BooksService(BooksRepository repositorio, AutorValidator validator) {
        this.repositorio = repositorio;
        this.validator = validator;
    }


    public ResponseEntity<BooksModel> Create(BooksDTO books) {
        var booksmodel = new BooksModel();
        BeanUtils.copyProperties(books, booksmodel);
        validator.validar(booksmodel);
        BooksModel savedBook = repositorio.save(booksmodel);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedBook);
    }






    public ResponseEntity<List<BooksModel>> FindAll() {
        return ResponseEntity.status(HttpStatus.OK).body(repositorio.findAll());
    }





    public ResponseEntity<Object> Delete(UUID id) {
        Optional<BooksModel> books0 = repositorio.findById(id);
        if(books0.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Erro");
        }
        repositorio.delete(books0.get());
        return ResponseEntity.status(HttpStatus.OK).body("Sucesso!!");

    }





    public ResponseEntity<Object> findById(UUID id) {
        Optional<BooksModel> books0 = repositorio.findById(id);
        if(books0.isEmpty()){
            System.out.println("Livro não encontrado");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("ERRO");
        }

        return ResponseEntity.status(HttpStatus.OK).body(books0.get());

    }





    public ResponseEntity<Object> update(UUID id, BooksDTO books) {
        Optional<BooksModel> books0 = repositorio.findById(id);

        if(books0.isEmpty()){
           return ResponseEntity.status(HttpStatus.NOT_FOUND).body("ERRO, Não encontrado");
        }

        var booksModel = books0.get();
        BeanUtils.copyProperties(books, booksModel);
        return ResponseEntity.status(HttpStatus.OK).body(repositorio.save(booksModel));
    }




    public List<BooksModel> pesquisa (String autor, String nationality) {
        if(autor != null && nationality != null) {
            return repositorio.findByAutorAndNationality(autor, nationality);
        }

        if(autor != null ) {
            return repositorio.findByAutor(autor);
        }
        if(nationality != null ) {
            return repositorio.findByNationality(nationality);
        }

        return repositorio.findAll();
    }
}

