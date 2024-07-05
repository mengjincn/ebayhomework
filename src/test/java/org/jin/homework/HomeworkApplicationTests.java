package org.jin.homework;

import org.jin.homework.service.UserService;
import org.jin.homework.util.Base64Util;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class HomeworkApplicationTests {
    @Autowired
    private UserService userService;

    @Test
    void testDecodeAuthHeader() throws IOException {
        String authHeader = Base64.getEncoder().encodeToString(
                "{\"userId\":123456,\"accountName\":\"testUser\",\"role\":\"admin\"}".getBytes()
        );

        var user = Base64Util.decodeAuthHeader(authHeader);

        assertEquals(123456L, user.getUserId());
        assertEquals("testUser", user.getAccountName());
        assertEquals("admin", user.getRole());
    }

    @Test
    void testAddUserAccess() throws IOException {
        Long userId = 123456L;
        Set<String> endpoints = new HashSet<>(Arrays.asList("resource A", "resource B"));

        userService.addUserAccess(userId, endpoints);

        assertTrue(userService.hasAccess(userId, "resource A"));
        assertTrue(userService.hasAccess(userId, "resource B"));
        assertFalse(userService.hasAccess(userId, "resource D"));
    }

}
