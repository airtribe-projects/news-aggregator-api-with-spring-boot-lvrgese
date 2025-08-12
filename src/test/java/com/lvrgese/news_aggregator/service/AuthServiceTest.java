package com.lvrgese.news_aggregator.service;

import com.lvrgese.news_aggregator.auth.entity.AuthResponse;
import com.lvrgese.news_aggregator.auth.entity.LoginRequest;
import com.lvrgese.news_aggregator.auth.entity.RegisterRequest;
import com.lvrgese.news_aggregator.auth.service.AuthService;
import com.lvrgese.news_aggregator.auth.util.JwtUtil;
import com.lvrgese.news_aggregator.entity.User;
import com.lvrgese.news_aggregator.entity.UserRole;
import com.lvrgese.news_aggregator.exception.InvalidCredentialsException;
import com.lvrgese.news_aggregator.exception.ResourceAlreadyExistsException;
import com.lvrgese.news_aggregator.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private AuthService authService;

    @Test
    void registerUser_success() {
        RegisterRequest req = new RegisterRequest("Test User", "test@example.com", "password");

        when(userRepository.findByUsername("test@example.com")).thenReturn(Optional.empty());
        when(passwordEncoder.encode("password")).thenReturn("encodedPassword");

        User savedUser = User.builder()
                .userId(1L)
                .name("Test User")
                .username("test@example.com")
                .password("encodedPassword")
                .userRole(UserRole.ROLE_USER)
                .isEnabled(true)
                .build();

        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        try (MockedStatic<JwtUtil> jwtUtilMock = mockStatic(JwtUtil.class)) {
            jwtUtilMock.when(() -> JwtUtil.generateJwtToken("test@example.com"))
                    .thenReturn("mockedToken");

            AuthResponse response = authService.registerUser(req);

            assertNotNull(response);
            assertEquals(1L, response.getId());
            assertEquals("Test User", response.getName());
            assertEquals("mockedToken", response.getToken());
        } catch (ResourceAlreadyExistsException ignored) {
        }
    }

    @Test
    void registerUser_userAlreadyExists() {
        RegisterRequest req = new RegisterRequest("Test User", "test@example.com", "password");
        when(userRepository.findByUsername("test@example.com"))
                .thenReturn(Optional.of(new User()));

        assertThrows(ResourceAlreadyExistsException.class,
                () -> authService.registerUser(req));
    }

    @Test
    void loginUser_success() throws InvalidCredentialsException {
        LoginRequest loginRequest = new LoginRequest("test@example.com", "password");

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);
        when(userRepository.findByUsername("test@example.com"))
                .thenReturn(Optional.of(User.builder()
                        .userId(1L)
                        .name("Test User")
                        .username("test@example.com")
                        .password("encodedPassword")
                        .userRole(UserRole.ROLE_USER)
                        .isEnabled(true)
                        .build()));

        try (MockedStatic<JwtUtil> jwtUtilMock = mockStatic(JwtUtil.class)) {
            jwtUtilMock.when(() -> JwtUtil.generateJwtToken("test@example.com"))
                    .thenReturn("mockedToken");

            AuthResponse response = authService.loginUser(loginRequest);

            assertNotNull(response);
            assertEquals("mockedToken", response.getToken());
        }
    }

    @Test
    void loginUser_invalidCredentials() {
        LoginRequest loginRequest = new LoginRequest("wrong@example.com", "badpass");

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new RuntimeException("Auth failed"));

        assertThrows(InvalidCredentialsException.class,
                () -> authService.loginUser(loginRequest));
    }
}
