package com.next.infod.controller.mappers;

import com.next.infod.controller.DTOS.BooksDTO;
import com.next.infod.model.BooksModel;
import java.time.LocalDate;
import java.util.UUID;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-12-18T15:16:28-0300",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.5 (Azul Systems, Inc.)"
)
@Component
public class BooksMapperImpl implements BooksMapper {

    @Override
    public BooksModel toEntity(BooksDTO dto) {
        if ( dto == null ) {
            return null;
        }

        BooksModel booksModel = new BooksModel();

        booksModel.setId( dto.id() );
        booksModel.setAutor( dto.autor() );
        booksModel.setNascimento( dto.nascimento() );
        booksModel.setNationality( dto.nationality() );

        return booksModel;
    }

    @Override
    public BooksDTO toDTO(BooksModel books) {
        if ( books == null ) {
            return null;
        }

        UUID id = null;
        String autor = null;
        LocalDate nascimento = null;
        String nationality = null;

        id = books.getId();
        autor = books.getAutor();
        nascimento = books.getNascimento();
        nationality = books.getNationality();

        BooksDTO booksDTO = new BooksDTO( id, autor, nascimento, nationality );

        return booksDTO;
    }
}
