package com.Ecotrack.UsageService.Repositories;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.Ecotrack.UsageService.Models.User;

import java.util.List;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    List<User> findBy(String userId);
    List<User> findByEmail(String email);
}