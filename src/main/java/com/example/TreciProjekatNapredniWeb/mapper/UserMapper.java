package com.example.TreciProjekatNapredniWeb.mapper;

import com.example.TreciProjekatNapredniWeb.model.Role;
import com.example.TreciProjekatNapredniWeb.model.User;
import com.example.TreciProjekatNapredniWeb.model.UserInfo;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class UserMapper {

    public UserInfo UserToUserInfo(User user){
        UserInfo userInfo = new UserInfo();
        userInfo.setName(user.getName());
        userInfo.setLastname(user.getLastName());
        userInfo.setMail(user.getMail());
        userInfo.setRoles(user.getRoles().stream().map(this::RoleName).collect(Collectors.toList()));
        return userInfo;
    }

    public String RoleName(Role role){
        String name = role.getName();
        return name;
    }
}
