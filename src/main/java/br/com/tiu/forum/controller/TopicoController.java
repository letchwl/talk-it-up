package br.com.tiu.forum.controller;

import br.com.tiu.forum.infra.exception.RegraDeNegocioException;
import br.com.tiu.forum.model.topico.*;
import br.com.tiu.forum.model.usuario.Usuario;
import br.com.tiu.forum.model.usuario.UsuarioRepository;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.Optional;

@Controller
@RequestMapping("/topicos")
public class TopicoController {

    private final TopicoService topicoService;
    private final TopicoRepository topicoRepository;
    private final UsuarioRepository usuarioRepository;

    public TopicoController(TopicoService topicoService, TopicoRepository topicoRepository, UsuarioRepository usuarioRepository) {
        this.topicoService = topicoService;
        this.topicoRepository = topicoRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @GetMapping
    public String listarTopicos(Pageable pageable, Model model, Principal principal) {
        Page<DadosListagemTopico> topicos = topicoService.listarTopicos(pageable);
        model.addAttribute("topicos", topicos);

        String emailLogado = principal.getName();

        Optional<Usuario> usuario = usuarioRepository.findByEmailIgnoreCaseAndVerificadoTrue(emailLogado);

        if (usuario.isPresent()) {
            model.addAttribute("usuario", usuario.get());  // chave "usuario"
            model.addAttribute("nomeUsuarioLogado", usuario.get().getNomeUsuario());  // opcional
        } else {
            model.addAttribute("usuario", null);
        }

        return "topicos/lista";
    }

    @GetMapping("/novo")
    public String formularioNovoTopico(Model model) {
        model.addAttribute("dadosCadastroTopico", new DadosCadastroTopico(null, null, null));
        return "topicos/novo";
    }

    @PostMapping
    public String cadastrar(
            @ModelAttribute @Valid DadosCadastroTopico dados,
            BindingResult result,
            RedirectAttributes redirectAttributes,
            Principal principal) {

        if (result.hasErrors()) {
            return "topicos/novo";
        }

        String emailLogado = principal.getName();
        Usuario autor = usuarioRepository.findByEmailIgnoreCaseAndVerificadoTrue(emailLogado)
                .orElseThrow(() -> new RegraDeNegocioException("Usuário não encontrado!"));

        topicoService.cadastrar(dados, autor);
        redirectAttributes.addFlashAttribute("mensagemSucesso", "Tópico criado com sucesso!");
        return "redirect:/topicos";
    }

    @GetMapping("/{id}/editar")
    public String abrirEdicao(@PathVariable Long id, Model model) {
        var topico = topicoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tópico não encontrado"));
        var dados = new DadosAtualizacaoTopico(topico.getId(), topico.getTitulo(), topico.getMensagem());
        model.addAttribute("dados", dados);
        return "topicos/editar";
    }

    @PutMapping("/{id}/editar")
    public String editarTopico(@PathVariable Long id,
                               @Valid DadosAtualizacaoTopico dados,
                               BindingResult bindingResult,
                               RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.dadosAtualizacaoTopico", bindingResult);
            redirectAttributes.addFlashAttribute("dadosAtualizacaoTopico", dados);
            return "redirect:/topicos/" + id + "/editar";
        }

        try {
            DadosListagemTopico atualizado = topicoService.atualizar(dados, id);
            redirectAttributes.addFlashAttribute("sucesso", "Tópico atualizado com sucesso!");
            return "redirect:/topicos";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", e.getMessage());
            redirectAttributes.addFlashAttribute("dadosAtualizacaoTopico", dados);
            return "redirect:/topicos/" + id + "/editar";
        }
    }

    @DeleteMapping("/{id}/excluir")
    public String deletar(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        topicoService.deletar(id);
        redirectAttributes.addFlashAttribute("mensagemSucesso", "Tópico excluído com sucesso!");
        return "redirect:/topicos";
    }
}
