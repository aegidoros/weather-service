
package training.weather.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import training.weather.service.IWeatherService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(WeatherController.class)
public class WeatherControllerIntegrationTest {
    @Autowired
    private MockMvc mvc;
    @MockBean
    private IWeatherService weatherService;

    @Test
    public void getCityWeather_Successful() throws Exception {
        given(weatherService.getCityWeather(anyString(), any()))
                .willReturn(ResponseEntity.ok("\"Showers\""));

        mvc.perform(
                get("/api/training/weather")
                        .param("city", "london").param("date", "2020-01-12T00:30:00"))
                .andExpect(status().isOk());
    }
}

