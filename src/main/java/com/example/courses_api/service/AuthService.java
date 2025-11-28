package com.example.courses_api.service;

import com.example.courses_api.dto.LoginRequestDTO;
import com.example.courses_api.dto.LoginResponseDTO;
import com.example.courses_api.dto.RegisterRequestDTO;
import com.example.courses_api.exception.UserAlreadyExistsException;
import com.example.courses_api.model.Role;
import com.example.courses_api.model.User;
import com.example.courses_api.repository.UserRepository;
import com.example.courses_api.security.JwtUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final JwtUtils jwtUtils;
    private final BCryptPasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository, JwtUtils jwtUtils, BCryptPasswordEncoder passwordEncoder)
    {
        this.userRepository = userRepository;
        this.jwtUtils = jwtUtils;
        this.passwordEncoder = passwordEncoder;
    }

    public void register(RegisterRequestDTO dto)
    {
        if(userRepository.existsByEmail(dto.getEmail()))
        {
            throw  new UserAlreadyExistsException("User with email " + dto.getEmail() + " already exists");
        }

        User user = new User();
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setHashedPassword(passwordEncoder.encode(dto.getPassword()));
        user.setRole(Role.STUDENT);
        userRepository.save(user);
    }

    public LoginResponseDTO login(LoginRequestDTO dto)
    {
        User user = userRepository.findByEmail(dto.getEmail());
        if(user == null)
        {
            throw new IllegalArgumentException("Invalid email or password");
        }
        if(! passwordEncoder.matches(dto.getPassword(), user.getHashedPassword()))
        {
            throw new IllegalArgumentException("Invalid email or password");
        }

        String token = jwtUtils.generateToken(user.getEmail(), Map.of("userId", user.getId(), "role" , user.getRole()));

        LoginResponseDTO responseDTO = new LoginResponseDTO();
        responseDTO.setToken(token);
        responseDTO.setUsername(user.getUsername());
        return responseDTO;
    }



}
