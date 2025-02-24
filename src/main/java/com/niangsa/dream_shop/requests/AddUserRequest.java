package com.niangsa.dream_shop.requests;

import jakarta.validation.constraints.NotNull;

public record AddUserRequest(
        @NotNull(message = "Le prénom est requis") String firstName,
        @NotNull(message = "Le nom est requis") String lastName,
        @NotNull(message = "L'email' est requis") String email,
        @NotNull(message = "Le mot de passe est requis") String password,
        @NotNull(message = "Le role est requis")  String role
) {
}
