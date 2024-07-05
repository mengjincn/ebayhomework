package org.jin.homework.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.jin.homework.model.ApiResponse;
import org.jin.homework.model.User;
import org.jin.homework.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class UserControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private HttpServletRequest request;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void accessResource_WithValidAccess_ShouldSucceed() throws Exception {
        // Arrange
        User user = new User();
        user.setUserId(123456L);
        when(request.getAttribute("user")).thenReturn(user);
        when(userService.hasAccess(123456L, "resource A")).thenReturn(true);

        // Act
        ResponseEntity<ApiResponse<Object>> response = userController.accessResource(request, "resource A");

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(200, response.getBody().getCode());
        assertEquals("Access granted to resource A", response.getBody().getMessage());
    }

    @Test
    void accessResource_WithInvalidAccess_ShouldFail() throws Exception {
        // Arrange
        User user = new User();
        user.setUserId(123456L);
        when(request.getAttribute("user")).thenReturn(user);
        when(userService.hasAccess(123456L, "resource D")).thenReturn(false);

        // Act
        ResponseEntity<ApiResponse<Object>> response = userController.accessResource(request, "resource D");

        // Assert
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
        assertEquals(403, response.getBody().getCode());
        assertEquals("Access denied to resource D", response.getBody().getMessage());
    }

    @Test
    void accessResource_WithNoUser_ShouldReturnUnauthorized() {
        // Arrange
        when(request.getAttribute("user")).thenReturn(null);

        // Act
        ResponseEntity<ApiResponse<Object>> response = userController.accessResource(request, "resource C");

        // Assert
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals(401, response.getBody().getCode());
        assertEquals("Unauthorized: User information not found", response.getBody().getMessage());
    }
}