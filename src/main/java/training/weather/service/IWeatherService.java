package training.weather.service;

import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Optional;

public interface IWeatherService {

  ResponseEntity<String> getCityWeather(String city, Optional<LocalDateTime> date);
}
