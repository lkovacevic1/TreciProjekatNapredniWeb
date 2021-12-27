package com.example.TreciProjekatNapredniWeb.response;

import lombok.Data;

@Data
public class UserResponse {
    private String jwt;

    public UserResponse(String jwt){
        this.jwt = jwt;
    }
}
