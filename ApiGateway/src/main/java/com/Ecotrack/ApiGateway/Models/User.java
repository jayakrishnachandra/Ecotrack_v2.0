package com.Ecotrack.ApiGateway.Models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "user")
public class User {


    @Id
    public
    String email;
    String username;
    public String password;
    int men_count;
    int room_count;
    double totalWaterUsageLimit;
    double totalElectricityUsageLimit;

    public String getPassword() {
      return this.password;
    }
    public void setPassword(String value) {
      this.password = value;
    }
    public String getEmail() {
      return this.email;
    }
    public void setEmail(String value) {
      this.email = value;
    }

    public double getTotalWaterUsageLimit() {
      return this.totalWaterUsageLimit;
    }
    public void setTotalWaterUsageLimit(double value) {
      this.totalWaterUsageLimit = value;
    }

    public int getMen_count() {
      return this.men_count;
    }
    public void setMen_count(int value) {
      this.men_count = value;
    }

    public int getRoom_count() {
      return this.room_count;
    }
    public void setRoom_count(int value) {
      this.room_count = value;
    }

    public double getTotalElectricityUsageLimit() {
      return this.totalElectricityUsageLimit;
    }
    public void setTotalElectricityUsageLimit(double value) {
      this.totalElectricityUsageLimit = value;
    }

    public String getUsername() {
      return this.username;
    }
    public void setUsername(String value) {
      this.username = value;
    }
}
