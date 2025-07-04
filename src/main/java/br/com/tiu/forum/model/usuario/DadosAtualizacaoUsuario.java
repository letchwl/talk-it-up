package br.com.tiu.forum.model.usuario;

import jakarta.validation.constraints.NotBlank;

public record DadosAtualizacaoUsuario(

        @NotBlank String nomeUsuario,
        @NotBlank String displayName,
        @NotBlank String biografia

) {
}
