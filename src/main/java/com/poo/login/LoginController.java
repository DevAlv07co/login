package com.poo.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    // Cuando entras desde el navegador, este método te muestra el HTML del login
    @GetMapping("/login")
    public String mostrarFormularioLogin() {
        return "login";
    }

    // Cuando presionas el botón "Entrar", este método recibe tus datos y los revisa
    @PostMapping("/login")
    public String procesarLogin(@RequestParam String username,
                                @RequestParam String password,
                                Model model) {

        // Busca al usuario en MySQL
        Usuario usuario = usuarioRepository.findByUsername(username);

        // Si lo encuentra y la contraseña es correcta...
        if (usuario != null && usuario.getPassword().equals(password)) {
            model.addAttribute("nombreUsuario", usuario.getUsername());
            return "welcome"; // Te manda a welcome.html
        } else {
            // Si te equivocas...
            model.addAttribute("error", "Usuario o contraseña incorrectos");
            return "login"; // Te regresa a login.html con un mensaje de error
        }
    }
}