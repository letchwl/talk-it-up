package br.com.tiu.forum.model.usuario;

public record DadosListagemUsuario(Long id,
                                   String email,
                                   String displayName,
                                   String nomeUsuario,
                                   String biografia
) {
    public DadosListagemUsuario(Usuario usuario) {
        this(usuario.getId(), usuario.getUsername(),
                usuario.getDisplayName(), usuario.getNomeUsuario(),
                usuario.getBiografia());
    }
}
