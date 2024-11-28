package com.next.infod.controller;

import com.next.infod.Enums.GeneroLivro;
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
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

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
    public ResponseEntity<Object> delete(@PathVariable(value = "id") String id){
        return service.obterPorId((UUID.fromString(id)))
                .map(livro -> {
                    service.Delete(livro);
                    return ResponseEntity.noContent().build();

                }).orElseGet(() -> ResponseEntity.notFound().build());
    }


    @GetMapping("{id}")
    public ResponseEntity<ResultadoPesquisaLivroDTO> obterDetalhes(@PathVariable("id") String id) {
            return service.obterPorId(UUID.fromString(id))
                    .map(livro -> {
                        var dto = mapper.toDTO(livro);
                        return ResponseEntity.ok(dto);
                    }).orElseGet( () -> ResponseEntity.notFound().build());
    }


    @GetMapping
    public ResponseEntity<List<ResultadoPesquisaLivroDTO>> pesquisa(
            @RequestParam(value = "isbn", required = false)
            String isbn,
            @RequestParam(value = "titulo", required = false)
            String titulo,
            @RequestParam(value = "autor", required = false)
            String autor,
            @RequestParam(value = "genero", required = false)
            GeneroLivro genero,
            @RequestParam(value = "dataPublicacao", required = false)
            Integer anoPublicacao
    ) {
        var resultado = service.pesquisa(isbn, titulo, autor, genero, anoPublicacao);
        var lista = resultado.
                stream().
                map(mapper::toDTO)
                .collect(Collectors
                        .toList());

        return ResponseEntity.ok(lista);
    }
}
