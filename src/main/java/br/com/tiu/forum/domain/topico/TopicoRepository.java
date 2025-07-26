package br.com.tiu.forum.domain.topico;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TopicoRepository extends JpaRepository<Topico, Long> {

    @Query("""
    select new br.com.tiu.forum.domain.topico.DadosListagemTopico(
        t.id, t.titulo, t.mensagem, t.autor.nomeUsuario, t.dataCriacao, t.categoria
    )
    from Topico t
    join t.autor a
""")
    Page<DadosListagemTopico> listar(Pageable pageable);

}
