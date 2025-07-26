package br.com.tiu.forum.domain.resposta;

import br.com.tiu.forum.domain.usuario.Usuario;

import java.time.LocalDateTime;

public record DadosListagemResposta(
        Long id,
        String mensagem,
        Usuario autor,
        LocalDateTime dataCriacao
) {
    public DadosListagemResposta(Resposta resposta) {
        this(resposta.getId(), resposta.getMensagem(),
                resposta.getAutor(), resposta.getDataCriacao());
    }
}
