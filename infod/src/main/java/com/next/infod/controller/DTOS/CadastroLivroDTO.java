package com.next.infod.controller.DTOS;

import com.next.infod.Enums.GeneroLivro;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.ISBN;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record CadastroLivroDTO(
        @ISBN
        @NotBlank(message = "campo obrigatorio")
        String isbn,
        @NotBlank(message = "campo obrigatorio")
        String titulo,


        @Past(message = "nao pode ser uma data futura")
        LocalDate dataPublicacao,

        @NotNull(message = "campo obrigatorio")
        GeneroLivro genero,

        @NotNull(message = "campo obrigatorio")  // Garantindo que o preço seja obrigatório
        @DecimalMin(value = "0.01", message = "O preço deve ser maior que zero")  // Exemplo de validação para preço
        BigDecimal preco,
        @NotNull(message = "campo obrigatorio")
        UUID idAutor
) {
}
