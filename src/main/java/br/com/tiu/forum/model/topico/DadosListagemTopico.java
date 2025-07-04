package br.com.tiu.forum.model.topico;

import java.time.LocalDateTime;

public record DadosListagemTopico(
        Long id,
        String titulo,
        String mensagem,
        String nomeAutor,
        LocalDateTime dataCriacao,
        Categoria categoria
) {
    public DadosListagemTopico(Topico topico) {
        this(topico.getId(), topico.getTitulo(), topico.getMensagem(),
                topico.getAutor().getNome(), topico.getDataCriacao(), topico.getCategoria());
    }
}

