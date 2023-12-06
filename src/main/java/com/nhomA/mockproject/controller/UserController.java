package com.nhomA.mockproject.controller;

import com.nhomA.mockproject.dto.RegistrationDTO;
import com.nhomA.mockproject.exception.PasswordIncorrectException;
import com.nhomA.mockproject.exception.UserNotFoundException;
import com.nhomA.mockproject.service.UserService;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
public class UserController
{
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PutMapping("/change-password")
    public ResponseEntity<?> changePassword (Authentication authentication, @RequestParam("password") String password
                                             ,@RequestParam("newPassword") String newPassword){
        String username = authentication.getName();
        try{
            return new ResponseEntity<> (userService.changePassword(username,password,newPassword), HttpStatus.OK);
        }
        catch (PasswordIncorrectException ex){
            return new ResponseEntity<> (ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
        catch (Exception ex){
            return new ResponseEntity<> (ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/admin/user/{id}")
    public ResponseEntity<?> deleteUser (@PathVariable("id") Long id){
        try{
            return new ResponseEntity<> (userService.deleteUser(id), HttpStatus.OK);
        }catch (Exception ex){
            return new ResponseEntity<> (ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/admin/user/{id}")
    public ResponseEntity<?> updateUser (@PathVariable("id") Long id, @RequestBody RegistrationDTO registrationDTO){
        try{
            return new ResponseEntity<> (userService.updateUser(id,registrationDTO), HttpStatus.OK);
        }catch (Exception ex){
            return new ResponseEntity<> (ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PutMapping("/admin/reset-password")
    public ResponseEntity<?> resetPassword(@RequestParam("id") Long id, @RequestParam("password") String password ){
        try{
            return new ResponseEntity<> (userService.resetPassword(id,password), HttpStatus.OK);
        }
        catch (AuthenticationException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNAUTHORIZED);
        }
        catch (ExpiredJwtException ex){
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNAUTHORIZED);
        }
        catch (AccessDeniedException ex){
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.FORBIDDEN);
        }
        catch (UserNotFoundException ex) {
            return new ResponseEntity<> (ex.getMessage(), HttpStatus.NOT_FOUND);
        }
        catch (Exception ex){
            return new ResponseEntity<> (ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/admin/get-users")
    public ResponseEntity<?> getAllUser(){
        try{
            return new ResponseEntity<> (userService.getAllUser(), HttpStatus.OK);
        }
        catch (AuthenticationException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNAUTHORIZED);
        }
        catch (ExpiredJwtException ex){
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNAUTHORIZED);
        }
        catch (AccessDeniedException ex){
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.FORBIDDEN);
        }
        catch (UserNotFoundException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
        catch (Exception ex){
            return new ResponseEntity<> (ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestParam("email") String email){
        try{
            return new ResponseEntity<> (userService.sendForgotPassword(email), HttpStatus.OK);
        }
        catch (UserNotFoundException ex){
            return new ResponseEntity<> (ex.getMessage(), HttpStatus.NOT_FOUND);
        }
        catch (Exception ex){
            return new ResponseEntity<> (ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/update-password")
    public ResponseEntity<?> updatePassword(@RequestParam("tokenForgot") String token, @RequestParam("username") String username,@RequestParam("password") String password ){
        try{
            return new ResponseEntity<> (userService.updatePassword(token,username,password), HttpStatus.OK);
        }
        catch (UserNotFoundException ex){
            return new ResponseEntity<> (ex.getMessage(), HttpStatus.NOT_FOUND);
        }
        catch (Exception ex){
            return new ResponseEntity<> (ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/send-mail")
    public ResponseEntity<?> sendSimpleEmail() {
        try{
            return new ResponseEntity<> (userService.sendEmail("nguyenthiminhnguyetkt9x1@gmail.com", "hello", "description"), HttpStatus.OK);
        }
        catch (Exception ex){
            return new ResponseEntity<> (ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
