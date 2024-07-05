package org.jin.homework.controller;


import jakarta.servlet.http.HttpServletRequest;
import org.jin.homework.model.ApiResponse;
import org.jin.homework.model.User;
import org.jin.homework.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/{resource}")
    public ResponseEntity<ApiResponse<Object>> accessResource(HttpServletRequest request,
                                            @PathVariable String resource) {
        try {
            User user = (User) request.getAttribute("user");
            if (user == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(new ApiResponse<>(401, "Unauthorized: User information not found"));
            }
            if (userService.hasAccess(user.getUserId(), resource)) {
                return ResponseEntity.ok(new ApiResponse<>(200, "Access granted to " + resource));
            } else {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(new ApiResponse<>(403, "Access denied to " + resource));
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(400, "Error: " + e.getMessage()));
        }
    }
}