package com.nhomA.mockproject.service;

import com.nhomA.mockproject.dto.RegistrationDTO;
import com.nhomA.mockproject.dto.UserDTO;

import java.util.List;

public interface UserService {
    boolean updatePassword (String username,String checkPassword, String newPassword);
    List<UserDTO> getAllUser ();
    boolean deleteUser (Long id);
    boolean resetPassword(Long id, String newPassword);
    UserDTO upadateUser(Long id, RegistrationDTO registrationDTO);
}
