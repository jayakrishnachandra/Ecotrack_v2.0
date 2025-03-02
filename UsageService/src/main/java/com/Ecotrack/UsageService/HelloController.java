// package com.Ecotrack.UsageService;
// import java.util.List;
// import java.util.Optional;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.HttpStatus;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.RestController;
// import com.Ecotrack.UsageService.Models.DailyUsage;
// import com.Ecotrack.UsageService.Models.UsageResponse;

// import com.Ecotrack.UsageService.Services.UsageService;
// import jakarta.servlet.http.HttpServletRequest;
// import org.springframework.web.bind.annotation.CrossOrigin;
// import org.springframework.web.bind.annotation.RequestMapping;

// @CrossOrigin(origins = "*") 
// @RestController
// public class HelloController {

//     @Autowired
//     private UsageService usageService;


//     @RequestMapping(path = "/getUsage")
//     public ResponseEntity<UsageResponse>getMethodName(HttpServletRequest req) {
//             String email = dbToken.get().getEmail();
//             UsageResponse ur = usageService.getTotalAndRecentUsage(email);
//             return ResponseEntity.ok(ur);
//     }

//     @RequestMapping(path = "/getDailyUsage")
//     public ResponseEntity<List<DailyUsage>> getDailyUsages(HttpServletRequest req) {

//             String email = dbToken.get().getEmail();
//             List<DailyUsage> dailyUsages = usageService.getDailyUsages(email);
//             return ResponseEntity.ok(dailyUsages);
//         }
//         return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
//     }
    
// }
