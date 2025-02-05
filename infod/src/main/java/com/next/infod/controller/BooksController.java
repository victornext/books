package com.next.infod.controller;



import com.next.infod.controller.DTOS.BooksDTO;
import com.next.infod.controller.DTOS.ErrorResponse;
import com.next.infod.controller.mappers.BooksMapper;
import com.next.infod.exceptions.ArquivoDuplicado;
import com.next.infod.model.BooksModel;
import com.next.infod.model.Usuario;
import com.next.infod.security.SecurityService;
import com.next.infod.services.BooksService;
import com.next.infod.services.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.Authenticator;
import java.net.URI;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("autores")
@RequiredArgsConstructor
@Tag(name = "autores")
public class BooksController implements GenericController {

    private final BooksMapper mapper;
    private final BooksService services;


    @PostMapping(value = "/create" )
    @PreAuthorize("hasRole('GERENTE')")
    @Operation(summary = "Salvar", description = "Cadastrar novo autor")
    @ApiResponses({

            @ApiResponse(responseCode = "201", description = "Cadastrado com sucesso"),
            @ApiResponse(responseCode = "422", description = "Erro de validação"),
            @ApiResponse(responseCode = "409", description = "Conflito, quando o autor ja está cadastrado")
    })
    public ResponseEntity<?> create(@RequestBody @Valid BooksDTO dto){


        BooksModel books = mapper.toEntity(dto);
        services.Create(books);

        URI location = gerarHeaderLocation(books.getId());

        return ResponseEntity.created(location).build();
    }


    @PutMapping(value = "update/{id}")
    @Operation(summary = "Update", description = "Atualiza um autor existente")
    @ApiResponses({

            @ApiResponse(responseCode = "200", description = "Autor atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Autor não encontrado"),
            @ApiResponse(responseCode = "409", description = "Autor já cadastrado")
    })
    public ResponseEntity<?> update(@PathVariable(value = "id") UUID id, @RequestBody @Valid BooksDTO dto) {
        try {
            BooksModel books = mapper.toEntity(dto);
        return services.update(id, books); }
        catch(ArquivoDuplicado e) {
            var ErroDTO = ErrorResponse.conflito(e.getMessage());
            return ResponseEntity.status(ErroDTO.status()).body(ErroDTO);
        }
    }




    @GetMapping(value = "/findAll")
    @PreAuthorize("hasAnyRole('OPERADOR', 'GERENTE')")
    @Operation(summary = "FindAll", description = "Pesquisa por todos os autores existentes")
    @ApiResponses({

            @ApiResponse(responseCode = "200", description = "Autores encontrados com sucesso"),
    })
    public ResponseEntity<List<BooksModel>> findAll(){
        return services.FindAll();
    }




    @DeleteMapping(value = "/delete/{id}")
    @PreAuthorize("hasAnyRole('OPERADOR', 'GERENTE')")
    @Operation(summary = "Delete", description = "Delete por Id, um autor existente")
    @ApiResponses({

            @ApiResponse(responseCode = "204", description = "Autor deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Autor não encontrado"),
            @ApiResponse(responseCode = "400", description = "Autor tem livro registrado e não pode ser deletado")
    })
    ResponseEntity<Object> Delete(@PathVariable(value = "id") UUID id){
        return services.Delete(id);
    }






    @GetMapping(value =  "get/{id}")
    @PreAuthorize("hasAnyRole('OPERADOR', 'GERENTE')")
    @Operation(summary = "FindById", description = "procurando pelo id do autor")
    @ApiResponses({

            @ApiResponse(responseCode = "200", description = "Autor encontrado"),
            @ApiResponse(responseCode = "404", description = "Autor não encontrado")
    })
    public ResponseEntity<Object> findById(@PathVariable(value = "id") UUID id) {
        return services.findById(id);
    }






    @GetMapping(value = "/pesquisa")
    @PreAuthorize("hasAnyRole('OPERADOR', 'GERENTE')")
    @Operation(summary = "Pesquisar", description = "Realiza pesquisa de autores por parametros")
    @ApiResponses({

            @ApiResponse(responseCode = "200", description = "Autor encontrado"),
    })
    public ResponseEntity<List<BooksDTO>> pesquisar(
            @RequestParam(value ="autor", required = false) String autor,
            @RequestParam(value = "nationality", required = false) String nationality) {
        List<BooksModel> resultado = services.PesquisaByExample(autor, nationality);
        List<BooksDTO> lista = resultado.stream()
                .map(mapper::toDTO).collect(Collectors.toList());
        return ResponseEntity.ok(lista);
    }


}
