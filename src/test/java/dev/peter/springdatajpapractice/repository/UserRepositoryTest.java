package dev.peter.springdatajpapractice.repository;

import dev.peter.springdatajpapractice.model.Role;
import dev.peter.springdatajpapractice.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }

    @Test
    void givenUser_whenFindByUsername_thenReturnUserOptional() {
        // given
        String username = "user";
        User user = new User(1L, username, "user", Role.USER, true);
        userRepository.save(user);
        // when
        Optional<User> expected = userRepository.findByUsername(username);
        // then
        assertThat(expected).isNotEmpty()
                .get()
                .isEqualTo(user);
    }

    @Test
    void givenNoUser_whenFindByUsername_thenReturnEmptyOptional() {
        // given
        String username = "user";
        // when
        Optional<User> expected = userRepository.findByUsername(username);
        // then
        assertThat(expected).isEmpty();
    }
}