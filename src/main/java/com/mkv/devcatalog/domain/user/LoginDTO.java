package com.mkv.devcatalog.domain.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginDTO(
        @NotBlank(message = "Insira o email")
        @Email(message = "Insira um email válido")
        String email,

        @NotBlank(message = "Insira a senha!")
        String password) {
}
