package com.lvrgese.news_aggregator.service;


import com.lvrgese.news_aggregator.dto.UserDTO;
import com.lvrgese.news_aggregator.entity.User;
import com.lvrgese.news_aggregator.entity.UserRole;
import com.lvrgese.news_aggregator.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private Authentication authentication;

    @Mock
    private SecurityContext securityContext;

    @InjectMocks
    private UserService userService;

    @Test
    void testGetCurrentUser_whenUserExists() {

        User mockUser = User.builder()
                .userId(1L)
                .username("test@example.com")
                .name("Test User")
                .password("secret")
                .userRole(UserRole.ROLE_USER)
                .isEnabled(true)
                .build();

        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("test@example.com");
        when(userRepository.findByUsername("test@example.com")).thenReturn(Optional.of(mockUser));

        SecurityContextHolder.setContext(securityContext);

        User result = userService.getCurrentUser();

        assertNotNull(result);
        assertEquals("Test User", result.getName());
        verify(userRepository, times(1)).findByUsername("test@example.com");
    }

    @Test
    void testGetCurrentUser_whenUserNotFound() {
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("missing@example.com");
        when(userRepository.findByUsername("missing@example.com")).thenReturn(Optional.empty());

        SecurityContextHolder.setContext(securityContext);

        assertThrows(UsernameNotFoundException.class, () -> userService.getCurrentUser());
    }

    @Test
    void testGetUserProfile_returnsUserDTO() {
        User mockUser = User.builder()
                .userId(1L)
                .username("test@example.com")
                .name("Test User")
                .password("secret")
                .userRole(UserRole.ROLE_USER)
                .isEnabled(true)
                .build();

        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("test@example.com");
        when(userRepository.findByUsername("test@example.com")).thenReturn(Optional.of(mockUser));

        SecurityContextHolder.setContext(securityContext);

        UserDTO result = userService.getUserProfile();

        assertNotNull(result);
        assertEquals("Test User", result.getName());
        assertEquals("test@example.com", result.getUsername());
    }
}
