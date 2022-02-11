package com.example.TreciProjekatNapredniWeb.repositories;

import com.example.TreciProjekatNapredniWeb.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    public Role findByName(String name);
}
