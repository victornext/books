package com.next.infod.services;

import com.next.infod.DTOS.BooksDTO;
import com.next.infod.model.BooksModel;
import com.next.infod.repositories.BooksRepository;
import org.apache.coyote.Response;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class BooksService {
    @Autowired
    BooksRepository repositorio;


    public ResponseEntity<BooksModel> Create(BooksDTO books) {
        var booksmodel = new BooksModel();
        BeanUtils.copyProperties(books, booksmodel);
        return ResponseEntity.status(HttpStatus.CREATED).body(repositorio.save(booksmodel));
    }

    

}
