package com.nhomA.mockproject.service.impl;

import com.nhomA.mockproject.dto.UserDTO;
import com.nhomA.mockproject.entity.User;
import com.nhomA.mockproject.exception.PasswordIncorrectException;
import com.nhomA.mockproject.exception.UserExistedException;
import com.nhomA.mockproject.exception.UserNotFoundException;
import com.nhomA.mockproject.mapper.UserMapper;
import com.nhomA.mockproject.repository.UserRepository;
import com.nhomA.mockproject.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
    }

    @Transactional
    @Override
    public boolean updatePassword(String username, String checkPassword, String newPassword) {
        Optional<User> emptyUser = userRepository.findByUsername(username);
        User user = emptyUser.get();
        boolean check = passwordEncoder.matches(checkPassword,user.getPassword());
        if(check == false){
            throw new PasswordIncorrectException("Password incorrect!");
        }
        String encodeNewPass = passwordEncoder.encode(newPassword);
        user.setPassword(encodeNewPass);
        userRepository.save(user);
        return true;
    }
    @Transactional
    @Override
    public List<UserDTO> getAllUser() {
        List<User> users = userRepository.findAll();
        List<UserDTO> userDTOS = userMapper.toDTOs(users);
        return userDTOS;
    }
    @Transactional
    @Override
    public boolean deleteUser(Long id) {
        userRepository.deleteById(id);
        return true;
    }
    @Transactional
    @Override
    public boolean resetPassword(Long id, String newPassword) {
        Optional<User> emptyUser = userRepository.findById(id);
        if(emptyUser.isEmpty())
        {
            throw new UserNotFoundException("User not found!");
        }
        User user = emptyUser.get();
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
        return true;
    }
}
