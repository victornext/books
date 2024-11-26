package com.next.infod.controller.DTOS;

import com.next.infod.Enums.GeneroLivro;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record ResultadoPesquisaLivro(
        UUID id,
        String isbn,
        String titulo,
        LocalDate DataPublicacao,
        GeneroLivro genero,
        BigDecimal preco,
        BooksDTO autor
) {
}
