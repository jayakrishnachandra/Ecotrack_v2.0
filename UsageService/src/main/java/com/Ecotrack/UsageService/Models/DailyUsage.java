package com.Ecotrack.UsageService.Models;
import java.time.LocalDate;

public class DailyUsage {
    private LocalDate date; // Use String for date in "YYYY-MM-DD" format
    private double totalWaterUsage; // or Double, depending on your needs
    private double totalElectricityUsage; // or Double

    public DailyUsage(LocalDate date, double totalWaterUsage, double totalElectricityUsage) {
        this.date = date;
        this.totalWaterUsage = totalWaterUsage;
        this.totalElectricityUsage = totalElectricityUsage;
    }

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

    public LocalDate getDate() {
      return this.date;
    }
    public void setDate(LocalDate value) {
      this.date = value;
    }
}