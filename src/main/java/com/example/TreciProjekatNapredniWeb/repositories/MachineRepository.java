package com.example.TreciProjekatNapredniWeb.repositories;

import com.example.TreciProjekatNapredniWeb.model.Machine;
import com.example.TreciProjekatNapredniWeb.model.User;
import com.example.TreciProjekatNapredniWeb.model.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

public interface MachineRepository extends JpaRepository<Machine, Long> {
    @Query("FROM Machine M where M.id = :id")
    public Machine findMachineById(Long id);

    @Query("SELECT M FROM Machine M where M.createdBy = :id and M.active = true")
    public List<Machine> allMachinesByUser(User id);

    @Modifying
    @Query("UPDATE Machine M SET M.active = false WHERE M.id = :id and M.status=0")
    @Transactional
    public void deleteMachineById(Long id);

    @Query("SELECT M FROM Machine M WHERE (M.name = :name OR :name IS NULL) AND (M.status = :status OR :status IS NULL) AND (:dateFrom IS NULL OR :dateTo IS NULL OR(M.created >= :dateFrom OR M.created <= :dateTo)) AND M.createdBy = :id")
    public List<Machine> searchMachine(String name, Status status, LocalDate dateFrom, LocalDate dateTo, User id);
}
