package com.next.infod.controller;

import com.next.infod.controller.DTOS.CadastroLivroDTO;
import com.next.infod.controller.DTOS.ResultadoPesquisaLivroDTO;
import com.next.infod.controller.mappers.LivroMapper;
import com.next.infod.model.Livro;
import com.next.infod.services.LivroService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("livros")
@RequiredArgsConstructor
@Validated
public class LivroController implements GenericController {

    private final LivroService service;
    private final LivroMapper mapper;

    @PostMapping("/create")
    public ResponseEntity<Livro> create(@RequestBody @Valid CadastroLivroDTO dto) {
        Livro livro = mapper.toEntity(dto);
        service.create(livro);

        URI url = gerarHeaderLocation(livro.getId());

        return ResponseEntity.created(url).build();
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


    @GetMapping("{id}")
    public ResponseEntity<ResultadoPesquisaLivroDTO> obterDetalhes(@PathVariable("id") String id) {
            return service.obterPorId(UUID.fromString(id))
                    .map(livro -> {
                        var dto = mapper.toDTO(livro);
                        return ResponseEntity.ok(dto);
                    }).orElseGet( () -> ResponseEntity.notFound().build());
    }
}
