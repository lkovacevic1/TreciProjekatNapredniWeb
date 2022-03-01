package com.example.TreciProjekatNapredniWeb.controllers;

import com.example.TreciProjekatNapredniWeb.model.Role;
import com.example.TreciProjekatNapredniWeb.model.User;
import com.example.TreciProjekatNapredniWeb.model.UserInfo;
import com.example.TreciProjekatNapredniWeb.request.UserRequest;
import com.example.TreciProjekatNapredniWeb.response.UserResponse;
import com.example.TreciProjekatNapredniWeb.services.UserService;
import com.example.TreciProjekatNapredniWeb.utils.JwtUtil;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public UserController(UserService userService, AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> login (@RequestBody UserRequest userLogin) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userLogin.getMail(), userLogin.getPassword()));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(401).build();
        }
        System.out.println(jwtUtil.generateToken(userLogin.getMail()));
        return ResponseEntity.ok(new UserResponse(jwtUtil.generateToken(userLogin.getMail()), userService.RolesForUser(userLogin.getMail())));
    }

    @GetMapping("/all")
    public ResponseEntity<Page<UserInfo>> getUsers(){
        return ResponseEntity.ok().body(userService.getUsers());
    }

    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public User createUser(@RequestBody User user){
        return userService.saveUser(user);
    }

    @PutMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    private ResponseEntity<User> updateUser(@RequestBody UserInfo user){
        User updateUser = userService.updateUser(user);
        return new ResponseEntity<>(updateUser, HttpStatus.OK);
    }

    @DeleteMapping(value = "/delete/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    private ResponseEntity<?> deleteUser(@PathVariable("id") Long id){
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/roles", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    private ResponseEntity<Role[]> getRoles(@RequestHeader("Authorization") String authorization){
        String jwt = authorization.substring("Bearer ".length());
        return ResponseEntity.ok().body(userService.RolesForUser(jwtUtil.extractUsername(jwt)));
    }
}