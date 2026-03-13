/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package SchoolmanagementSystem.SchoolmanagementSystem.service;

import SchoolmanagementSystem.SchoolmanagementSystem.model.User;
import SchoolmanagementSystem.SchoolmanagementSystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired 
    private UserRepository repo;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // 1. On cherche l'utilisateur par son email (identifiant de ton formulaire)
        User user = repo.findByEmail(email);
        
        // 2. Si l'utilisateur n'existe pas, on lance une erreur
        if (user == null) {
            throw new UsernameNotFoundException("Utilisateur non trouvé avec l'email : " + email);
        }

        // 3. On construit l'objet UserDetails que Spring Security comprend
        return org.springframework.security.core.userdetails.User
                .withUsername(user.getEmail())
                .password(user.getPassword()) // Utilise NoOpPasswordEncoder (texte clair) défini dans Config.java
                .authorities(user.getRoles().stream()
                    // CORRECTION ICI : On utilise directement le nom du rôle de la base (ROLE_ADMIN)
                    .map(role -> new SimpleGrantedAuthority(role.getName()))
                    .collect(Collectors.toList()))
                .disabled(!user.isEnabled()) // Désactive si enabled = 0 dans MySQL
                .build();
    }
}