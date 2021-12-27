package com.example.TreciProjekatNapredniWeb.services;

import com.example.TreciProjekatNapredniWeb.model.Role;
import com.example.TreciProjekatNapredniWeb.model.User;
import com.example.TreciProjekatNapredniWeb.model.UserInfo;
import org.springframework.data.domain.Page;

public interface UserService {
    User saveUser(User user);
    Role saveRole(Role role);
    void addRoleToUser(String mail, String roleName);
    User updateUser(User user);
    void deleteUser(Long id);
    Page<UserInfo> getUsers();

}
