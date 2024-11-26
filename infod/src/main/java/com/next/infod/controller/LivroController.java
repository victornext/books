package com.next.infod.controller;

import com.next.infod.controller.DTOS.CadastroLivroDTO;
import com.next.infod.controller.mappers.LivroMapper;
import com.next.infod.model.Livro;
import com.next.infod.services.LivroService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("livros")
@RequiredArgsConstructor
@Validated
public class LivroController {

    private final LivroService service;
    private final LivroMapper mapper;

    @PostMapping("/create")
    public ResponseEntity<Livro> create(@RequestBody @Valid CadastroLivroDTO dto) {
        Livro livro = mapper.toEntity(dto);
        return service.create(livro);
    }


    @GetMapping("/findall")
    public ResponseEntity<List<Livro>> findAll() {
        return service.findAll();
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<Livro> findById(@PathVariable(value = "id") UUID id) {
        return service.findById(id);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable(value = "id") UUID id, @RequestBody @Valid CadastroLivroDTO DTO){
        return service.update(id, DTO);
    }


    @DeleteMapping("delete/{id}")
    public ResponseEntity<?> delete(@PathVariable(value = "id") UUID id){
        return service.Delete(id);
    }
}
