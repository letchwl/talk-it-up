package br.com.tiu.forum.domain.usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByEmailIgnoreCaseAndVerificadoTrue(String username);

    Optional<Usuario> findByToken(String codigo);

    Optional<Usuario> findByNomeUsuarioIgnoreCaseAndVerificadoTrueAndAtivoTrue(String nomeUsuario);

    boolean existsByNomeUsuario(@Size(min = 3, max = 20, message = "Nome de usuário deve ter entre 3 e 20 caracteres") @NotBlank String s);

    boolean existsByEmail(@Email @NotBlank String email);

    boolean existsByDisplayName(@Size(min = 3, max = 30, message = "Display name deve ter entre 3 e 30 caracteres") @NotBlank String s);

    boolean existsByNomeUsuarioAndIdNot(@Size(min = 3, max = 20, message = "Nome de usuário deve ter entre 3 e 20 caracteres") @NotBlank String nomeUsuario, Long id);

    boolean existsByDisplayNameAndIdNot(@Size(min = 3, max = 30, message = "Display name deve ter entre 3 e 30 caracteres") @NotBlank String displayName, Long id);
}
