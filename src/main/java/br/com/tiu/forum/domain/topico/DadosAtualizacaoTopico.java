package br.com.tiu.forum.domain.topico;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record DadosAtualizacaoTopico(
        @NotNull Long id,
        @Size(max = 100, message = "O título deve ter no máximo 100 caracteres")
        @NotBlank String titulo,
        @Size(max = 2000, message = "A mensagem deve ter no máximo 2000 caracteres")
        @NotBlank String mensagem
) {
}
