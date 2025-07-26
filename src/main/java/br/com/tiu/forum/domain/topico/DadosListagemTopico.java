package br.com.tiu.forum.domain.topico;

import br.com.tiu.forum.domain.resposta.DadosListagemResposta;
import br.com.tiu.forum.domain.resposta.Resposta;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public record DadosListagemTopico(
        Long id,
        String titulo,
        String mensagem,
        String nomeAutor,
        LocalDateTime dataCriacao,
        Categoria categoria,
        List<DadosListagemResposta> respostas
) {
    public DadosListagemTopico(Topico topico) {
        this(
                topico.getId(),
                topico.getTitulo(),
                topico.getMensagem(),
                topico.getAutor().getNomeUsuario(),
                topico.getDataCriacao(),
                topico.getCategoria(),
                topico.getRespostas()
                        .stream()
                        .map((Resposta id1) -> new DadosListagemResposta(id1))
                        .collect(Collectors.toList())
        );
    }

    public DadosListagemTopico(Long id, String titulo, String mensagem, String nomeAutor, LocalDateTime dataCriacao, Categoria categoria) {
        this(id, titulo, mensagem, nomeAutor, dataCriacao, categoria, List.of());
    }

}