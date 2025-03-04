package com.Ecotrack.UsageService;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.Ecotrack.UsageService.Models.DailyUsage;
import com.Ecotrack.UsageService.Models.User;
import com.Ecotrack.UsageService.Services.UsageService;
import com.Ecotrack.UsageService.Services.UserService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class UsageRestController {

    private static final Logger logger = LoggerFactory.getLogger(UsageRestController.class);
    private final String AUTH_SERVICE_URL = "http://localhost:8065/auth/validateToken";
    private final RestTemplate restTemplate = new RestTemplate();

    @Autowired
    private UsageService usageService;

    @Autowired
    private UserService userService;

    @GetMapping("/userData")
    public ResponseEntity<User> getUserData(@RequestHeader(name = "Authorization", required = false) String authHeader) {
        String email = validateToken(authHeader);
        if (email != null) {
            List<User> users = userService.findByEmail(email);
            return users.isEmpty() ? ResponseEntity.status(HttpStatus.NOT_FOUND).build() : ResponseEntity.ok(users.get(0));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @GetMapping("/getDailyUsage")
    public ResponseEntity<List<DailyUsage>> getDailyUsages(@RequestHeader(name = "Authorization", required = false) String authHeader) {
        String email = validateToken(authHeader);
        if (email != null) {
            List<DailyUsage> dailyUsages = usageService.getDailyUsages(email);
            return ResponseEntity.ok(dailyUsages);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    private String validateToken(String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            logger.warn("Missing or invalid Authorization header");
            return null;
        }

        String token = authHeader.substring(7); // Extract token after "Bearer "
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", "Bearer " + token);

            HttpEntity<String> entity = new HttpEntity<>(headers);

            ResponseEntity<String> response = restTemplate.exchange(
                    AUTH_SERVICE_URL, HttpMethod.POST, entity, String.class
            );

            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                logger.debug("Token validation successful, email: {}", response.getBody());
                return response.getBody();
            } else {
                logger.warn("Token validation failed, status: {}, body: {}", response.getStatusCode(), response.getBody());
            }
        } catch (RestClientException e) {
            logger.error("Token validation failed: {}", e.getMessage());
        }
        return null;
    }
}
