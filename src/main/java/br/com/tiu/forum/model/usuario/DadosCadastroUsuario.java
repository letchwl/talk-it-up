package br.com.tiu.forum.model.usuario;

import jakarta.validation.constraints.NotBlank;

public record DadosCadastroUsuario(
        @NotBlank String email,
        @NotBlank String senha,
        @NotBlank String nomeUsuario,
        @NotBlank String displayName,
        String biografia
) {
}