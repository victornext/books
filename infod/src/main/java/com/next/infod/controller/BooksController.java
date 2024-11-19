package com.next.infod.controller;



import com.next.infod.DTOS.BooksDTO;
import com.next.infod.model.BooksModel;
import com.next.infod.repositories.BooksRepository;
import com.next.infod.services.BooksService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.config.RepositoryNameSpaceHandler;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BooksController {




    @Autowired
    BooksService services;


    @PostMapping(value = "/autores" )
    public ResponseEntity<BooksModel> create(@RequestBody @Valid BooksDTO DTO){
        return services.Create(DTO);
    }


    @GetMapping(value = "/getAll")
    public ResponseEntity<List<BooksModel>> findAll(){
        return services.FindAll();
    }



}
