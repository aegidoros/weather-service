package training.weather.controller;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import training.weather.service.IWeatherService;

import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@RequestMapping("/")
public class WeatherController {

  private final IWeatherService weatherService;

  public WeatherController(IWeatherService weatherService) {
    this.weatherService = weatherService;
  }

  @RequestMapping(
      method = RequestMethod.GET,
      value = "/api/training/weather",
      produces = MediaType.TEXT_PLAIN_VALUE)
  ResponseEntity<String> getCityWeather(
      @RequestParam(value = "city") String city,
      @RequestParam(value = "date", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
              Optional<LocalDateTime> date) {
    return weatherService.getCityWeather(city, date);
  }
}
