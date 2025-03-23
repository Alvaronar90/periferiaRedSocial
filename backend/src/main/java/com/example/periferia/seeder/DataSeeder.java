package com.example.periferia.seeder;

import com.example.periferia.model.User;
import com.example.periferia.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public DataSeeder(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        // Verificar si ya existen usuarios para evitar duplicados
        if(userRepository.count() == 0) {
            // Crear usuario de prueba 1
            User user1 = new User();
            user1.setName("Pedro");
            user1.setEmail("pedro@correo.com");
            user1.setPassword(passwordEncoder.encode("123456"));

            // Crear usuario de prueba 2
            User user2 = new User();
            user2.setName("Alicia");
            user2.setEmail("alicia@correo.com");
            user2.setPassword(passwordEncoder.encode("123456"));

            // Guardar los usuarios en la base de datos
            userRepository.save(user1);
            userRepository.save(user2);

            System.out.println("Usuarios de prueba creados exitosamente.");
        } else {
            System.out.println("La base de datos ya tiene usuarios, se omite la inserción de datos de prueba.");
        }
    }
}

