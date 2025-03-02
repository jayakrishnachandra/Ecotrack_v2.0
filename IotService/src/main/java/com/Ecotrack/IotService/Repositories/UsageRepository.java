package com.Ecotrack.IotService.Repositories;

import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.Ecotrack.IotService.Models.Usage;

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

// // Method to get daily water usage for each day in a week
// @Aggregation(pipeline = {
//     "{ $match: { 'email': ?0, 'localDateTime': { $gte: ?1, $lt: ?2 } } }",
//     "{ $project: { day: { $dateToString: { format: '%Y-%m-%d', date: '$localDateTime' } }, waterUsage: '$waterUsage' } }",
//     "{ $group: { _id: '$day', totalWaterUsage: { $sum: '$waterUsage' } } }"
// })
// List<DailyUsage> aggregateDailyWaterUsageForWeek(String email, LocalDateTime startOfWeek, LocalDateTime endOfWeek);

// // Method to get daily electricity usage for each day in a week
// @Aggregation(pipeline = {
//     "{ $match: { 'email': ?0, 'localDateTime': { $gte: ?1, $lt: ?2 } } }",
//     "{ $project: { day: { $dateToString: { format: '%Y-%m-%d', date: '$localDateTime' } }, electricityUsage: '$electricityUsage' } }",
//     "{ $group: { _id: '$day', totalElectricityUsage: { $sum: '$electricityUsage' } } }"
// })
// List<DailyUsage> aggregateDailyElectricityUsageForWeek(String email, LocalDateTime startOfWeek, LocalDateTime endOfWeek);


}
