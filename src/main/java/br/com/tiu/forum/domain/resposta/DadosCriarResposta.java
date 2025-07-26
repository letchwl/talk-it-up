package br.com.tiu.forum.domain.resposta;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DadosCriarResposta(
        @NotBlank String mensagem,
        @NotNull Long topicoId
) {
}
