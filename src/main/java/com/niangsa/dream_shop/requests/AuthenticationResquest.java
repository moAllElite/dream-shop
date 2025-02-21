package com.niangsa.dream_shop.requests;

import jakarta.validation.constraints.NotNull;

public record AuthenticationResquest(
        @NotNull(message = "L'email est requis") String email,
        @NotNull(message = "Le mot de passe est requis") String password) {
}
