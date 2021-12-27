package com.example.TreciProjekatNapredniWeb.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.Collection;

@Data
public class UserInfo {
    private String name;
    private String lastname;
    private String mail;
    private Collection<String> roles = new ArrayList<>();
}
