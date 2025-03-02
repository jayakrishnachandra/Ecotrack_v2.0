// package com.Ecotrack.IotService;

// import java.util.Collections;
// import java.util.List;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.HttpStatus;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.RestController;
// import org.springframework.web.client.RestTemplate;

// import com.Ecotrack.IotService.Models.Usage;
// import com.Ecotrack.IotService.Services.UsageService;

// import jakarta.servlet.http.HttpServletRequest;
// import org.springframework.web.bind.annotation.CrossOrigin;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestBody;

// @CrossOrigin(origins = "*")
// @RestController
// public class HelloController {

//     @Autowired
//     private UsageService usageService;

//     private final String AUTH_SERVICE_URL = "http://localhost:8080/auth/validateToken"; // Change this based on your Auth service URL

//     @PostMapping(path = "/updateUsage")
//     public ResponseEntity<Usage> addUsage(HttpServletRequest req, @RequestBody Usage usage) {

//         String authHeader = req.getHeader("Authorization");
//         if (authHeader == null || !authHeader.startsWith("Bearer ")) {
//             return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
//         }

//         String token = authHeader.substring(7);
//         RestTemplate restTemplate = new RestTemplate();
//         ResponseEntity<String> response = restTemplate.postForEntity(AUTH_SERVICE_URL, token, String.class);

//         if (response.getStatusCode() == HttpStatus.OK) {
//             String email = response.getBody(); // Expecting email as response
//             usage.setEmail(email);
//             return ResponseEntity.ok(usageService.save(usage));
//         }

//         return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
//     }
// }
