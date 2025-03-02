package com.Ecotrack.UsageService.Repositories;

import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.Ecotrack.UsageService.Models.Usage;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface UsageRepository extends MongoRepository<Usage, String> {
    List<Usage> findByEmail(String userId);
    Optional<Usage> findFirstByEmailOrderByLocalDateTimeDesc(String email);
     
    // Method to get total water usage for today
    @Aggregation(pipeline = {
    "{ $match: { 'email': ?0, 'localDateTime': { $gte: ?1, $lt: ?2 } } }",
    "{ $group: { _id: null, total: { $sum: '$waterUsage' } } }"
})
    Double aggregateTotalWaterUsageForToday(String email, LocalDateTime startOfDay, LocalDateTime endOfDay);
   
    // Method to get total electricity usage for today
    @Aggregation(pipeline = {
        "{ $match: { 'email': ?0, 'localDateTime': { $gte: ?1, $lt: ?2 } } }",
        "{ $group: { _id: null, total: { $sum: '$electricityUsage' } } }"
    })
    Double aggregateTotalElectricityUsageForToday(String email, LocalDateTime startOfDay, LocalDateTime endOfDay);

}
