package dev.peter.springdatajpapractice.controller;

import dev.peter.springdatajpapractice.dto.UserRequestDto;
import dev.peter.springdatajpapractice.model.User;
import dev.peter.springdatajpapractice.service.SecurityUserDetailsService;
import jakarta.servlet.http.HttpServlet;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class RegisterController {

    private SecurityUserDetailsService userDetailsService;
    private AuthenticationManager authenticationManager;

    @GetMapping("/")
    public ResponseEntity<String> greetUser(Authentication authentication) {
        return new ResponseEntity<>("Hello " + authentication.getName(), HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody UserRequestDto userRequestDto) {
        return userDetailsService.registerUser(userRequestDto);
    }

    @PostMapping("/login")
    public ResponseEntity<String> authenticateUser(@RequestBody UserRequestDto loginDto){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.getUsername(), loginDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return new ResponseEntity<>("User login successfully!", HttpStatus.OK);
    }
}
