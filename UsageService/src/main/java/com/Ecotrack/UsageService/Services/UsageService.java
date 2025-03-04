package com.Ecotrack.UsageService.Services;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Ecotrack.UsageService.Models.DailyUsage;
import com.Ecotrack.UsageService.Models.UsageResponse;
import com.Ecotrack.UsageService.Models.User;
import com.Ecotrack.UsageService.Repositories.UsageRepository;
import com.Ecotrack.common.models.Usage;

@Service
public class UsageService {

    @Autowired
    private UsageRepository usageRepository;

    @Autowired
    private UserService userService;

    public Usage save(Usage usage) {
        return usageRepository.save(usage);
    }

    public List<Usage> findByEmail(String userId) {
        return usageRepository.findByEmail(userId);
    }

    public Usage findMostRecentByEmail(String email) {
        Optional<Usage> usage = usageRepository.findFirstByEmailOrderByLocalDateTimeDesc(email);
        return usage.orElse(null);
    }

    public UsageResponse getTotalAndRecentUsage(String email) {
        LocalDate today = LocalDate.now(ZoneId.of("UTC"));
        LocalDateTime startOfDay = today.atStartOfDay();
        LocalDateTime endOfDay = today.plusDays(1).atStartOfDay();
    
        Double totalWaterUsageToday = usageRepository.aggregateTotalWaterUsageForToday(email, startOfDay, endOfDay);
        Double totalElectricityUsageToday = usageRepository.aggregateTotalElectricityUsageForToday(email, startOfDay, endOfDay);

    
        Optional<Usage> mostRecentUsageOpt = usageRepository.findFirstByEmailOrderByLocalDateTimeDesc(email);
        Usage mostRecentUsage = mostRecentUsageOpt.orElse(null);
        double waterUsage = mostRecentUsage != null ? mostRecentUsage.getWaterUsage() : 0;
        double electricityUsage = mostRecentUsage != null ? mostRecentUsage.getElectricityUsage() : 0;
        totalWaterUsageToday = totalWaterUsageToday != null ? totalWaterUsageToday : 0;
        totalElectricityUsageToday = totalElectricityUsageToday != null ? totalElectricityUsageToday/(1000*3600) : 0;

        List<User> users = userService.findByEmail(email);
        User u = users.get(0);
        Double percentageOfWaterUsed = (totalWaterUsageToday/u.totalWaterUsageLimit)*100;
        Double percentageOfElectricityUsed = (totalElectricityUsageToday/u.totalElectricityUsageLimit)*100;

        return new UsageResponse(waterUsage, electricityUsage,percentageOfWaterUsed,percentageOfElectricityUsed,totalWaterUsageToday, totalElectricityUsageToday);
    }

    public List<DailyUsage> getDailyUsages(String email)
    {
        List<DailyUsage> dailyUsages = new ArrayList<>();
        LocalDate day = LocalDate.now(ZoneId.of("UTC")).minusDays(7);

        for(int i=0;i<8;i++)
        {
            LocalDateTime startOfDay = day.atStartOfDay();
            LocalDateTime endOfDay = day.plusDays(1).atStartOfDay();

            Double totalWaterUsageToday = usageRepository.aggregateTotalWaterUsageForToday(email, startOfDay, endOfDay);
            Double totalElectricityUsageToday = usageRepository.aggregateTotalElectricityUsageForToday(email, startOfDay, endOfDay);

            dailyUsages.add(new DailyUsage(day,  totalWaterUsageToday != null ? totalWaterUsageToday : 0, totalElectricityUsageToday != null ? totalElectricityUsageToday/(1000*3600) : 0));

            day = day.plusDays(1);
        }

        return dailyUsages;
    }


    

    
}