package training.weather.service.feign;

import feign.FeignException;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import training.weather.bo.Location;
import training.weather.bo.LocationInfo;

import java.util.List;

@FeignClient(name = "metaweather", url = "https://www.metaweather.com")
public interface MetAWeatherFeignClient {
  @RequestMapping(method = RequestMethod.GET, value = "/api/location/search/")
  List<Location> getLocation(@RequestParam(value = "query") String city) throws FeignException;

  @RequestMapping(method = RequestMethod.GET, value = "/api/location/{woeid}")
  LocationInfo getLocationInfo(@PathVariable(value = "woeid") Integer woeid) throws FeignException;
}
