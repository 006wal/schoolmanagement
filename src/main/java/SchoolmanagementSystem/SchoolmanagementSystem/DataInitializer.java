/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author walen
 */
package SchoolmanagementSystem.SchoolmanagementSystem;

import SchoolmanagementSystem.SchoolmanagementSystem.model.Role;
import SchoolmanagementSystem.SchoolmanagementSystem.model.User;
import SchoolmanagementSystem.SchoolmanagementSystem.repository.RoleRepository;
import SchoolmanagementSystem.SchoolmanagementSystem.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.util.HashSet;
import java.util.Set;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public DataInitializer(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (userRepository.count() == 0) {
            // Créer les rôles
            Role adminRole = new Role();
            adminRole.setName("ROLE_ADMIN");
            roleRepository.save(adminRole);

            Role teacherRole = new Role();
            teacherRole.setName("ROLE_TEACHER");
            roleRepository.save(teacherRole);

            Role studentRole = new Role();
            studentRole.setName("ROLE_STUDENT");
            roleRepository.save(studentRole);

            // Créer admin
            User admin = new User();
            admin.setEmail("admin@school.com");
            admin.setPassword("admin123");
            Set<Role> adminRoles = new HashSet<>();
            adminRoles.add(adminRole);
            admin.setRoles(adminRoles);
            userRepository.save(admin);

            // Créer teacher marc
            User marc = new User();
            marc.setEmail("marc.prof@school.com");
            marc.setPassword("prof123");
            Set<Role> teacherRoles = new HashSet<>();
            teacherRoles.add(teacherRole);
            marc.setRoles(teacherRoles);
            userRepository.save(marc);

            // Créer teacher jean
            User jean = new User();
            jean.setEmail("jean@ecole.com");
            jean.setPassword("jean123");
            Set<Role> jeanRoles = new HashSet<>();
            jeanRoles.add(teacherRole);
            jean.setRoles(jeanRoles);
            userRepository.save(jean);

            // Créer student alice
            User alice = new User();
            alice.setEmail("alice.student@school.com");
            alice.setPassword("student123");
            Set<Role> studentRoles = new HashSet<>();
            studentRoles.add(studentRole);
            alice.setRoles(studentRoles);
            userRepository.save(alice);

            // Créer student wall
            User wall = new User();
            wall.setEmail("wall@school.com");
            wall.setPassword("Wall123");
            Set<Role> wallRoles = new HashSet<>();
            wallRoles.add(studentRole);
            wall.setRoles(wallRoles);
            userRepository.save(wall);
        }
    }
}