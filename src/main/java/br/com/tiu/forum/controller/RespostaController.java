package br.com.tiu.forum.controller;

import br.com.tiu.forum.domain.resposta.*;
import br.com.tiu.forum.domain.usuario.Usuario;
import br.com.tiu.forum.infra.exception.RegraDeNegocioException;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class RespostaController {

    private final RespostaService respostaService;

    public RespostaController(RespostaService respostaService) {
        this.respostaService = respostaService;
    }

    @GetMapping("/topicos/{id}/respostas/novo")
    public String formularioNovaResposta(@PathVariable Long id, Model model) {
        model.addAttribute("topicoId", id);
        model.addAttribute("dadosCriarResposta", new DadosCriarResposta("", id));
        return "respostas/novo"; // nome do template para o formulário
    }

    @PostMapping("/topicos/{id}/respostas/novo")
    public String criarResposta(@Valid DadosCriarResposta dados,
                                BindingResult result,
                                @AuthenticationPrincipal Usuario autor,
                                RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("erros", result.getAllErrors());
            return "redirect:/topicos/" + dados.topicoId();
        }

        try {
            respostaService.criarResposta(dados, autor);
            redirectAttributes.addFlashAttribute("mensagem", "Resposta criada com sucesso!");
        } catch (RegraDeNegocioException e) {
            redirectAttributes.addFlashAttribute("erro", e.getMessage());
        }

        return "redirect:/topicos/" + dados.topicoId();
    }
}
