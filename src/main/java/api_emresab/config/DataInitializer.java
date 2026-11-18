package api_emresab.config;

import api_emresab.model.Usuario;
import api_emresab.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner initData(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            // Si no existe el usuario admin, lo creamos automáticamente
            if (usuarioRepository.findByUsername("admin").isEmpty()) {
                Usuario admin = new Usuario();
                admin.setUsername("admin");
                // Contraseña encriptada correspondiente a "12345"
                admin.setPassword(passwordEncoder.encode("12345"));
                admin.setRol("ROLE_ADMIN");
                
                usuarioRepository.save(admin);
                System.out.println(">>> ¡Usuario administrador creado con éxito! (User: admin / Pass: 12345)");
            }
        };
    }
}