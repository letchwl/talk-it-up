package br.com.tiu.forum.domain.resposta;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record DadosAtualizacaoResposta(
        @Size(max = 2000, message = "A mensagem deve ter no máximo 2000 caracteres")
        @NotBlank(message = "A mensagem não pode estar em branco")
        String mensagem
) {
}
