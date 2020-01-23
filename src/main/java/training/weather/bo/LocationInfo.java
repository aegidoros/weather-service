package training.weather.bo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class LocationInfo {

    @JsonProperty(value = "consolidated_weather")
    private List<ConsolidatedWeather> consolidatedWeatherList;
}
