package com.next.infod.controller.DTOS;

import com.next.infod.Enums.GeneroLivro;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;


public record ResultadoPesquisaLivroDTO(
        UUID id,
        String isbn,
        String titulo,
        LocalDate dataPublicacao,
        GeneroLivro genero,
        BigDecimal preco,
        BooksDTO books  // A relação agora aponta corretamente para um BooksDTO
) {
}
