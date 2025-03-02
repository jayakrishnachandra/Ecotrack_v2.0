
package com.Ecotrack.UsageService.Models;

import java.io.Serializable;

public class UsageResponse implements Serializable{
    private double waterUsage;
    private double electricityUsage;
    private double percentageOfWaterUsed;
    private double percentageOfElectricityUsed;
    private double totalWaterUsage;
    private double totalElectricityUsage;
    


    public UsageResponse(double waterUsage, double electricityUsage,double percentageOfWaterUsed,double percentageOfElectricityUsed,double totalWaterUsageToday, double totalElectricityUsageToday) {
      this.totalWaterUsage = totalWaterUsageToday;
      this.totalElectricityUsage = totalElectricityUsageToday;
      this.percentageOfElectricityUsed = percentageOfElectricityUsed;
      this.percentageOfWaterUsed = percentageOfWaterUsed;
      this.waterUsage = waterUsage;
      this.electricityUsage = electricityUsage;
    }

  //   public UsageResponse(Usage usage) {
  //     this.email = usage.getEmail();
  //     this.waterUsage = usage.getWaterUsage();
  //     this.electricityUsage = usage.getElectricityUsage();
  // }
  
    public double getTotalWaterUsage() {
      return this.totalWaterUsage;
    }
    public void setTotalWaterUsage(double value) {
      this.totalWaterUsage = value;
    }

    public double getTotalElectricityUsage() {
      return this.totalElectricityUsage;
    }
    public void setTotalElectricityUsage(double value) {
      this.totalElectricityUsage = value;
    }

    public double getWaterUsage() {
      return this.waterUsage;
    }
    public void setWaterUsage(double value) {
      this.waterUsage = value;
    }

    public double getElectricityUsage() {
      return this.electricityUsage;
    }
    public void setElectricityUsage(double value) {
      this.electricityUsage = value;
    }

    public double getPercentageOfWaterUsed() {
      return this.percentageOfWaterUsed;
    }
    public void setPercentageOfWaterUsed(double value) {
      this.percentageOfWaterUsed = value;
    }

    public double getPercentageOfElectricityUsed() {
      return this.percentageOfElectricityUsed;
    }
    public void setPercentageOfElectricityUsed(double value) {
      this.percentageOfElectricityUsed = value;
    }
}