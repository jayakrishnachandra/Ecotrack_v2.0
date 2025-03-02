
package com.Ecotrack.ApiGateway.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Ecotrack.ApiGateway.Models.User;
import com.Ecotrack.ApiGateway.Repository.UserRepository;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User save(User us) {
        return userRepository.save(us);
    }

    public List<User> findByEmail(String userId) {
        return userRepository.findByEmail(userId);
    }
    public List<User> findall()
    {
        return userRepository.findAll();
    }

    
}
