package br.com.tiu.forum.domain.topico;

import br.com.tiu.forum.domain.resposta.Resposta;
import br.com.tiu.forum.domain.usuario.Usuario;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "topicos")
public class Topico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    @Column(length = 2000)
    private String mensagem;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "autor_id")
    private Usuario autor;
    private LocalDateTime dataCriacao;
    @Enumerated(EnumType.STRING)
    private Categoria categoria;
    @OneToMany(mappedBy = "topico", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Resposta> respostas;

    public Topico() {}

    public Topico(DadosCadastroTopico dados, Usuario autor) {
        this.titulo = dados.titulo();
        this.mensagem = dados.mensagem();
        this.categoria = dados.categoria();
        this.autor = autor;
        this.dataCriacao = LocalDateTime.now();
    }

    // Getters e setters

    public Long getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getMensagem() {
        return mensagem;
    }

    public Usuario getAutor() {
        return autor;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public void setAutor(Usuario autor) {
        this.autor = autor;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public List<Resposta> getRespostas() {
        return respostas;
    }
}
