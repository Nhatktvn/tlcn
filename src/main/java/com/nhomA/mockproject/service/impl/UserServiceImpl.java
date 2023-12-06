package com.nhomA.mockproject.service.impl;

import com.nhomA.mockproject.dto.RegistrationDTO;
import com.nhomA.mockproject.dto.UserDTO;
import com.nhomA.mockproject.entity.Identification;
import com.nhomA.mockproject.entity.User;
import com.nhomA.mockproject.exception.PasswordIncorrectException;
import com.nhomA.mockproject.exception.UserNotFoundException;
import com.nhomA.mockproject.mapper.UserMapper;
import com.nhomA.mockproject.repository.IdentificationRepository;
import com.nhomA.mockproject.repository.UserRepository;
import com.nhomA.mockproject.service.UserService;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final IdentificationRepository identificationRepository;
    private  final JavaMailSender javaMailSender;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, UserMapper userMapper, IdentificationRepository identificationRepository, JavaMailSender javaMailSender) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
        this.identificationRepository = identificationRepository;
        this.javaMailSender = javaMailSender;
    }

    @Transactional
    @Override
    public boolean changePassword(String username, String checkPassword, String newPassword) {
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

    @Override
    public UserDTO updateUser(Long id, RegistrationDTO registrationDTO) {
        Optional<User> existedUser = userRepository.findById(id);
        if(existedUser.isEmpty()){
            throw  new UserNotFoundException("User not found!");
        }
        User user = existedUser.get();
        user.setPassword(registrationDTO.getPassword());
        user.setUsername(registrationDTO.getUsername());
        Identification identification = user.getIdentification();
        identification.setFullName(registrationDTO.getFullName());
        identification.setEmail(registrationDTO.getEmail());
        identification.setPhone(registrationDTO.getPhone());
        user.setIdentification(identification);
        User saveUser = userRepository.save(user);
        return userMapper.toDTO(saveUser);
    }

    @Override
    public String sendForgotPassword(String email) {
        Optional<Identification> existedIdentification = identificationRepository.findByEmail(email);
        if(existedIdentification.isEmpty()){
            throw new UserNotFoundException("User not found!");
        }
        User user = existedIdentification.get().getUser();
        String resetToken = UUID.randomUUID().toString();
        user.setTokenResetPassword(resetToken);
        userRepository.save(user);

        String resetLink = "http://your-app-url/reset-password?token=" + resetToken + "&username=" + user.getUsername();
        sendEmail(email, "Password Reset", "Click the link to reset your password: " + resetLink);
        return resetLink;
    }

    @Override
    public boolean updatePassword(String token, String username, String password) {
        Optional<User> existedUser = userRepository.findByUsername(username);
        if(existedUser.isEmpty()) {
            throw new UserNotFoundException("User not found!");
        }
        if(!token.equals(existedUser.get().getTokenResetPassword())){
            return false;
        }
        User user = existedUser.get();
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);
        return true;
    }

    public void sendEmail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        javaMailSender.send(message);
    }
}
