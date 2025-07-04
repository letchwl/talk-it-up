package br.com.tiu.forum.controller;

import br.com.tiu.forum.model.topico.*;
import br.com.tiu.forum.model.usuario.Usuario;
import br.com.tiu.forum.model.usuario.UsuarioRepository;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    private final UsuarioRepository usuarioRepository;

    public TopicoController(TopicoService topicoService, UsuarioRepository usuarioRepository) {
        this.topicoService = topicoService;
        this.usuarioRepository = usuarioRepository;
    }

    @GetMapping
    public String listarTopicos(Pageable pageable, Model model, Principal principal) {
        Page<DadosListagemTopico> topicos = topicoService.listarTopicos(pageable);
        model.addAttribute("topicos", topicos);

        String emailLogado = principal.getName();

        Optional<Usuario> usuario = usuarioRepository.findByEmailIgnoreCaseAndVerificadoTrue(emailLogado);

        String nomeUsuarioLogado = usuario.get().getNomeUsuario();

        model.addAttribute("nomeUsuarioLogado", nomeUsuarioLogado);

        return "topicos/lista";
    }



    @GetMapping("/novo")
    public String formularioNovoTopico(Model model) {
        model.addAttribute("dadosCadastroTopico", new DadosCadastroTopico(null, null, null, null));
        return "topicos/novo";
    }

    @PostMapping
    public String cadastrar(
            @ModelAttribute @Valid DadosCadastroTopico dadosCadastroTopico,
            BindingResult result,
            RedirectAttributes redirectAttributes,
            @AuthenticationPrincipal Usuario autor) {

        if (result.hasErrors()) {
            return "topicos/novo";
        }

        topicoService.cadastrar(dadosCadastroTopico, autor);
        redirectAttributes.addFlashAttribute("mensagemSucesso", "Tópico criado com sucesso!");
        return "redirect:/topicos";
    }

    @PostMapping("/{id}/excluir")
    public String deletar(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        topicoService.deletar(id);
        redirectAttributes.addFlashAttribute("mensagemSucesso", "Tópico excluído com sucesso!");
        return "redirect:/topicos";
    }
}
