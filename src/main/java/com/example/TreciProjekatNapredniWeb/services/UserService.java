package com.example.TreciProjekatNapredniWeb.services;

import com.example.TreciProjekatNapredniWeb.model.Machine;
import com.example.TreciProjekatNapredniWeb.model.Role;
import com.example.TreciProjekatNapredniWeb.model.User;
import com.example.TreciProjekatNapredniWeb.model.UserInfo;
import com.example.TreciProjekatNapredniWeb.response.MachineResponse;
import org.springframework.data.domain.Page;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface UserService {
    User saveUser(User user);
    Role saveRole(Role role);
    void addRoleToUser(String mail, String roleName);
    User updateUser(UserInfo user);
    void deleteUser(Long id);
    Page<UserInfo> getUsers();
    Role[] RolesForUser(String mail);
    Optional<User> getUser(Long id);
    User userId(String mail);

    //Add machine to user
    Machine saveMachine(Machine machine);
    void addMachineToUser(String mail, Long id);
    List<Machine> getMachines(String username);
    void deleteMachine(Long id);
    Machine saveMachine(String name, String username);
    List<Machine> searchMachines(String name, String status, LocalDate dateFrom, LocalDate dateTo, User mail);
    Machine getMachine(Long id);
    void startMachine(Machine machine);
    void stopMachine(Machine machine);
    void restartMachine(Machine machine);

    void scheduleMachine(Long id, String date, String time, String action) throws ParseException;

    MachineResponse callStartMachine(Long id);
    MachineResponse callStopMachine(Long id);
    MachineResponse callRestartMachine(Long id);
}
