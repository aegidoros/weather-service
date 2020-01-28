package training.weather.service.implementation;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import training.weather.bo.ConsolidatedWeather;
import training.weather.bo.Location;
import training.weather.bo.LocationInfo;
import training.weather.service.IWeatherService;
import training.weather.service.feign.MetAWeatherFeignClient;

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
        Optional<String> cityWeather;
        HttpStatus httpStatus = HttpStatus.OK;
        if (dateTime.isPresent() && isWithinFiveDays(dateTime.get())) {
            cityWeather = getWeatherState(city, dateTime.get());
        } else if (!dateTime.isPresent()) {
            cityWeather = getWeatherState(city, LocalDateTime.now());
        } else {
            cityWeather = Optional.of("Date not valid");
            httpStatus = HttpStatus.BAD_REQUEST;
        }
        if (!cityWeather.isPresent()) {
            httpStatus = HttpStatus.BAD_REQUEST;
            cityWeather = Optional.of("Location not valid");
        }
        return ResponseEntity.status(httpStatus).body(cityWeather.get());
    }

    private Optional<String> getWeatherState(String city, LocalDateTime dateTime) {
        Optional<String> weatherState = Optional.empty();
        List<Location> locations = metAWeatherService.getLocation(city);
        Optional<Location> location = getFirstLocation(locations);
        if (location.isPresent()) {
            Integer woeid = location.get().getWoeid();
            LocationInfo locationInfo = metAWeatherService.getLocationInfo(woeid);
            for (ConsolidatedWeather consolidatedWeather : locationInfo.getConsolidatedWeatherList()) {
                if (dateTime.toLocalDate().equals(consolidatedWeather.getApplicableDate())) {
                    weatherState = Optional.of(consolidatedWeather.getWeatherStateName());
                    break;
                }
            }
        }
        return weatherState;
    }

    private Optional<Location> getFirstLocation(List<Location> locations) {
        Optional<Location> location = Optional.empty();
        if (!locations.isEmpty()) {
            location = locations.stream().findFirst();
        }
        return location;
    }

    private boolean isWithinFiveDays(LocalDateTime dateTime) {
        Date date = Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant());
        Date dateFrom = Date.from(LocalDateTime.now().minusDays(1).atZone(ZoneId.systemDefault()).toInstant());
        return date.after(dateFrom) && date.before(Date.from(LocalDateTime.now().plusDays(5).atZone(ZoneId.systemDefault()).toInstant()));
    }

}
