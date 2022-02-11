package com.example.TreciProjekatNapredniWeb.mapper;

import com.example.TreciProjekatNapredniWeb.model.Role;
import com.example.TreciProjekatNapredniWeb.model.User;
import com.example.TreciProjekatNapredniWeb.model.UserInfo;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserInfo UserToUserInfo(User user){
        UserInfo userInfo = new UserInfo();
        userInfo.setId(user.getId());
        userInfo.setName(user.getName());
        userInfo.setLastName(user.getLastName());
        userInfo.setMail(user.getMail());
        Role[] roles = new Role[user.getRoles().size()];
        roles = user.getRoles().toArray(roles);
        userInfo.setRoles(roles);
        return userInfo;
    }

    public String RoleName(Role role){
        String name = role.getName();
        return name;
    }
}
