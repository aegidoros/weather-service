package training.weather.bo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Location {

  private String title;

  @JsonProperty(value = "location_type")
  private String locationType;

  @JsonProperty(value = "latt_long")
  private String lattLong;

  private Integer woeid;
  private Integer distance;
}
