package dev.peter.springdatajpapractice.service;

import dev.peter.springdatajpapractice.config.JwtService;
import dev.peter.springdatajpapractice.dto.UserRequestDto;
import dev.peter.springdatajpapractice.model.Role;
import dev.peter.springdatajpapractice.model.User;
import dev.peter.springdatajpapractice.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@AllArgsConstructor
public class SecurityUserDetailsService implements UserDetailsService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private AuthenticationManager authenticationManager;
    private JwtService jwtService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    private void createUser(UserDetails user) {
        userRepository.save((User) user);
    }

    public User registerUser(UserRequestDto userRequestDto) {
        if (userRequestDto == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User cannot be null");
        }

        if (userRequestDto.getUsername() == null || userRequestDto.getUsername().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username cannot be null");
        }
        if (userRequestDto.getPassword() == null || userRequestDto.getPassword().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Password cannot be null");
        }

        User user = User.builder()
                .username(userRequestDto.getUsername())
                .password(passwordEncoder.encode(userRequestDto.getPassword()))
                .role(Role.USER)
                .accountNonLocked(true)
                .build();
        createUser(user);
        return user;
    }

    public String authenticateUser(UserRequestDto loginDto) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDto.getUsername(),
                        loginDto.getPassword()
                        )
        );
        User user = userRepository.findByUsername(loginDto.getUsername())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        return jwtService.generateToken(user);
    }
}
