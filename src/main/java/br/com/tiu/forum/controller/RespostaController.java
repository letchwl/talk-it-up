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
        return "respostas/novo";
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

    @GetMapping("/respostas/{id}/editar")
    public String mostrarFormularioEdicao(@PathVariable Long id,
                                          @AuthenticationPrincipal Usuario usuario,
                                          Model model,
                                          RedirectAttributes redirectAttributes) {
        try {
            Resposta resposta = respostaService.buscarPorId(id);

            if (!resposta.getAutor().equals(usuario)) {
                redirectAttributes.addFlashAttribute("erro", "Você não tem permissão para editar esta resposta.");
                return "redirect:/topicos/" + resposta.getTopico().getId();
            }

            DadosAtualizacaoResposta dados = new DadosAtualizacaoResposta(resposta.getMensagem());

            model.addAttribute("dadosAtualizacaoResposta", dados);
            model.addAttribute("respostaId", id);
            model.addAttribute("topicoId", resposta.getTopico().getId());
            return "respostas/editar";
        } catch (RegraDeNegocioException e) {
            redirectAttributes.addFlashAttribute("erro", e.getMessage());
            return "redirect:/";
        }
    }

    @PutMapping("/respostas/{id}/editar")
    public String editarResposta(@PathVariable Long id,
                                 @Valid @ModelAttribute("dadosAtualizacaoResposta") DadosAtualizacaoResposta dados,
                                 BindingResult result,
                                 @AuthenticationPrincipal Usuario autor,
                                 Model model,
                                 RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("respostaId", id);
            model.addAttribute("topicoId", respostaService.buscarPorId(id).getTopico().getId());
            return "respostas/editar";
        }

        try {
            respostaService.atualizar(id, dados, autor);
            redirectAttributes.addFlashAttribute("mensagem", "Resposta atualizada com sucesso!");
        } catch (RegraDeNegocioException e) {
            redirectAttributes.addFlashAttribute("erro", e.getMessage());
            return "redirect:/respostas/" + id + "/editar";
        }

        Long topicoId = respostaService.buscarPorId(id).getTopico().getId();
        return "redirect:/topicos/" + topicoId;
    }

    @DeleteMapping("/{id}")
    public String excluirResposta(@PathVariable Long id,
                                  @AuthenticationPrincipal Usuario autor,
                                  RedirectAttributes redirectAttributes) {
        try {
            Long topicoId = respostaService.buscarPorId(id).getTopico().getId();
            respostaService.excluirResposta(id, autor);
            redirectAttributes.addFlashAttribute("mensagem", "Resposta excluída com sucesso!");
            return "redirect:/topicos/" + topicoId;
        } catch (RegraDeNegocioException e) {
            redirectAttributes.addFlashAttribute("erro", e.getMessage());
            return "redirect:/topicos";
        }

    }
}