package br.com.tiu.forum.model.topico;

import br.com.tiu.forum.model.usuario.Usuario;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
public class TopicoService {

    private final TopicoRepository topicoRepository;

    public TopicoService(TopicoRepository topicoRepository) {
        this.topicoRepository = topicoRepository;
    }

    @Transactional
    public Topico cadastrar(DadosCadastroTopico dados, Usuario autor) {
        var topico = new Topico(dados, autor);
        return topicoRepository.save(topico);
    }


    public Page<DadosListagemTopico> listarTopicos(Pageable pageable) {
        return topicoRepository.findAll(pageable)
                .map(DadosListagemTopico::new);
    }

    @Transactional
    public DadosListagemTopico atualizar(DadosAtualizacaoTopico dados) {
        Topico topico = topicoRepository.getReferenceById(dados.id());
        topico.setTitulo(dados.titulo());
        topico.setMensagem(dados.mensagem());
        return new DadosListagemTopico(topico);
    }

    public void deletar(Long id) {
        if (!topicoRepository.existsById(id)) {
            throw new EntityNotFoundException("Tópico com ID " + id + " não encontrado.");
        }
        topicoRepository.deleteById(id);
    }

}
