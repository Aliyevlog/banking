package org.project.unitech.service;

import lombok.RequiredArgsConstructor;
import org.project.unitech.dto.LoginDTO;
import org.project.unitech.dto.UserDTO;
import org.project.unitech.exception.UserAlreadyExistsException;
import org.project.unitech.mapper.UserMapper;
import org.project.unitech.model.User;
import org.project.unitech.repository.UserRepository;
import org.project.unitech.util.JwtUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    public String authenticateUser(LoginDTO loginDTO) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDTO.pin(), loginDTO.password()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        return jwtUtil.generateToken(loginDTO.pin());
    }

    public void registerUser(UserDTO userDTO) {
        if (userRepository.findByPin(userDTO.pin()).isPresent()) {
            throw new UserAlreadyExistsException("User with PIN already registered");
        }

        User newUser = UserMapper.INSTANCE.userDTOtoUser(userDTO);
        newUser.setPassword(passwordEncoder.encode(userDTO.password()));
        userRepository.save(newUser);
    }
}
