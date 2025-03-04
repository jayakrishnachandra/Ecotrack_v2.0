package com.Ecotrack.common.models;
import java.io.Serializable;


public class KafkaUsageEntity implements Serializable {
   private String email;
    private double waterUsage;
    private double electricityUsage;


    public KafkaUsageEntity() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public double getWaterUsage() {
        return waterUsage;
    }

    public void setWaterUsage(double waterUsage) {
        this.waterUsage = waterUsage;
    }

    public double getElectricityUsage() {
        return electricityUsage;
    }

    public void setElectricityUsage(double electricityUsage) {
        this.electricityUsage = electricityUsage;
    }

    public KafkaUsageEntity(Usage usage)
    {
        this.email = usage.getEmail();
        this.waterUsage = usage.getWaterUsage();
        this.electricityUsage = usage.getElectricityUsage();
    }
    
    @Override
    public String toString()
    {
        return " " + this.getEmail() + " " + this.getWaterUsage() + " " + this.getElectricityUsage();
    }
}