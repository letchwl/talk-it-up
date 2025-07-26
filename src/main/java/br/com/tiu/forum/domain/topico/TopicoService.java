package br.com.tiu.forum.domain.topico;

import br.com.tiu.forum.domain.usuario.Usuario;
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
    public DadosListagemTopico buscarDtoPorIdComRespostas(Long id) {
        Topico topico = topicoRepository.buscarPorIdComRespostas(id)
                .orElseThrow(() -> new EntityNotFoundException("Tópico não encontrado com id: " + id));
        return new DadosListagemTopico(topico);
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
    public DadosListagemTopico atualizar(DadosAtualizacaoTopico dados, Long idPath) {
        try {
            if (!idPath.equals(dados.id())) {
                throw new IllegalArgumentException("ID do tópico inválido.");
            }

            Topico topico = topicoRepository.findById(idPath)
                    .orElseThrow(() -> new EntityNotFoundException("Tópico não encontrado."));

            topico.setTitulo(dados.titulo());
            topico.setMensagem(dados.mensagem());

            return new DadosListagemTopico(topico);

        } catch (IllegalArgumentException | EntityNotFoundException e) {
            // aqui você pode logar o erro se quiser
            throw e;  // relança para o controller tratar
        } catch (Exception e) {
            // log de erro genérico
            throw new RuntimeException("Erro inesperado ao atualizar tópico.", e);
        }
    }

    public void deletar(Long id) {
        if (!topicoRepository.existsById(id)) {
            throw new EntityNotFoundException("Tópico com ID " + id + " não encontrado.");
        }
        topicoRepository.deleteById(id);
    }

}
