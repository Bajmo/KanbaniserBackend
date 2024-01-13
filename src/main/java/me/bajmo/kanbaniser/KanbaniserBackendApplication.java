package me.bajmo.kanbaniser;

import me.bajmo.kanbaniser.models.ERole;
import me.bajmo.kanbaniser.models.Role;
import me.bajmo.kanbaniser.repositories.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class KanbaniserBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(KanbaniserBackendApplication.class, args);
    }

    @Bean
    public CommandLineRunner dataLoader(RoleRepository roleRepository) {
        return args -> {
            insertRoleIfNotExists(roleRepository);
        };
    }

    private static void insertRoleIfNotExists(RoleRepository roleRepository) {
        if (roleRepository.findByName(ERole.USER).isEmpty()) {
            Role role = new Role();
            role.setName(ERole.USER);
            roleRepository.save(role);
        }
    }

}
