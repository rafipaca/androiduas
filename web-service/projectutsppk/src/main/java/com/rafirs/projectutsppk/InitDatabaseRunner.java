package com.rafirs.projectutsppk;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.rafirs.projectutsppk.entity.ERole;
import com.rafirs.projectutsppk.entity.Role;
import com.rafirs.projectutsppk.entity.User;
import com.rafirs.projectutsppk.repository.RoleRepository;
import com.rafirs.projectutsppk.repository.UserRepository;

/**
 *
 * @author Rafi
 */
@Component
public class InitDatabaseRunner implements CommandLineRunner {
    @Autowired
    private Environment env;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public void run(String... args) throws Exception {
        if (roleRepository.count() == 0) {
            Role pengguna = Role.builder()
                .name(ERole.ROLE_USER)
                .build();
            
            
            Role admin = Role.builder()
                .name(ERole.ROLE_ADMIN)
                .build();

            roleRepository.save(pengguna);
            roleRepository.save(admin);
        }

        if (userRepository.count() == 0) {
            Optional<Role> adminRole = roleRepository.findByName(ERole.ROLE_ADMIN);
            if (adminRole.isEmpty()) {
                return;
            }

            Set<Role> roles = new HashSet<>();
            roles.add(adminRole.get());

            User user = User.builder()
                .name("Admin")
                .email(env.getProperty("rafi.root.email"))
                .password(encoder.encode(env.getProperty("rafi.root.password")))
                .roles(roles)
                .build();
            
            userRepository.save(user);
        }
    }
}
