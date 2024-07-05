package org.jin.homework.controller;


import jakarta.servlet.http.HttpServletRequest;
import org.jin.homework.model.ApiResponse;
import org.jin.homework.model.User;
import org.jin.homework.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

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