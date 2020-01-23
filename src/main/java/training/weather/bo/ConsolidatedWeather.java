package training.weather.bo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ConsolidatedWeather {

    @JsonProperty(value = "applicable_date")
    private LocalDate applicableDate;

    @JsonProperty(value = "weather_state_name")
    private String weatherStateName;
}
