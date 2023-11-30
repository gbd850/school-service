package dev.peter.springdatajpapractice.service;

import dev.peter.springdatajpapractice.dto.UserRequestDto;
import dev.peter.springdatajpapractice.model.Role;
import dev.peter.springdatajpapractice.model.User;
import dev.peter.springdatajpapractice.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SecurityUserDetailsService implements UserDetailsService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public void createUser(UserDetails user) {
        userRepository.save((User) user);
    }

    public ResponseEntity<User> registerUser(UserRequestDto userRequestDto) {
        User user = User.builder()
                .username(userRequestDto.getUsername())
                .password(passwordEncoder.encode(userRequestDto.getPassword()))
                .role(Role.USER)
                .accountNonLocked(true)
                .build();
        createUser(user);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }
}
