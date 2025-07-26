package br.com.tiu.forum.domain.resposta;

import br.com.tiu.forum.domain.topico.Topico;
import br.com.tiu.forum.domain.usuario.Usuario;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "respostas")
public class Resposta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "topico_id")
    private Topico topico;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    private Usuario autor;
    private String mensagem;
    private LocalDateTime dataCriacao;

    public Long getId() {
        return id;
    }

    public String getContent() {
        return mensagem;
    }

    public Usuario getAuthor() {
        return autor;
    }

    public Topico getTopico() {
        return topico;
    }

    public LocalDateTime getDate() {
        return dataCriacao;
    }
}
