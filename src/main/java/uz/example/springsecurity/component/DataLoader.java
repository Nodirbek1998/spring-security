package uz.example.springsecurity.component;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import uz.example.springsecurity.entity.Role;
import uz.example.springsecurity.entity.RoleName;
import uz.example.springsecurity.entity.User;
import uz.example.springsecurity.repository.RoleRepository;
import uz.example.springsecurity.repository.UserRepository;

import java.util.HashSet;

@Component
public class DataLoader implements CommandLineRunner {

    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;


    @Value("${spring.jpa.hibernate.ddl-auto}")
    private String modeInitial;

    public DataLoader(RoleRepository roleRepository, PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }


    @Override
    public void run(String... args) throws Exception {
        if(modeInitial.equals("create")) {
            Role.RoleBuilder roleBuilder = Role.builder().roleName(RoleName.SUPERADMIN);
            roleRepository.save(roleBuilder.build());
            roleBuilder = Role.builder().roleName(RoleName.STUDENT);
            roleRepository.save(roleBuilder.build());


            HashSet<Role> roles = new HashSet<>(roleRepository.findAll());

            User admins = new User();
            admins.setUsername("nodir");
            admins.setPassword(passwordEncoder.encode("1234"));
            admins.setFirstName("Nodirbek");
            admins.setLastName("Mamadaliyev");
            admins.setRoles(roles);
            userRepository.save(admins);
        }
    }
}
