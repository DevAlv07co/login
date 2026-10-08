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

    // Muestra el formulario de inicio de sesión
    @GetMapping("/login")
    public String mostrarFormularioLogin() {
        return "login";
    }

    // Procesa el inicio de sesión
    @PostMapping("/login")
    public String procesarLogin(@RequestParam String username,
                                @RequestParam String password,
                                Model model) {
        Usuario usuario = usuarioRepository.findByUsername(username);

        if (usuario != null && usuario.getPassword().equals(password)) {
            model.addAttribute("nombreUsuario", usuario.getUsername());
            return "welcome";
        } else {
            model.addAttribute("error", "Usuario o contraseña incorrectos");
            return "login";
        }
    }

    // NUEVO MÉTODO: Registra un nuevo usuario en la base de datos
    @PostMapping("/registrar")
    public String registrarNuevoUsuario(@RequestParam String nuevoUsername,
                                        @RequestParam String nuevoPassword,
                                        @RequestParam String usuarioActual,
                                        Model model) {

        // 1. Verificamos si el nombre ya existe en la base de datos
        if (usuarioRepository.findByUsername(nuevoUsername) != null) {
            model.addAttribute("errorRegistro", "El usuario '" + nuevoUsername + "' ya existe.");
        } else {
            // 2. Si no existe, creamos el objeto Usuario y lo guardamos
            Usuario nuevo = new Usuario();
            nuevo.setUsername(nuevoUsername);
            nuevo.setPassword(nuevoPassword);

            // Esta línea ejecuta el INSERT INTO en MySQL automáticamente
            usuarioRepository.save(nuevo);

            model.addAttribute("mensajeExito", "¡Usuario '" + nuevoUsername + "' registrado con éxito!");
        }

        // Mantenemos el saludo al usuario actual en la pantalla
        model.addAttribute("nombreUsuario", usuarioActual);
        return "welcome";
    }
}