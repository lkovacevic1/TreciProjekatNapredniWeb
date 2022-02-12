package com.example.TreciProjekatNapredniWeb.repositories;

import com.example.TreciProjekatNapredniWeb.model.ErrorHistroy;
import com.example.TreciProjekatNapredniWeb.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ErrorHistoryRepository extends JpaRepository<ErrorHistroy, Long> {
    public ErrorHistroy[] findAllByMachine_CreatedBy(User user);
}
