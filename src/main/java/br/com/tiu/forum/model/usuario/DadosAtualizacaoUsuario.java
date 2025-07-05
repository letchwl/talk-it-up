package br.com.tiu.forum.model.usuario;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record DadosAtualizacaoUsuario(
        @Size(min = 3, max = 20, message = "Nome de usuário deve ter entre 3 e 20 caracteres")
        @NotBlank String nomeUsuario,
        @Size(min = 3, max = 30, message = "Display name deve ter entre 3 e 30 caracteres")
        @NotBlank String displayName,
        @Size(max = 160, message = "Biografia deve ter no máximo 160 caracteres")
        @NotBlank String biografia
) {
}
