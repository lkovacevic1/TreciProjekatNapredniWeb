package com.example.TreciProjekatNapredniWeb.bootstrap;

import com.example.TreciProjekatNapredniWeb.model.Role;
import com.example.TreciProjekatNapredniWeb.model.User;
import com.example.TreciProjekatNapredniWeb.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class BootstrapData implements CommandLineRunner {

    private final PasswordEncoder passwordEncoder;
    private final UserService userService;

    @Autowired
    public BootstrapData(PasswordEncoder passwordEncoder, UserService userService) {
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
    }

    @Override
    public void run(String... args) throws Exception {

        userService.saveRole(new Role(null, "can_read_users"));
        userService.saveRole(new Role(null, "can_create_users"));
        userService.saveRole(new Role(null, "can_update_users"));
        userService.saveRole(new Role(null, "can_delete_users"));

        userService.saveUser(new User(null, "Luka", "Kovacevic", "lkovacevic@raf.rs", "1234"));
        userService.saveUser(new User(null, "Andrej", "Gasic", "agasic@raf.rs", "1234"));
        userService.saveUser(new User(null, "Stefan", "Budimac", "sbudimac@raf.rs", "1234"));
        userService.saveUser(new User(null, "Anastasija", "Radonjic", "aradonjic@raf.rs", "1234"));

        userService.addRoleToUser("lkovacevic@raf.rs", "can_read_users");
        userService.addRoleToUser("lkovacevic@raf.rs", "can_create_users");
        userService.addRoleToUser("agasic@raf.rs", "can_read_users");
        userService.addRoleToUser("agasic@raf.rs", "can_update_users");
        userService.addRoleToUser("sbudimac@raf.rs", "can_delete_users");
    }
}
