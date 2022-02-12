package com.example.TreciProjekatNapredniWeb.services;

import com.example.TreciProjekatNapredniWeb.mapper.UserMapper;
import com.example.TreciProjekatNapredniWeb.model.*;
import com.example.TreciProjekatNapredniWeb.model.enums.Status;
import com.example.TreciProjekatNapredniWeb.repositories.ErrorHistoryRepository;
import com.example.TreciProjekatNapredniWeb.repositories.MachineRepository;
import com.example.TreciProjekatNapredniWeb.repositories.RoleRepository;
import com.example.TreciProjekatNapredniWeb.repositories.UserRepository;
import com.example.TreciProjekatNapredniWeb.response.MachineResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class UserServiceImplementation implements UserService{

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final MachineRepository machineRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final TaskScheduler taskScheduler;
    private final ErrorHistoryRepository errorHistoryRepository;

    public UserServiceImplementation(TaskScheduler taskScheduler, UserRepository userRepo, RoleRepository roleRepo, MachineRepository machineRepository, PasswordEncoder passwordEncoder, UserMapper userMapper, ErrorHistoryRepository errorHistoryRepository) {
        this.userRepository = userRepo;
        this.roleRepository = roleRepo;
        this.machineRepository = machineRepository;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
        this.taskScheduler = taskScheduler;
        this.errorHistoryRepository = errorHistoryRepository;
    }

    @Override
    public User saveUser(User user) {
        user.setPassword(this.passwordEncoder.encode(user.getPassword()));
        Collection<Role> roles = new ArrayList<>();
        for (Role role: user.getRoles()) {
            roles.add(role);
        }
        user.setRoles(roles);
        return userRepository.save(user);
    }

    @Override
    public Role saveRole(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public void addRoleToUser(String mail, String roleName) {
        User user = userRepository.findByMail(mail);
        Role role = roleRepository.findByName(roleName);
        user.getRoles().add(role);
        userRepository.save(user);
    }

    @Override
    public User updateUser(UserInfo user) {
        User newUser = userRepository.getById(user.getId());
        newUser.setName(user.getName());
        newUser.setLastName(user.getLastName());
        newUser.setMail(user.getMail());
        Collection<Role> roles = new ArrayList<>();
        for (Role role: user.getRoles()) {
            roles.add(role);
        }
        newUser.setRoles(roles);
        return userRepository.save(newUser);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public Page<UserInfo> getUsers() {
        return userRepository.findAll(PageRequest.of(0, 10)).map(userMapper::UserToUserInfo);
    }

    @Override
    public Role[] RolesForUser(String mail) {
        Collection<Role> allRols = userRepository.findByMail(mail).getRoles();
        Role[] roles = new Role[allRols.size()];
        roles = allRols.toArray(roles);
        return roles;
    }

    @Override
    public Optional<User> getUser(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public User userId(String mail) {
        return userRepository.findByMail(mail);
    }

    @Override
    public Machine saveMachine(Machine machine) {
        return machineRepository.save(machine);
    }

    @Override
    public void addMachineToUser(String mail, Long id) {
        User user = userRepository.findByMail(mail);
        Machine machine = machineRepository.findMachineById(id);
        machine.setCreatedBy(user);
        machineRepository.save(machine);
    }

    @Override
    public List<Machine> getMachines(String username) {
        User user = userRepository.findByMail(username);
        return machineRepository.allMachinesByUser(user);
    }

    @Override
    public void deleteMachine(Long id) {
        machineRepository.deleteMachineById(id);
    }

    @Override
    public Machine saveMachine(String name, String username) {
        User user = userRepository.findByMail(username);
        Machine machine = new Machine();
        machine.setStatus(Status.STOPPED);
        machine.setCreatedBy(user);
        machine.setActive(true);
        machine.setName(name);
        machine.setCreated(LocalDate.now());
        return this.machineRepository.save(machine);
    }

    @Override
    public List<Machine> searchMachines(String name, String status, LocalDate dateFrom, LocalDate dateTo, User id) {
        Status st = null;
        if(status != null){
            st = Status.valueOf(status);
        }
        return this.machineRepository.searchMachine(name, st, dateFrom, dateTo, id);
    }

    @Override
    public Machine getMachine(Long id) {
        return machineRepository.findMachineById(id);
    }

    @Override
    @Async
    public void startMachine(Machine machine) {
        if(machine.getStatus().equals(Status.STOPPED)){
            if(machine.isUsed()){
                return;
            }else{
                machine.setUsed(true);
                machineRepository.save(machine);
                machine.setVersion(machine.getVersion() + 1);
                try{
                    Thread.sleep(10000);
                }catch (Exception e){}
                machine.setStatus(Status.RUNNING);
                machine.setUsed(false);
                machineRepository.save(machine);
            }
        }
    }

    @Override
    @Async
    public void stopMachine(Machine machine) {
        if(machine.getStatus().equals(Status.RUNNING)){
            if(machine.isUsed()){
                return;
            }else{
                machine.setUsed(true);
                machineRepository.save(machine);
                machine.setVersion(machine.getVersion() + 1);
                try{
                    Thread.sleep(10000);
                }catch (Exception e){
                    e.printStackTrace();
                }
                machine.setStatus(Status.STOPPED);
                machine.setUsed(false);
                machineRepository.save(machine);
            }
        }
    }

    @Override
    @Async
    public void restartMachine(Machine machine) {
        if(machine.getStatus().equals(Status.RUNNING)){
            if(machine.isUsed()){
                return;
            }else{
                machine.setUsed(true);
                try{
                    Thread.sleep(5000);
                }catch (Exception e){
                    e.printStackTrace();
                }
                machine.setStatus(Status.STOPPED);
                machineRepository.save(machine);
                machine.setVersion(machine.getVersion() + 1);
                try{
                    Thread.sleep(5000);
                }catch (Exception e){
                    e.printStackTrace();
                }
                machine.setStatus(Status.RUNNING);
                machine.setUsed(false);
                machineRepository.save(machine);
            }
        }
    }

    @Override
    public void scheduleMachine(Long id, String date, String time, String action) throws ParseException {
        Date date1 = new SimpleDateFormat("dd/MM/yyyy hh:mm").parse(date + " " + time);
        this.taskScheduler.schedule(() -> {
            if(action.equals("Start")){
                MachineResponse machineResponse = callStartMachine(id);
                if(machineResponse.getStatus() != HttpStatus.OK){
                    addToErrorHistory(id, new java.sql.Timestamp(date1.getTime()).toLocalDateTime(), action, machineResponse.getResponse());
                }
            }
            else if(action.equals("Stop")){
                MachineResponse machineResponse = callStopMachine(id);
                if(machineResponse.getStatus() != HttpStatus.OK){
                    addToErrorHistory(id, new java.sql.Timestamp(date1.getTime()).toLocalDateTime(), action, machineResponse.getResponse());
                }
            }else{
                MachineResponse machineResponse = callRestartMachine(id);
                if(machineResponse.getStatus() != HttpStatus.OK){
                    addToErrorHistory(id, new java.sql.Timestamp(date1.getTime()).toLocalDateTime(), action, machineResponse.getResponse());
                }
            }
        }, date1);
    }

    @Override
    public MachineResponse callStartMachine(Long id) {
        Machine machine = getMachine(id);
        if(machine.isUsed()){
            return new MachineResponse("Machine is being used", HttpStatus.BAD_REQUEST);
        }
        if(machine.getStatus() != Status.STOPPED){
            return new MachineResponse("Machine is not STOPPED", HttpStatus.BAD_REQUEST);
        }
        try{
            startMachine(machine);
            return new MachineResponse("OK", HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return new MachineResponse("Machine is being used", HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public MachineResponse callStopMachine(Long id) {
        Machine machine = getMachine(id);
        if(machine.isUsed()){
            return new MachineResponse("Machine is being used", HttpStatus.BAD_REQUEST);
        }
        if(machine.getStatus() != Status.RUNNING){
            return new MachineResponse("Machine is not RUNNING", HttpStatus.BAD_REQUEST);
        }
        try{
            stopMachine(machine);
            return new MachineResponse("OK", HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return new MachineResponse("Machine is being used", HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public MachineResponse callRestartMachine(Long id) {
        Machine machine = getMachine(id);
        if(machine.isUsed()){
            return new MachineResponse("Machine is being used", HttpStatus.BAD_REQUEST);
        }
        if(machine.getStatus() != Status.RUNNING){
            return new MachineResponse("Machine is not RUNNING", HttpStatus.BAD_REQUEST);
        }
        try{
            restartMachine(machine);
            return new MachineResponse("OK", HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return new MachineResponse("Machine is being used", HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public void addToErrorHistory(Long id, LocalDateTime date, String action, String message) {
        Machine machine = machineRepository.findMachineById(id);
        ErrorHistroy errorHistroy = new ErrorHistroy(null, date, action, message, machine);
        errorHistoryRepository.save(errorHistroy);
    }


    @Override
    public ErrorHistroy[] getErrorsForUser(User user) {
        return this.errorHistoryRepository.findAllByMachine_CreatedBy(user);
    }
}
