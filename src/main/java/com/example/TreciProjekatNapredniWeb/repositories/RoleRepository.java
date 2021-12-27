package com.example.TreciProjekatNapredniWeb.repositories;

import com.example.TreciProjekatNapredniWeb.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    public Role findByName(String name);
}
