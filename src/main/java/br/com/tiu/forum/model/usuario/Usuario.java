package br.com.tiu.forum.model.usuario;

import br.com.tiu.forum.infra.exception.RegraDeNegocioException;
import br.com.tiu.forum.model.topico.Topico;
import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "usuarios")
public class Usuario implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nomeUsuario;
    private String email;
    private String senha;
    private String token;
    private LocalDateTime expiracaoToken;
    private Boolean ativo;
    private Boolean verificado;
    private String displayName;
    private String biografia;
    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Topico> topicos = new ArrayList<>();

    public Usuario(){}

    public Usuario(DadosCadastroUsuario dados, String senhaCriptografada){
        this.email = dados.email();
        this.senha = senhaCriptografada;
        this.nomeUsuario = dados.nomeUsuario();
        this.displayName = dados.displayName();
        this.biografia = dados.biografia();
        this.token = UUID.randomUUID().toString();
        this.expiracaoToken = LocalDateTime.now().plusMinutes(30);
        this.ativo = false;
        this.verificado = false;
    }

    public Long getId() {
        return id;
    }

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public String getEmail() {
        return email;
    }

    public String getSenha() {
        return senha;
    }

    public String getDisplayName() {
        return displayName;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public boolean isEnabled() {
        return Boolean.TRUE.equals(this.ativo);
    }

    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return email;
    }

    public String getToken() {
        return token;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public String getBiografia() {
        return biografia;
    }

    public LocalDateTime getExpiracaoToken() {
        return expiracaoToken;
    }

    public Boolean getVerificado() {
        return verificado;
    }

    public void verificar() {
        if(expiracaoToken.isBefore(LocalDateTime.now())){
            throw new RegraDeNegocioException("Link de verificação expirou!");
        }
        this.verificado = true;
        this.ativo = true;
        this.token = null;
        this.expiracaoToken = null;
    }

    public String getNome() {
        return nomeUsuario;
    }
}
