package com.next.infod.controller.mappers;

import com.next.infod.controller.DTOS.CadastroLivroDTO;
import com.next.infod.controller.DTOS.ResultadoPesquisaLivroDTO;
import com.next.infod.model.Livro;
import com.next.infod.repositories.BooksRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring", uses = BooksMapper.class)
public abstract class LivroMapper {
    @Autowired
    BooksRepository booksepository;

    @Mapping(target = "books", expression = "java( booksepository.findById(dto.idAutor()).orElse(null) )")
    public abstract Livro toEntity(CadastroLivroDTO dto);

    public abstract ResultadoPesquisaLivroDTO toDTO(Livro livro);

}
