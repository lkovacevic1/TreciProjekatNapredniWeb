package com.example.TreciProjekatNapredniWeb.controllers;

import com.example.TreciProjekatNapredniWeb.model.Machine;
import com.example.TreciProjekatNapredniWeb.model.ScheduleMachine;
import com.example.TreciProjekatNapredniWeb.model.User;
import com.example.TreciProjekatNapredniWeb.model.enums.Status;
import com.example.TreciProjekatNapredniWeb.response.MachineResponse;
import com.example.TreciProjekatNapredniWeb.services.UserService;
import com.example.TreciProjekatNapredniWeb.utils.JwtUtil;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/machine")
public class MachineController {

    private final UserService userService;
    private final JwtUtil jwtUtil;

    public MachineController(UserService userService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @GetMapping(value="/all", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Machine>> getMachines(@RequestHeader("Authorization") String authorization){
        String jwt = authorization.substring("Bearer ".length());
        return ResponseEntity.ok().body(userService.getMachines(jwtUtil.extractUsername(jwt)));
    }

    @PutMapping(value = "/destroy/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    private ResponseEntity<?> deleteMachine(@PathVariable("id") Long id){
        userService.deleteMachine(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Machine createMachine(@RequestHeader("Authorization") String authorization, @RequestBody String name){
        String jwt = authorization.substring("Bearer ".length());
        return userService.saveMachine(name, jwtUtil.extractUsername(jwt));
    }

    @GetMapping(value = "/search", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Machine> searchMachines(@RequestParam(required = false) String name, @RequestParam(required = false) String status, @RequestParam(defaultValue = "1-1-1970") @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate dateFrom, @RequestParam(defaultValue = "1-1-2900")  @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate dateTo, @RequestHeader("Authorization") String  mail){
        String user = mail.substring("Bearer ".length());
        User user1 = userService.userId(jwtUtil.extractUsername(user));
        return  userService.searchMachines(name, status, dateFrom, dateTo, user1);
    }

    @PutMapping(value = "/start/{id}")
    public ResponseEntity<?> startMachine(@PathVariable Long id){
        MachineResponse machineResponse = userService.callStartMachine(id);
        return ResponseEntity.status(machineResponse.getStatus()).body(machineResponse.getResponse());
    }

    @PutMapping(value = "/stop/{id}")
    public ResponseEntity<?> stopMachine(@PathVariable Long id){
        MachineResponse machineResponse = userService.callStopMachine(id);
        return ResponseEntity.status(machineResponse.getStatus()).body(machineResponse.getResponse());
    }

    @PutMapping(value = "/restart/{id}")
    public ResponseEntity<?> restartMachine(@PathVariable Long id){
        MachineResponse machineResponse = userService.callRestartMachine(id);
        return ResponseEntity.status(machineResponse.getStatus()).body(machineResponse.getResponse());
    }

    @PutMapping(value="/schedule/{id}")
    public ResponseEntity<?> scheduleMachine(@PathVariable Long id, @RequestBody ScheduleMachine scheduleMachine) throws ParseException {
        userService.scheduleMachine(id, scheduleMachine.getDate(), scheduleMachine.getTime(), scheduleMachine.getAction());
        return ResponseEntity.ok().build();
    }
}
