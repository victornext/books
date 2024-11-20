package com.next.infod.DTOS;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record BooksDTO(
        @NotBlank(message = "Autor não pode estar vazio") String autor,

        @NotNull(message = "Data de nascimento não pode ser nula") LocalDate nascimento,

        @NotBlank(message = "Nacionalidade não pode estar vazia") String nationality
) {
}
