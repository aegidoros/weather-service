package training.weather.service.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import training.weather.bo.Location;
import training.weather.bo.LocationInfo;

import java.util.List;

@FeignClient(name = "metaweather", url = "https://www.metaweather.com")
public interface MetAWeatherFeignClient {
    @GetMapping(value = "/api/location/search/")
    List<Location> getLocation(@RequestParam(value = "query") String city);

    @GetMapping(value = "/api/location/{woeid}")
    LocationInfo getLocationInfo(@PathVariable(value = "woeid") Integer woeid);
}
