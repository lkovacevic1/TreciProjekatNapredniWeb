package com.example.TreciProjekatNapredniWeb.response;

import com.example.TreciProjekatNapredniWeb.model.Role;
import lombok.Data;

@Data
public class UserResponse {
    private String jwt;
    private Role[] roles;

    public UserResponse(String jwt, Role[] roles){
        this.roles = roles;
        this.jwt = jwt;
    }
}
