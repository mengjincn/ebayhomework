package org.jin.homework.controller;


import jakarta.servlet.http.HttpServletRequest;
import org.jin.homework.model.ApiResponse;
import org.jin.homework.model.User;
import org.jin.homework.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }



    @PostMapping("/addUser")
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<ApiResponse<Object>> addUser(HttpServletRequest request,
                                                       @RequestBody Map<String, Object> userAccess) {
        try {
            User admin = (User) request.getAttribute("user");
            if (admin == null || !"admin".equals(admin.getRole())) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(new ApiResponse<>(403, "Access denied: Admin role required"));
            }

            Long userId = Long.parseLong(userAccess.get("userId").toString());
            List<String> endpoints = (ArrayList<String>) userAccess.get("endpoint");
            userService.addUser(userId, "BLANK", "BLANK");
            userService.addUserAccess(userId, new HashSet<>(endpoints));
            return ResponseEntity.ok(new ApiResponse<>(200, "User access added successfully"));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(400, "Error: " + e.getMessage()));
        }
    }
}
