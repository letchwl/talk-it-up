package br.com.tiu.forum.domain.resposta;

import br.com.tiu.forum.domain.topico.Topico;
import br.com.tiu.forum.domain.topico.TopicoRepository;
import br.com.tiu.forum.domain.usuario.Usuario;
import br.com.tiu.forum.infra.exception.RegraDeNegocioException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class RespostaService {

    private final RespostaRepository respostaRepository;
    private final TopicoRepository topicoRepository;

    public RespostaService(RespostaRepository respostaRepository, TopicoRepository topicoRepository) {
        this.respostaRepository = respostaRepository;
        this.topicoRepository = topicoRepository;
    }

    @Transactional
    public void criarResposta(DadosCriarResposta dados, Usuario autor) {
        Topico topico = topicoRepository.findById(dados.topicoId())
                .orElseThrow(() -> new RegraDeNegocioException("Tópico não encontrado"));

        Resposta resposta = new Resposta();
        resposta.setMensagem(dados.mensagem());
        resposta.setAutor(autor);
        resposta.setTopico(topico);
        resposta.setDataCriacao(LocalDateTime.now());

        respostaRepository.save(resposta);
    }
}
