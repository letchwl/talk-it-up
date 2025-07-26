package br.com.tiu.forum.domain.topico;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record DadosCadastroTopico(
        @Size(max = 100, message = "O título deve ter no máximo 255 caracteres")
        @NotBlank String titulo,
        @Size(max = 2000, message = "A mensagem deve ter no máximo 2000 caracteres")
        @NotBlank String mensagem,
        @NotNull Categoria categoria
) {}

