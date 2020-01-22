package training.weather.service.implementation;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import training.weather.bo.ConsolidatedWeather;
import training.weather.bo.Location;
import training.weather.bo.LocationInfo;
import training.weather.service.IWeatherService;
import training.weather.service.feign.MetAWeatherFeignClient;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class WeatherServiceImpl implements IWeatherService {

  private final MetAWeatherFeignClient metAWeatherService;

  public WeatherServiceImpl(MetAWeatherFeignClient feignClient) {
    this.metAWeatherService = feignClient;
  }

  @Override
  public ResponseEntity<String> getCityWeather(String city, Optional<LocalDateTime> dateTime) {
    if (dateTime.isPresent() && isWithinFiveDays(dateTime.get())) {
      return getWeatherState(city, dateTime.get());
    } else if (!dateTime.isPresent()) {
      return getWeatherState(city, LocalDateTime.now());
    } else {
      String cityWeather = "Date not yet available";
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(cityWeather);
    }
  }

  private ResponseEntity<String> getWeatherState(String city, LocalDateTime dateTime) {
    Optional<String> weatherState = Optional.empty();
    HttpStatus httpStatus = HttpStatus.OK;
    List<Location> locations = metAWeatherService.getLocation(city);
    if (!locations.isEmpty()) {
      Integer woeid = locations.stream().findFirst().get().getWoeid();
      LocationInfo locationInfo = metAWeatherService.getLocationInfo(woeid);
      for (ConsolidatedWeather consolidatedWeather : locationInfo.getConsolidatedWeatherList()) {
        if (matchApplicableDate(dateTime.toLocalDate(), consolidatedWeather.getApplicableDate())) {
          weatherState = Optional.of(consolidatedWeather.getWeatherStateName());
          break;
        }
      }
      if (!weatherState.isPresent()) {
        httpStatus = (HttpStatus.BAD_REQUEST);
        weatherState = Optional.of("Date not longer available");
      }

    } else {
      weatherState = Optional.of("Location not valid");
      httpStatus = (HttpStatus.BAD_REQUEST);
    }
    return ResponseEntity.status(httpStatus).body(weatherState.get());
  }

  private boolean isWithinFiveDays(LocalDateTime dateTime) {

    Date date = convertToDateViaInstant(dateTime);

    return date.before(new Date(new Date().getTime() + (1000 * 60 * 60 * 24 * 6)));
  }

  private boolean matchApplicableDate(LocalDate inputDate, LocalDate outputDate) {
    return inputDate.equals(outputDate);
  }

  private Date convertToDateViaInstant(LocalDateTime dateToConvert) {
    return java.util.Date.from(dateToConvert.atZone(ZoneId.systemDefault()).toInstant());
  }
}
