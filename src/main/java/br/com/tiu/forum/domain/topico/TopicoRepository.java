package br.com.tiu.forum.domain.topico;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface TopicoRepository extends JpaRepository<Topico, Long> {

    @Query("""
    select new br.com.tiu.forum.domain.topico.DadosListagemTopico(
        t.id, t.titulo, t.mensagem, t.autor.nomeUsuario, t.dataCriacao, t.categoria
    )
    from Topico t
    join t.autor a
""")
    Page<DadosListagemTopico> listar(Pageable pageable);
    @EntityGraph(attributePaths = {"respostas", "autor", "categoria", "respostas.autor"})
    Optional<Topico> findById(Long id);

    @EntityGraph(attributePaths = {"respostas"})
    @Query("SELECT t FROM Topico t WHERE t.id = :id")
    Optional<Topico> buscarPorIdComRespostas(@Param("id") Long id);

}
