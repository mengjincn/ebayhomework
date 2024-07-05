package org.jin.homework.controller;


import jakarta.servlet.http.HttpServletRequest;
import org.jin.homework.model.ApiResponse;
import org.jin.homework.model.User;
import org.jin.homework.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @PostMapping("/addUser")
    public ResponseEntity<ApiResponse<Object>> addUser(HttpServletRequest request,
                                     @RequestBody Map<String, Object> userAccess) {
        try {
            User admin = (User) request.getAttribute("user");
            if (admin == null || !"admin".equals(admin.getRole())) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(new ApiResponse<>(403, "Access denied: Admin role required"));
            }

            Long userId = Long.parseLong(userAccess.get("userId").toString());
            @SuppressWarnings("unchecked")
            List<String> endpoints = (ArrayList<String>) userAccess.get("endpoint");
            userService.addUserAccess(userId, new HashSet<>(endpoints));
            return ResponseEntity.ok(new ApiResponse<>(200, "User access added successfully"));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(400, "Error: " + e.getMessage()));
        }
    }
}
