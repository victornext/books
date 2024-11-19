package com.next.infod.controller;



import com.next.infod.DTOS.BooksDTO;
import com.next.infod.model.BooksModel;
import com.next.infod.repositories.BooksRepository;
import com.next.infod.services.BooksService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BooksController {




    @Autowired
    BooksService services;


    @PostMapping(value = "/autores" )
    public ResponseEntity<BooksModel> create(@RequestBody @Valid BooksDTO DTO){
        return services.Create(DTO);
    }




}
