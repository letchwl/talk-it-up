package br.com.tiu.forum.controller;

import br.com.tiu.forum.infra.exception.RegraDeNegocioException;
import br.com.tiu.forum.model.usuario.*;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping("/registrar")
    public String exibirFormularioRegistro(Model model) {
        model.addAttribute("dadosCadastroUsuario", new DadosCadastroUsuario("", "", "", "", ""));
        return "login/registrar";
    }

    @PostMapping("/registrar")
    public String cadastrar(@Valid @ModelAttribute("dadosCadastroUsuario") DadosCadastroUsuario dados,
                            BindingResult result,
                            RedirectAttributes attr) {
        if (result.hasErrors()) {
            return "login/registrar";
        }

        var usuario = usuarioService.cadastrar(dados);
        attr.addFlashAttribute("mensagemSucesso", "Cadastro realizado com sucesso! Verifique seu e-mail.");

        return "redirect:/login";
    }

    @GetMapping("/verificar-conta")
    public String verificarEmail(@RequestParam String codigo, RedirectAttributes attr) {
        try {
            usuarioService.verificarEmail(codigo);
            attr.addFlashAttribute("mensagemSucesso", "Conta verificada com sucesso! Você já pode fazer login.");
        } catch (RegraDeNegocioException e) {
            attr.addFlashAttribute("mensagemErro", e.getMessage());
        }

        return "redirect:login/login";
    }

    @GetMapping("perfil/{nomeUsuario}")
    public String exibirPerfil(@PathVariable String nomeUsuario, Model model) {
        var usuario = usuarioService.buscarPeloNomeUsuario(nomeUsuario);
        model.addAttribute("usuario", usuario); // Não passa DTO se o HTML espera o original
        return "usuario/perfil";
    }

    @GetMapping("/perfil/{nomeUsuario}/editar")
    public String exibirFormularioEdicao(@PathVariable String nomeUsuario,
                                         @AuthenticationPrincipal Usuario logado,
                                         Model model) {

        if (!logado.getNomeUsuario().equals(nomeUsuario)) {
            return "redirect:/acesso-negado";
        }

        var dados = new DadosAtualizacaoUsuario(
                logado.getNomeUsuario(),
                logado.getDisplayName(),
                logado.getBiografia() != null ? logado.getBiografia() : ""
        );

        model.addAttribute("dados", dados);
        model.addAttribute("usuario", logado);
        return "usuario/editar";
    }

    @PutMapping("/perfil/{nomeUsuario}/editar")
    public String editarPerfil(@ModelAttribute("dados") @Valid DadosAtualizacaoUsuario dados,
                               BindingResult result,
                               @AuthenticationPrincipal Usuario logado,
                               Model model,
                               RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            model.addAttribute("erro", "Erro na validação dos dados.");
            return "usuario/editar";
        }

        var usuario = usuarioService.editarPerfil(logado, dados);
        redirectAttributes.addFlashAttribute("mensagem", "Perfil atualizado com sucesso!");
        return "redirect:/perfil/" + logado.getNomeUsuario();
    }
}