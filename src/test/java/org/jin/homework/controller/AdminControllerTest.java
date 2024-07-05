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


import java.io.IOException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class AdminControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private HttpServletRequest request;

    @InjectMocks
    private AdminController adminController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void addUser_WithAdminRole_ShouldSucceed() throws Exception {
        // Arrange
        User adminUser = new User();
        adminUser.setRole("admin");
        when(request.getAttribute("user")).thenReturn(adminUser);

        Map<String, Object> userAccess = new HashMap<>();
        userAccess.put("userId", 123456L);
        List<String> endpoints = new ArrayList<>();
        endpoints.add("resource A");
        userAccess.put("endpoint", endpoints);

        // Act
        ResponseEntity<ApiResponse<Object>> response = adminController.addUser(request, userAccess);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(200, response.getBody().getCode());
        assertEquals("User access added successfully", response.getBody().getMessage());
        verify(userService, times(1)).addUserAccess(123456L, new HashSet<>(endpoints));
    }

    @Test
    void addUser_WithNonAdminRole_ShouldFail() throws IOException {
        // Arrange
        User nonAdminUser = new User();
        nonAdminUser.setRole("user");
        when(request.getAttribute("user")).thenReturn(nonAdminUser);

        Map<String, Object> userAccess = new HashMap<>();

        // Act
        ResponseEntity<ApiResponse<Object>> response = adminController.addUser(request, userAccess);

        // Assert
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
        assertEquals(403, response.getBody().getCode());
        assertEquals("Access denied: Admin role required", response.getBody().getMessage());
        verify(userService, never()).addUserAccess(anyLong(), anySet());
    }
}