package com.next.infod.controller.mappers;


import com.next.infod.controller.DTOS.BooksDTO;
import com.next.infod.model.BooksModel;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BooksMapper {
    BooksModel toEntity(BooksDTO dto);

    BooksDTO toDTO(BooksModel books);
}
