package br.com.tiu.forum.model.topico;

import br.com.tiu.forum.model.usuario.Usuario;

import java.time.LocalDateTime;

public record DadosListagemTopico(
        Long id,
        String title,
        String content,
        Usuario author,
        LocalDateTime date,
        Categoria categoria
) {

    public DadosListagemTopico(Topico topico) {
        this(topico.getId(), topico.getTitulo(), topico.getMensagem(),
                topico.getAutor(), topico.getDataCriacao(), topico.getCategoria());
    }

}
