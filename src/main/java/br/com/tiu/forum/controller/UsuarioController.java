package br.com.tiu.forum.controller;

import br.com.tiu.forum.infra.exception.RegraDeNegocioException;
import br.com.tiu.forum.model.usuario.DadosCadastroUsuario;
import br.com.tiu.forum.model.usuario.DadosListagemUsuario;
import br.com.tiu.forum.model.usuario.UsuarioService;
import jakarta.validation.Valid;
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

    @GetMapping("/{nomeUsuario}")
    public String exibirPerfil(@PathVariable String nomeUsuario, Model model){
        var usuario = usuarioService.buscarPeloNomeUsuario(nomeUsuario);
        model.addAttribute("usuario", new DadosListagemUsuario(usuario));
        return "usuario/perfil";
    }


}
