package com.next.infod.controller;



import com.next.infod.DTOS.AutorDTO;
import com.next.infod.DTOS.BooksDTO;
import com.next.infod.model.BooksModel;
import com.next.infod.services.BooksService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
public class BooksController {




    @Autowired
    BooksService services;


    @PostMapping(value = "/create" )
    public ResponseEntity<BooksModel> create(@RequestBody @Valid BooksDTO DTO){
        return services.Create(DTO);
    }


    @GetMapping(value = "/findAll")
    public ResponseEntity<List<BooksModel>> findAll(){
        return services.FindAll();
    }


    @DeleteMapping(value = "/delete/{id}")
    ResponseEntity<Object> Delete(@PathVariable(value = "id") UUID id){
        return services.Delete(id);
    }


    @GetMapping(value =  "get/{id}")
    public ResponseEntity<Object> findById(@PathVariable(value = "id") UUID id) {
        return services.findById(id);
    }

    @PutMapping(value = "put/{id}")
    public ResponseEntity<Object> update(@PathVariable(value = "id") UUID id, @RequestBody @Valid BooksDTO books) {
        return services.update(id, books);
    }

    @GetMapping(value = "/pesquisa")
    public ResponseEntity<List<BooksDTO>> pesquisar(
            @RequestParam(value ="autor", required = false) String autor,
            @RequestParam(value = "nationality", required = false) String nationality) {
        List<BooksModel> resultado = services.pesquisa(autor, nationality);
        List<BooksDTO> lista = resultado.stream()
                .map(book -> new BooksDTO(
                        book.getAutor(),
                        book.getNascimento(),
                        book.getNationality())) // Mapeia para o DTO
                .collect(Collectors.toList());
        return ResponseEntity.ok(lista);
    }

}
