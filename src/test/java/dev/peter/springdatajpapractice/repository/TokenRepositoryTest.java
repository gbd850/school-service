package dev.peter.springdatajpapractice.repository;

import dev.peter.springdatajpapractice.model.Role;
import dev.peter.springdatajpapractice.model.Token;
import dev.peter.springdatajpapractice.model.TokenType;
import dev.peter.springdatajpapractice.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class TokenRepositoryTest {

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private UserRepository userRepository;

    @AfterEach
    void tearDown() {
        tokenRepository.deleteAll();
    }

    @Test
    void givenValidUser_whenFindValidTokens_thenReturnValidTokenList() {
        // given
        String tokenString = "token";
        Long userId = 1L;
        User user = new User(userId, "user", "user", Role.USER, true);
        userRepository.save(user);
        Token token = new Token(1, tokenString, TokenType.BEARER, false, false, user);
        tokenRepository.save(token);
        // when
        List<Token> expected = tokenRepository.findAllValidTokenByUserId(userId);
        // then
        assertThat(expected).isNotEmpty()
                .hasSize(1)
                .allMatch(t -> t.equals(token));
    }

    @Test
    void givenInvalidUser_whenFindValidTokens_thenReturnEmptyList() {
        // given
        Long invalidUserId = 10L;
        // when
        List<Token> expected = tokenRepository.findAllValidTokenByUserId(invalidUserId);
        // then
        assertThat(expected).isEmpty();
    }

    @Test
    void givenToken_whenFindByToken_thenReturnTokenOptional() {
        // given
        String tokenString = "token";
        Long userId = 1L;
        User user = new User(userId, "user", "user", Role.USER, true);
        userRepository.save(user);
        Token token = new Token(1, tokenString, TokenType.BEARER, false, false, user);
        tokenRepository.save(token);
        // when
        Optional<Token> expected = tokenRepository.findByToken(tokenString);
        // then
        assertThat(expected).isNotEmpty()
                .get()
                .isEqualTo(token);
    }

    @Test
    void givenInvalidTokenName_whenFindByToken_thenReturnTokenOptional() {
        // given
        String invalidTokenName = "token";
        // when
        Optional<Token> expected = tokenRepository.findByToken(invalidTokenName);
        // then
        assertThat(expected).isEmpty();
    }
}