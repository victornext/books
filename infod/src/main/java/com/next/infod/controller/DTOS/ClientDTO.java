package com.next.infod.controller.DTOS;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record ClientDTO (

        UUID id,

        @NotNull
        String clientId,

        @NotNull
        String clientSecret,

        @NotNull
        String redirectURI,

        @NotNull
        @Size(max = 50, message = "Maximo 50")
        String scope

){
}
