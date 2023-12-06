package com.nhomA.mockproject.service;

import com.nhomA.mockproject.dto.RegistrationDTO;
import com.nhomA.mockproject.dto.UserDTO;

import java.util.List;

public interface UserService {
    boolean changePassword (String username,String checkPassword, String newPassword);
    List<UserDTO> getAllUser ();
    boolean deleteUser (Long id);
    boolean resetPassword(Long id, String newPassword);
    UserDTO updateUser(Long id, RegistrationDTO registrationDTO);

    String sendForgotPassword (String email);

    boolean updatePassword (String token,String username,String password);
    boolean sendEmail(String to, String subject, String text);

}
