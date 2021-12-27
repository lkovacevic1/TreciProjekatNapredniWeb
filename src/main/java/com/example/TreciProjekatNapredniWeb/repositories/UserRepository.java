package com.example.TreciProjekatNapredniWeb.repositories;

import com.example.TreciProjekatNapredniWeb.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    public User findByName(String name);
    public User findByMail(String mail);
}
