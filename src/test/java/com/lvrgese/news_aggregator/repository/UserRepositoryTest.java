package com.lvrgese.news_aggregator.repository;

import com.lvrgese.news_aggregator.entity.User;
import com.lvrgese.news_aggregator.entity.UserRole;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void testFindByUsername_whenUserExists() {
        User user = User.builder()
                .username("test@example.com")
                .name("Test User")
                .password("encryptedPassword")
                .userRole(UserRole.ROLE_USER)
                .isEnabled(true)
                .build();

        userRepository.save(user);

        Optional<User> foundUser = userRepository.findByUsername("test@example.com");

        assertTrue(foundUser.isPresent(), "User should be found");
        assertEquals("Test User", foundUser.get().getName());
        assertEquals(UserRole.ROLE_USER, foundUser.get().getUserRole());
    }

    @Test
    void testFindByUsername_whenUserDoesNotExist() {
        Optional<User> foundUser = userRepository.findByUsername("missing@example.com");
        assertFalse(foundUser.isPresent(), "User should not be found");
    }
}
