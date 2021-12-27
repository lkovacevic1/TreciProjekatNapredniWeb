package com.example.TreciProjekatNapredniWeb.services;

import com.example.TreciProjekatNapredniWeb.mapper.UserMapper;
import com.example.TreciProjekatNapredniWeb.model.Role;
import com.example.TreciProjekatNapredniWeb.model.User;
import com.example.TreciProjekatNapredniWeb.model.UserInfo;
import com.example.TreciProjekatNapredniWeb.repositories.RoleRepository;
import com.example.TreciProjekatNapredniWeb.repositories.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImplementation implements UserService{

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    public UserServiceImplementation(UserRepository userRepo, RoleRepository roleRepo, PasswordEncoder passwordEncoder, UserMapper userMapper) {
        this.userRepository = userRepo;
        this.roleRepository = roleRepo;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
    }

    @Override
    public User saveUser(User user) {
        user.setPassword(this.passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public Role saveRole(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public void addRoleToUser(String mail, String roleName) {
        User user = userRepository.findByMail(mail);
        Role role = roleRepository.findByName(roleName);
        user.getRoles().add(role);
        userRepository.save(user);
    }

    @Override
    public User updateUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public Page<UserInfo> getUsers() {
        return userRepository.findAll(PageRequest.of(0, 2)).map(userMapper::UserToUserInfo);
    }
}
