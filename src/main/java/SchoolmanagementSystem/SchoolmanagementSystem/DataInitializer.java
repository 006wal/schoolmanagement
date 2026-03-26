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
import SchoolmanagementSystem.SchoolmanagementSystem.repository.GradeRepository;
import SchoolmanagementSystem.SchoolmanagementSystem.repository.RoleRepository;
import SchoolmanagementSystem.SchoolmanagementSystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.util.HashSet;
import java.util.Set;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Autowired
    private GradeRepository gradeRepository;

    public DataInitializer(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        Role adminRole = roleRepository.findByName("ROLE_ADMIN")
                .orElseGet(() -> roleRepository.save(new Role("ROLE_ADMIN")));

        Role teacherRole = roleRepository.findByName("ROLE_TEACHER")
                .orElseGet(() -> roleRepository.save(new Role("ROLE_TEACHER")));

        Role studentRole = roleRepository.findByName("ROLE_STUDENT")
                .orElseGet(() -> roleRepository.save(new Role("ROLE_STUDENT")));

        // Vider grades avant users
        gradeRepository.deleteAll();
        userRepository.deleteAll();

        User admin = new User();
        admin.setName("Admin");
        admin.setEmail("admin@school.com");
        admin.setPassword("admin123");
        admin.setEnabled(true);
        Set<Role> adminRoles = new HashSet<>();
        adminRoles.add(adminRole);
        admin.setRoles(adminRoles);
        userRepository.save(admin);

        User marc = new User();
        marc.setName("Marc");
        marc.setEmail("marc.prof@school.com");
        marc.setPassword("prof123");
        marc.setEnabled(true);
        Set<Role> marcRoles = new HashSet<>();
        marcRoles.add(teacherRole);
        marc.setRoles(marcRoles);
        userRepository.save(marc);

        User jean = new User();
        jean.setName("Jean");
        jean.setEmail("jean@ecole.com");
        jean.setPassword("jean123");
        jean.setEnabled(true);
        Set<Role> jeanRoles = new HashSet<>();
        jeanRoles.add(teacherRole);
        jean.setRoles(jeanRoles);
        userRepository.save(jean);

        User alice = new User();
        alice.setName("Alice");
        alice.setEmail("alice.student@school.com");
        alice.setPassword("student123");
        alice.setEnabled(true);
        Set<Role> aliceRoles = new HashSet<>();
        aliceRoles.add(studentRole);
        alice.setRoles(aliceRoles);
        userRepository.save(alice);

        User wall = new User();
        wall.setName("Wall");
        wall.setEmail("wall@school.com");
        wall.setPassword("Wall123");
        wall.setEnabled(true);
        Set<Role> wallRoles = new HashSet<>();
        wallRoles.add(studentRole);
        wall.setRoles(wallRoles);
        userRepository.save(wall);
    }
}