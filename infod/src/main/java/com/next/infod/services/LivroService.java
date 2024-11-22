package com.next.infod.services;


import com.next.infod.DTOS.CadastroLivroDTO;
import com.next.infod.Enums.GeneroLivro;
import com.next.infod.controller.LivroController;
import com.next.infod.exceptions.LivroNaoEncontrado;
import com.next.infod.model.Livro;
import com.next.infod.repositories.LivroRepository;
import lombok.AllArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class LivroService {
    @Autowired
    private final LivroRepository repositorio;


    public ResponseEntity<Livro> create (CadastroLivroDTO cadastro){
        var livro = new Livro();
        BeanUtils.copyProperties(cadastro, livro);
        return ResponseEntity.status(HttpStatus.CREATED).body(repositorio.save(livro));

    }


    public ResponseEntity<List<Livro>> findAll() {
        return ResponseEntity.status(HttpStatus.OK).body(repositorio.findAll());
    }


    public ResponseEntity<Livro> findById(UUID id) {
        Optional<Livro> livro0 = repositorio.findById(id);
        if(livro0.isEmpty()) {
            throw new LivroNaoEncontrado("Livro não localizado!    ID: " + id);

        }
        return ResponseEntity.status(HttpStatus.OK).body(livro0.get());
    }


    public ResponseEntity<Object> update(UUID id, CadastroLivroDTO DTO) {
        Optional<Livro> livro0 = repositorio.findById(id);
        if(livro0.isEmpty()){
            throw new LivroNaoEncontrado("Livro não localizado!    ID: "+ id);
        }

        var LivroModel=livro0.get();
        BeanUtils.copyProperties(DTO, LivroModel);

        return ResponseEntity.status(HttpStatus.OK).body(repositorio.save(LivroModel));
    }


    public ResponseEntity<Object> Delete(UUID id) {
        Optional<Livro> livro0 = repositorio.findById(id);

        if(livro0.isEmpty()){
            throw new LivroNaoEncontrado("Livro não localizado!    ID: "+ id);
        }

        repositorio.delete(livro0.get());
        return ResponseEntity.status(HttpStatus.OK).body("Objeto deletado com sucesso!!");
    }
}
