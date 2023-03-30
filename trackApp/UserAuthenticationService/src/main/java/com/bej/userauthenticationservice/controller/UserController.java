package com.bej.userauthenticationservice.controller;


import com.bej.userauthenticationservice.domain.User;
import com.bej.userauthenticationservice.exception.InvalidCredentialsException;
import com.bej.userauthenticationservice.exception.UserAlreadyExistsException;
import com.bej.userauthenticationservice.security.SecurityTokenGenerator;

import com.bej.userauthenticationservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class UserController {
    private UserService userService;
    private SecurityTokenGenerator securityTokenGenerator;

    @Autowired
    public UserController(UserService userService, SecurityTokenGenerator securityTokenGenerator) {
        this.userService = userService;
        this.securityTokenGenerator = securityTokenGenerator;
    }
    @PostMapping("/user")
    public ResponseEntity<?> saveUser(@RequestBody User user) throws UserAlreadyExistsException {
        return new ResponseEntity<>(userService.saveUser(user),HttpStatus.CREATED);
    }
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody User user) throws InvalidCredentialsException
    {
        Map<String,String> token = new HashMap<>();
//        boolean flag = true;
        User retrievedUser = userService.findByEmailAndPassword(user.getEmail(),user.getPassword());
        if(retrievedUser==null)
        {
            token.put("message", "Login Unsuccessful");
            token.put("emailId", user.getEmail());
            return new ResponseEntity<>(token,HttpStatus.OK);
        }
        token = securityTokenGenerator.createToken(user);
        return new ResponseEntity<>(token,HttpStatus.OK);
    }
}
