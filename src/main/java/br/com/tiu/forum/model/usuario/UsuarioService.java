package br.com.tiu.forum.model.usuario;

import br.com.tiu.forum.infra.email.EmailService;
import br.com.tiu.forum.infra.exception.RegraDeNegocioException;
import jakarta.transaction.Transactional;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UsuarioService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository, EmailService emailService, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.emailService = emailService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByEmailIgnoreCaseAndVerificadoTrue(username)
                .orElseThrow(() -> new UsernameNotFoundException("O usuário não foi encontrado!"));

        if (!usuario.isEnabled()) {
            throw new DisabledException("Conta não verificada. Por favor, verifique seu e-mail.");
        }

        return usuario;
    }


    @Transactional
    public Usuario cadastrar(DadosCadastroUsuario dados) {
        var senhaCriptografada = passwordEncoder.encode(dados.senha());

        var usuario = new Usuario(dados, senhaCriptografada);

        emailService.enviarEmailVerificacao(usuario);
        return usuarioRepository.save(usuario);
    }

    @Transactional
    public Usuario editarPerfil(Usuario usuario, DadosAtualizacaoUsuario dados) {
        if (dados.biografia() != null && dados.biografia().length() > 150) {
            throw new RegraDeNegocioException("A bio deve ter no máximo 150 caracteres");
        }

        if (dados.nomeUsuario() == null || dados.nomeUsuario().isBlank()) {
            throw new RegraDeNegocioException("O nome de usuário não pode estar vazio");
        }
        if (dados.nomeUsuario().length() < 3 || dados.nomeUsuario().length() > 30) {
            throw new RegraDeNegocioException("O nome de usuário deve ter entre 3 e 20 caracteres");
        }

        if (dados.displayName() == null || dados.displayName().isBlank()) {
            throw new RegraDeNegocioException("O display name não pode estar vazio");
        }
        if (dados.displayName().length() < 3 || dados.displayName().length() > 50) {
            throw new RegraDeNegocioException("O display name deve ter entre 3 e 30 caracteres");
        }

        usuario.alterarDados(dados);
        return usuarioRepository.save(usuario);
    }


    @Transactional
    public void verificarEmail(String codigo) {
        var usuario = usuarioRepository.findByToken(codigo)
                .orElseThrow(() -> new RegraDeNegocioException("Token inválido"));

        if (usuario.getVerificado() != null && usuario.getVerificado()) {
            throw new RegraDeNegocioException("Conta já verificada");
        }

        if (usuario.getExpiracaoToken() != null && usuario.getExpiracaoToken().isBefore(LocalDateTime.now())) {
            throw new RegraDeNegocioException("Token expirado. Solicite um novo link de verificação.");
        }

        usuario.verificar();
    }

    public Usuario buscarPeloNomeUsuario(String nomeUsuario) {
        return usuarioRepository.findByNomeUsuarioIgnoreCaseAndVerificadoTrueAndAtivoTrue(nomeUsuario).orElseThrow(
                () -> new RegraDeNegocioException("Usuário não encontrado!"));
    }

}
