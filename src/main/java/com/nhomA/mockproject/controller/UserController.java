package com.nhomA.mockproject.controller;

import com.nhomA.mockproject.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
@CrossOrigin

public class UserController
{
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PutMapping("/password")
    public ResponseEntity<?> updatePassword (Authentication authentication, @RequestParam("password") String password
                                             ,@RequestParam("newPassword") String newPassword){
        String username = authentication.getName();
        try{
            return new ResponseEntity<> (userService.updatePassword(username,password,newPassword), HttpStatus.OK);
        }catch (Exception ex){
            return new ResponseEntity<> (ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/admin/delete-user/{id}")
    public ResponseEntity<?> deleteUser (@PathVariable("id") Long id){
        try{
            return new ResponseEntity<> (userService.deleteUser(id), HttpStatus.OK);
        }catch (Exception ex){
            return new ResponseEntity<> (ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/admin/reset-password")
    public ResponseEntity<?> resetPassword(@RequestParam("id") Long id, @RequestParam("password") String password ){
        try{
            return new ResponseEntity<> (userService.resetPassword(id,password), HttpStatus.OK);
        }catch (Exception ex){
            return new ResponseEntity<> (ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/admin/get-users")
    public ResponseEntity<?> getAllUser(){
        try{
            return new ResponseEntity<> (userService.getAllUser(), HttpStatus.OK);
        }catch (Exception ex){
            return new ResponseEntity<> (ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
