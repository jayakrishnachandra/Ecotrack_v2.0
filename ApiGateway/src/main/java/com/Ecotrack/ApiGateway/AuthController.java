package com.Ecotrack.ApiGateway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.Ecotrack.ApiGateway.Models.User;
import com.Ecotrack.ApiGateway.filter.JwtUtils;
import com.Ecotrack.ApiGateway.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import java.util.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        List<User> users = userService.findByEmail(user.email);
        if (!users.isEmpty() && users.get(0).password.equals(user.password)) {
            String token = jwtUtils.generateToken(user.email);
            return ResponseEntity.ok(Collections.singletonMap("token", token));
        }
        return ResponseEntity.status(401).body("Authentication Failed");
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        if (userService.findByEmail(user.email).isEmpty()) {
            userService.save(user);
            String token = jwtUtils.generateToken(user.email);
            return ResponseEntity.ok(Collections.singletonMap("token", token));
        }
        return ResponseEntity.badRequest().body("User already exists");
    }

  @PostMapping("/validateToken")
public ResponseEntity<String> validateToken(@RequestHeader("Authorization") String authorizationHeader) {
    if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid token");
    }

    String token = authorizationHeader.substring(7); // Remove "Bearer " prefix
     String email = jwtUtils.verifyToken(token); // Your token validation logic

    if (email == null) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
    }

    System.out.println("token validated for "+ email);
    
    return ResponseEntity.ok(email);
}

    
    @GetMapping("/user")
    public ResponseEntity<?> getUserData(HttpServletRequest request) {
        String token = extractToken(request);
        if (token != null) {
            String email = jwtUtils.extractUsername(token);
            return ResponseEntity.ok(userService.findByEmail(email).get(0));
        }
        return ResponseEntity.status(401).build();
    }

    private String extractToken(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        return (authHeader != null && authHeader.startsWith("Bearer ")) ? authHeader.substring(7) : null;
    }
}
