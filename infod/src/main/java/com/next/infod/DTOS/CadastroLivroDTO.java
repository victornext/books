package com.next.infod.DTOS;

import com.next.infod.Enums.GeneroLivro;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record CadastroLivroDTO(
        @NotBlank(message = "O ISBN é obrigatório e não pode estar vazio.")
        String isbn,

        @NotBlank(message = "O título é obrigatório e não pode estar vazio.")
        @Size(min = 5, max = 160, message = "O título deve ter entre 5 e 160 caracteres.")
        String titulo,

        @NotNull(message = "A data de publicação é obrigatória.")
        @PastOrPresent(message = "A data de publicação não pode estar no futuro.")
        LocalDate dataPublicacao,

        @NotNull(message = "O gênero é obrigatório.")
        GeneroLivro genero,

        @NotNull(message = "O preço é obrigatório.")
        @Positive(message = "O preço deve ser maior que zero.")
        BigDecimal preco,

        @NotNull(message = "O ID do autor é obrigatório.")
        UUID idAutor
) {
}

