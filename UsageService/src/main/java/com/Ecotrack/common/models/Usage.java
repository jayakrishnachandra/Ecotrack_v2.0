package com.Ecotrack.common.models;
import java.io.Serializable;
import java.time.LocalDateTime;

import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

@Document(collection = "usage")
public class Usage implements Serializable {
   private String email;
    private double waterUsage;
    private double electricityUsage;
    private LocalDateTime localDateTime;

     @JsonCreator
    public Usage(
        @JsonProperty("email") String email,
        @JsonProperty("waterUsage") double waterUsage,
        @JsonProperty("electricityUsage") double electricityUsage,
        @JsonProperty("localDateTime") int[] localDateTimeArray) {

        this.email = email;
        this.waterUsage = waterUsage;
        this.electricityUsage = electricityUsage;

        // Convert array to LocalDateTime
        this.localDateTime = LocalDateTime.of(
            localDateTimeArray[0],  // Year
            localDateTimeArray[1],  // Month
            localDateTimeArray[2],  // Day
            localDateTimeArray[3],  // Hour
            localDateTimeArray[4],  // Minute
            localDateTimeArray[5],  // Second
            localDateTimeArray[6]   // Nanosecond
        );
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

    public String getEmail() {
      return this.email;
    }
    public void setEmail(String value) {
      this.email = value;
    }
    public LocalDateTime getLocalDateTime() {
      return this.localDateTime;
    }
    public void setLocalDateTime(LocalDateTime value) {
      this.localDateTime = value;
    }

    @Override
    public String toString() {
        return "Usage{" +
                "email='" + email + '\'' +
                ", waterUsage=" + waterUsage +
                ", electricityUsage=" + electricityUsage +
                ", localDateTime=" + localDateTime +
                '}';
    }

//   class LocalDateTimeDeserializer extends JsonDeserializer<LocalDateTime> {
//         @Override
//         public LocalDateTime deserialize(JsonParser p, DeserializationContext ctxt)
//                 throws IOException, JsonProcessingException {
//             String date = p.getText();
//             // Convert the string with 'Z' to LocalDateTime
//             return ZonedDateTime.parse(date).toLocalDateTime();
//         }
// }
}
