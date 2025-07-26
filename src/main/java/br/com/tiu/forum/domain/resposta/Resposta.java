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

    public Resposta() {}

    public Resposta(Topico topico, Usuario autor, String mensagem, LocalDateTime dataCriacao) {
        this.topico = topico;
        this.autor = autor;
        this.mensagem = mensagem;
        this.dataCriacao = dataCriacao;
    }

    public Long getId() {
        return id;
    }

    public String getMensagem() {
        return mensagem;
    }

    public Usuario getAutor() {
        return autor;
    }

    public Topico getTopico() {
        return topico;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTopico(Topico topico) {
        this.topico = topico;
    }

    public void setAutor(Usuario autor) {
        this.autor = autor;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }
}
