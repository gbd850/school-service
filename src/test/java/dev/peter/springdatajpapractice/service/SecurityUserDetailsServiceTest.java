package dev.peter.springdatajpapractice.service;

import dev.peter.springdatajpapractice.config.JwtService;
import dev.peter.springdatajpapractice.dto.UserRequestDto;
import dev.peter.springdatajpapractice.model.Role;
import dev.peter.springdatajpapractice.model.User;
import dev.peter.springdatajpapractice.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class SecurityUserDetailsServiceTest {

    private SecurityUserDetailsService securityUserDetailsService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private JwtService jwtService;

    @BeforeEach
    void setUp() {
        securityUserDetailsService = new SecurityUserDetailsService(
                userRepository,
                passwordEncoder,
                authenticationManager,
                jwtService
        );
    }

    @Test
    void givenUser_whenLoadUserByUsername_thenCallFindByUsername() {
        // given
        User user = new User(1L, "username", "password", Role.USER, true);
        given(userRepository.findByUsername(anyString())).willReturn(Optional.of(user));
        // when
        securityUserDetailsService.loadUserByUsername(user.getUsername());
        // then
        verify(userRepository).findByUsername(anyString());
    }

    @Test
    void givenInvalidUsername_whenLoadUserByUsername_thenThrowException() {
        // given
        String username = "username";
        // when
        // then
        assertThatThrownBy(() -> securityUserDetailsService.loadUserByUsername(username))
                .isInstanceOf(UsernameNotFoundException.class)
                .hasMessageContaining("User not found");
    }

    @Test
    void givenUser_whenRegisterUser_thenCallSave() {
        // given
        UserRequestDto user = new UserRequestDto("username", "password");
        // when
        securityUserDetailsService.registerUser(user);
        // then
        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);

        verify(userRepository).save(userArgumentCaptor.capture());

        User capturedUser = userArgumentCaptor.getValue();
        User expected = new User(null, user.getUsername(), passwordEncoder.encode(user.getPassword()), Role.USER, true);

        assertThat(capturedUser).isEqualTo(expected);
    }

    @Test
    void givenNoUser_whenRegisterUser_thenThrowException() {
        // given
        UserRequestDto user = null;
        // when
        // then
        assertThatThrownBy(() -> securityUserDetailsService.registerUser(user))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("User cannot be null");
    }

    @Test
    void givenNoUsername_whenRegisterUser_thenThrowException() {
        // given
        UserRequestDto user = new UserRequestDto(null, "password");
        // when
        // then
        assertThatThrownBy(() -> securityUserDetailsService.registerUser(user))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("Username cannot be null");
    }

    @Test
    void givenNoPassword_whenRegisterUser_thenThrowException() {
        // given
        UserRequestDto user = new UserRequestDto("username", null);
        // when
        // then
        assertThatThrownBy(() -> securityUserDetailsService.registerUser(user))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("Password cannot be null");
    }

    @Test
    void givenNoUsernameAndNoPassword_whenRegisterUser_thenThrowException() {
        // given
        UserRequestDto user = new UserRequestDto(null, null);
        // when
        // then
        assertThatThrownBy(() -> securityUserDetailsService.registerUser(user))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("Username cannot be null");
    }

    @Test
    void givenUser_whenAuthenticateUser_thenCallGenerateToken() {
        // given
        UserRequestDto user = new UserRequestDto("username", "password");
        given(userRepository.findByUsername(anyString()))
                .willReturn(Optional.of(User.builder().username(user.getUsername()).password(user.getPassword()).build()));
        // when
        securityUserDetailsService.authenticateUser(user);
        // then
        verify(jwtService).generateToken(any());
    }

    @Test
    void givenInvalidUser_whenAuthenticateUser_thenThrowException() {
        // given
        UserRequestDto user = new UserRequestDto("username", "password");
        // when
        // then
        assertThatThrownBy(() ->securityUserDetailsService.authenticateUser(user))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("User not found");
    }
}